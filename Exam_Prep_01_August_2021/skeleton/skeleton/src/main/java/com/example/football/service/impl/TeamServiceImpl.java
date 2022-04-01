package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

//ToDo - Implement all methods
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "teams.json");

        return String.join("\n", Files.readString(path));
    }

    @Override
    public String importTeams() throws IOException {
        String json = readTeamsFileContent();
        ImportTeamDTO[] importTeamDTOs = this.gson.fromJson(json, ImportTeamDTO[].class);
        StringBuilder result = new StringBuilder();

        for (ImportTeamDTO importTeamDTO : importTeamDTOs) {
            Set<ConstraintViolation<ImportTeamDTO>> violations = this.validator.validate(importTeamDTO);

            if (violations.isEmpty()) {
                String teamName = importTeamDTO.getName();
                Optional<Team> teamsInDb = this.teamRepository.findByName(teamName);

                if (teamsInDb.isEmpty()) {
                    Team newTeam = this.modelMapper.map(importTeamDTO, Team.class);
                   Optional<Town> town = this.townRepository.findByName(importTeamDTO.getTownName());
                    newTeam.setTown(town.get());

                    this.teamRepository.save(newTeam);
                    result.append(String.format("Successfully imported Team %s - %d\n", newTeam.getName(), newTeam.getFanBase()));

                } else result.append("Invalid Team\n");
            } else {
                result.append("Invalid Team\n");
            }
        }
        return result.toString();
    }
}
