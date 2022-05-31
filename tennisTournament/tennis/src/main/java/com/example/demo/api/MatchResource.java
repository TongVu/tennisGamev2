package com.example.demo.api;

import com.example.demo.entity.Match;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(MatchResource.PATH)
public class MatchResource {

    public static final String PATH = "/api/matches";
    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<Match>> getALL() {
        List<Match> matchList = matchService.getAll();
        return ResponseEntity.ok(matchList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Match match = matchService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found:" + id));
        return ResponseEntity.ok().body(match);
    }

    @PostMapping
    public ResponseEntity<Match> create(@RequestBody Match match) {
        Match createdMatch = matchService.saveMatch(match);
        return ResponseEntity.created(URI.create(PATH + "/" + createdMatch.getId())).body(createdMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> update(@PathVariable(value = "id") Integer id,
                                        @RequestBody Match matchDetail) throws ResourceNotFoundException {
        Match match = matchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found:" + id));
        match.setStartDate(matchDetail.getStartDate());
        match.setDuration(matchDetail.getDuration());
        match.setWinnerId(matchDetail.getWinnerId());
        match.setPlayer1(matchDetail.getPlayer1());
        match.setPlayer2(matchDetail.getPlayer2());
        match.setRound(matchDetail.getRound());
        match.setStadium(matchDetail.getStadium());
        Match matchUpdate = matchService.saveMatch(match);

        return ResponseEntity.ok(matchUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Match match = matchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Match not found:" + id));
        matchService.deleteMatchById(id);
        return ResponseEntity.noContent().build();
    }

}


