package com.example.demo.api;

import com.example.demo.entity.Match;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.MatchService;
import com.example.demo.service.PlayerService;
import com.example.demo.service.RoundService;
import com.example.demo.service.StadiumService;
import com.example.demo.service.dto.MatchDto;
import com.example.demo.service.mapper.MatchMapper;
import com.example.demo.api.request.MatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(MatchResource.PATH)
public class MatchResource {
    public static final  String PATH ="/api/matches";
    @Autowired
    private MatchService matchService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private StadiumService stadiumService;
    @Autowired
    private RoundService roundService;



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
    public ResponseEntity<MatchDto> create(@RequestBody MatchRequest match) {
    Match createdMatch =
        matchService.saveMatch(
            new Match(
                null,
                match.getStartDate(),
                match.getDuration(),
                match.getWinnerId(),
                playerService.getById(match.getPlayer1Id()).get(),
                playerService.getById(match.getPlayer2Id()).get(),
                roundService.findRoundById(match.getRoundId()).get(),
                stadiumService.findStadiumById(match.getStadiumId()).get()));

        return ResponseEntity.created(URI.create(PATH + "/" + createdMatch.getId())).body(MatchMapper.INSTANCE.toDto(createdMatch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> update(@PathVariable(value = "id") Integer id,
                                        @RequestBody MatchRequest matchDetail) throws ResourceNotFoundException {
        Match match = matchService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID not found:" + id));
        match.setStartDate(matchDetail.getStartDate());
        match.setDuration(matchDetail.getDuration());
        match.setPlayer1(playerService.getById(matchDetail.getPlayer1Id()).get());
        match.setPlayer2(playerService.getById(matchDetail.getPlayer2Id()).get());
        match.setRound(roundService.findRoundById(matchDetail.getRoundId()).get());
        match.setStadium(stadiumService.findStadiumById(matchDetail.getStadiumId()).get());
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


