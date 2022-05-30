package com.example.demo.api;

import com.example.demo.entity.Tournament;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.TournamentService;
import com.example.demo.service.dto.TournamentDto;
import com.example.demo.service.mapper.TournamentMapper;
import com.example.demo.service.mapper.TournamentMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(TournamentApi.PATH)

public class TournamentApi {
    public static final String PATH="/api/tournaments";
    @Autowired
    private TournamentService tournamentService;

    @GetMapping
    public ResponseEntity <List<TournamentDto>> getAll() {
        return ResponseEntity.ok(TournamentMapper.INSTANCE.toDtos(tournamentService.getAll()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Tournament> getTournamentByName(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Tournament tournament = tournamentService.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found on: " + name));
        return ResponseEntity.ok().body(tournament);
    }

    @PostMapping
    public ResponseEntity<Tournament> create(@RequestBody Tournament tournament) {
        Tournament tournamentCreated= tournamentService.saveTournament(tournament);
    return ResponseEntity.created(URI.create(TournamentApi.PATH+"/"+tournamentCreated.getName())).body(tournamentCreated);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Tournament tournament = tournamentService.findById (name)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found on: " + name));
        tournamentService.deleteById(name);
        return ResponseEntity.noContent().build();
    }
}
