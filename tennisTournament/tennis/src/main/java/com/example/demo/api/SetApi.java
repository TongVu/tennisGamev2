package com.example.demo.api;


import com.example.demo.Exceptions.ResourceNotFound;
import com.example.demo.entity.Set;
import com.example.demo.service.impl.SetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/set")
public class SetApi {
    @Autowired
    SetServiceImpl setService;

    @GetMapping
    public List<Set> getAll(){
        return setService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Set> getById(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Set set = setService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        return ResponseEntity.ok().body(set);
    }

    @PutMapping("/add")
    public Set add(@RequestBody Set set){
        return setService.save(set);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Set> update(@PathVariable(value = "id") Integer id,
                                      @RequestBody Set setDetails) throws ResourceNotFound{
        Set set = setService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        set.setId(setDetails.getId());
        set.setMatch(setDetails.getMatch());
        set.setSetNumber(setDetails.getSetNumber());
        set.setStartDate(setDetails.getStartDate());
        set.setEndDate(setDetails.getEndDate());
        set.setPlayer1Score(setDetails.getPlayer1Score());
        set.setPlayer2Score(setDetails.getPlayer2Score());
        Set setUpdated = setService.save(set);
        return ResponseEntity.ok(setUpdated);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFound {
        Set set = setService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        setService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
