package com.example.demo.api;

import com.example.demo.entity.Match;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchApi {
    @Autowired
    private final MatchService matchService;

    @GetMapping
    public List<Match> getALL() {
        List<Match> matchList = matchService.getAll();
        return matchList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable(value = "id")Integer id) throws ResourceNotFoundException {
        Match match = matchService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found:" + id));
        return ResponseEntity.ok().body(match);
    }

    @PostMapping("/add")
    public Match create(@RequestBody Match match){return matchService.saveMatch(match);}

    @PutMapping("/{id}")
    public ResponseEntity<Match> update(@PathVariable(value = "id")Integer id,
                                        @RequestBody Match matchDetail) throws ResourceNotFoundException{
        Match match = matchService.findById(id). orElseThrow(() -> new ResourceNotFoundException("ID not found:" +id));
        match.setStartDate(matchDetail.getStartDate());
        match.setDuration(matchDetail.getDuration());
        match.setWinnerId(matchDetail.getWinnerId());
        match.setPlayer1(matchDetail.getPlayer1());
        match.setPlayer2(matchDetail.getPlayer2());
        match.setRound(matchDetail.getRound());

       Match matchUpdate = matchService.saveMatch(match);

       return ResponseEntity.ok(matchUpdate);

    }
    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id")Integer id) throws ResourceNotFoundException{
        Match match = matchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Match not found:" + id));
        matchService.deleteMatchById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}


