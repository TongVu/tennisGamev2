package com.example.demo.api;

import com.example.demo.entity.RefereeMatch;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.impl.RefereeMatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RefereeMatchApi.PATH)
public class RefereeMatchResource {
    @Autowired
    private RefereeMatchServiceImpl refereeMatchService;
    public static final String PATH = "/api/refereematchs";

    @GetMapping
    public ResponseEntity<List<RefereeMatch>> getAll(){
        return ResponseEntity.ok(refereeMatchService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefereeMatch> getById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        RefereeMatch refereeMatch = refereeMatchService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        return ResponseEntity.ok(refereeMatch);
    }

    @PostMapping
    public ResponseEntity<RefereeMatch> add(@RequestBody RefereeMatch refereeMatch){
        RefereeMatch createdRefereeMatch = refereeMatchService.save(refereeMatch);
        return ResponseEntity.created(URI.create(RefereeMatchApi.PATH+"/"+createdRefereeMatch.getId())).body(createdRefereeMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefereeMatch> update(@PathVariable(value = "id") Integer id,
                                               @RequestBody RefereeMatch refereeMatchDetails) throws ResourceNotFoundException{
        RefereeMatch refereeMatch = refereeMatchService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        refereeMatch.setId(refereeMatchDetails.getId());
        refereeMatch.setMatch(refereeMatchDetails.getMatch());
        refereeMatch.setReferee(refereeMatchDetails.getReferee());
        RefereeMatch refereeMatchUpdated = refereeMatchService.save(refereeMatch);
        return ResponseEntity.ok(refereeMatchUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RefereeMatch> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException{
        RefereeMatch refereeMatch = refereeMatchService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        refereeMatchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
