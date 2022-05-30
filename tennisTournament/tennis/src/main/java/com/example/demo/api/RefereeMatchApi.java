package com.example.demo.api;

import com.example.demo.Exceptions.ResourceNotFound;
import com.example.demo.entity.RefereeMatch;
import com.example.demo.service.impl.RefereeMatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/refereematch")
public class RefereeMatchApi {
    @Autowired
    private RefereeMatchServiceImpl refereeMatchService;

    @GetMapping
    public List<RefereeMatch> getAll(){
        return refereeMatchService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefereeMatch> getById(@PathVariable(value = "id") Integer id) throws ResourceNotFound{
        RefereeMatch refereeMatch = refereeMatchService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        return ResponseEntity.ok().body(refereeMatch);
    }

    @PostMapping("/add")
    public RefereeMatch add(@RequestBody RefereeMatch refereeMatch){
        return refereeMatchService.save(refereeMatch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RefereeMatch> update(@PathVariable(value = "id") Integer id,
                                               @RequestBody RefereeMatch refereeMatchDetails) throws ResourceNotFound{
        RefereeMatch refereeMatch = refereeMatchService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        refereeMatch.setId(refereeMatchDetails.getId());
        refereeMatch.setMatch(refereeMatchDetails.getMatch());
        refereeMatch.setReferee(refereeMatchDetails.getReferee());
        RefereeMatch refereeMatchUpdated = refereeMatchService.save(refereeMatch);
        return ResponseEntity.ok(refereeMatchUpdated);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFound{
        RefereeMatch refereeMatch = refereeMatchService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        refereeMatchService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }
}
