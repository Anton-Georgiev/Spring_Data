package com.example.football.service.impl;

import com.example.football.models.dto.ImportPlayerDTO;
import com.example.football.models.dto.ImportPlayerRootDTO;
import com.example.football.models.dto.ImportStatRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PlayerServiceImpl implements PlayerService {

    private final Path path = Path
            .of( "src", "main", "resources", "files", "xml", "players.xml");
    private final PlayerRepository playerRepository;
    private final JAXBContext jaxbContext;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
//    private final TypeMap<ImportPlayerDTO, Player> dtoToPlayer;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.jaxbContext = JAXBContext.newInstance(ImportPlayerRootDTO.class);
        this.unmarshaller = jaxbContext.createUnmarshaller();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;

        Converter<String, LocalDate> toLocalDate = s -> s.getSource() == null ? null :
                LocalDate.parse(s.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        modelMapper.addConverter(toLocalDate);
//
//        TypeMap<ImportPlayerDTO, Player> typeMap = this.modelMapper.createTypeMap(ImportPlayerDTO.class, Player.class);
//        this.dtoToPlayer = typeMap.addMappings(map -> map.using(toLocalDate).map(ImportPlayerDTO::getBirthDate, Player::setBirthDate));
      this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), String.class, LocalDate.class);
    }


    @Override
    public boolean areImported() {
       return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return String.join("\n", Files.readString(path));
    }

    @Override
    public String importPlayers() throws IOException, JAXBException {
        ImportPlayerRootDTO playerRootDTOs = (ImportPlayerRootDTO) this.unmarshaller.unmarshal(Files.newBufferedReader(path));

       return playerRootDTOs.getPlayers().stream().map(this::importPlayer).collect(Collectors.joining("\n"));
    }

    private String importPlayer(ImportPlayerDTO dto) {
        Set<ConstraintViolation<ImportPlayerDTO>> error = this.validator.validate(dto);

        if (!error.isEmpty()){
            return "Invalid Player";
        }

        Player newPlayer = this.modelMapper.map(dto,Player.class);
        Optional<Player> playersInDB = this.playerRepository.findByEmail(newPlayer.getEmail());

        if(playersInDB.isPresent()){
            return "Invalid Player";
        }

        Optional<Town> town = this.townRepository.findByName(newPlayer.getTown().getName());
        Optional<Team> team = this.teamRepository.findByName(newPlayer.getTeam().getName());
        Optional<Stat> stat = this.statRepository.findById(newPlayer.getId());

        newPlayer.setTown(town.get());
        newPlayer.setTeam(team.get());
        newPlayer.setStat(stat.get());

        this.playerRepository.save(newPlayer);

        return String.format("Successfully imported Player %s %s - %s", newPlayer.getFirstName(), newPlayer.getLastName(), newPlayer.getPosition());
    }

    @Override
    public String exportBestPlayers() {
        LocalDate startDate = LocalDate.parse("01-01-1995", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate endDate = LocalDate.parse("01-01-2003",DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Player> players = this.playerRepository.findAllByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastName(startDate, endDate);

//        return "zdrave da e";
        return players.stream().map(Player::toString).collect(Collectors.joining());
    }
}
