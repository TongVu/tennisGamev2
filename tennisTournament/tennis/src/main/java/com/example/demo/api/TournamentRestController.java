package com.example.demo.api;

import com.example.demo.entity.Tournament;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/tournaments")

public class TournamentRestController {
    private TournamentService tournamentService;

    @GetMapping
    public List<Tournament> getAll() {
        return tournamentService.getAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Tournament> getTournamentByName(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Tournament tournament = tournamentService.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found on: " + name));
        return ResponseEntity.ok().body(tournament);
    }

    @PostMapping("/add")
    public void create(@RequestBody Tournament tournament) {
        tournamentService.saveTournament(tournament);
    }

    @DeleteMapping("/delete/{name}")
    public Map<String, Boolean> delete(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Tournament tournament = tournamentService.findById (name)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found on: " + name));
        tournamentService.deleteById(name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
