package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.ImportStatRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//ToDo - Implement all methods
@Service
public class StatServiceImpl implements StatService {
    private final Path path = Path
            .of("src", "main", "resources", "files", "xml", "stats.xml");

    private final StatRepository statRepository;
    private final JAXBContext jaxbContext;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;
        this.jaxbContext = JAXBContext.newInstance(ImportStatRootDTO.class);
        this.unmarshaller = jaxbContext.createUnmarshaller();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return String.join("\n", Files.readString(path));
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        ImportStatRootDTO statsDTOs = (ImportStatRootDTO) this.unmarshaller.unmarshal(Files.newBufferedReader(path));

       return statsDTOs.getStats().stream().map(this::importStat).collect(Collectors.joining("\n"));
    }

    private String importStat(ImportStatDTO dto) {
        Set<ConstraintViolation<ImportStatDTO>> errors = this.validator.validate(dto);

        if (!errors.isEmpty()){
            return "Invalid Stat";
        }
        Optional<Stat> statsInDb = this.statRepository
                .findByShootingAndPassingAndEndurance(dto.getPassing(), dto.getShooting(), dto.getEndurance());

        if (statsInDb.isPresent()){
            return "Invalid Stat";
        }

        Stat newStat = this.modelMapper.map(dto, Stat.class);
        this.statRepository.save(newStat);
        return String.format("Successfully imported Stat %.2f - %.2f - %.2f", newStat.getShooting(), newStat.getPassing(), newStat.getEndurance());
    }
}
