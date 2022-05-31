package com.example.demo.api;

import com.example.demo.entity.Match;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.MatchService;
import com.example.demo.service.dto.MatchDto;
import com.example.demo.service.mapper.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(MatchResource.PATH)
public class MatchResource {

    public static final String PATH = "/api/matches";
    @Autowired
    private MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchDto>> getALL() {
        List<Match> matchList = matchService.getAll();
        return ResponseEntity.ok(MatchMapper.INSTANCE.toDtos(matchList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Match match = matchService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found:" + id));
        return ResponseEntity.ok().body(MatchMapper.INSTANCE.toDto(match));
    }

    @PostMapping
    public ResponseEntity<MatchDto> create(@RequestBody Match match) {
        Match createdMatch = matchService.saveMatch(match);
        return ResponseEntity.created(URI.create(PATH + "/" + createdMatch.getId())).body(MatchMapper.INSTANCE.toDto(createdMatch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> update(@PathVariable(value = "id") Integer id,
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

        return ResponseEntity.ok(MatchMapper.INSTANCE.toDto(matchUpdate));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Match match = matchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Match not found:" + id));
        matchService.deleteMatchById(id);
        return ResponseEntity.noContent().build();
    }

}


