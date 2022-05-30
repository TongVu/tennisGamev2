package com.example.demo.api;

import com.example.demo.entity.Match;
import com.example.demo.entity.Round;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.RoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/round")
public class RoundApi {
    @Autowired
    private final RoundService roundService;

    @GetMapping
    public List<Round> getAll(){
        return roundService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Round> getById(@PathVariable(value = "id")Integer id) throws ResourceNotFoundException {
        Round round = roundService.findRoundById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found:" + id));
        return ResponseEntity.ok().body(round);
    }

    @PostMapping("/add")
    public Round create(@RequestBody Round round){
        return roundService.saveRound(round);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Round> update(@PathVariable(value = "id")Integer id,
                                        @RequestBody Round roundDetail) throws ResourceNotFoundException{
        Round round = roundService.findRoundById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        round.setRoundName(roundDetail.getRoundName());
        round.setRoundType(roundDetail.getRoundType());

        Round roundUpdate = roundService.saveRound(round);
        return ResponseEntity.ok(roundUpdate);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id")Integer id) throws ResourceNotFoundException{
        Round round = roundService.findRoundById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        roundService.deleteRoundById(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("delete", Boolean.TRUE);
        return response;
    }
}
