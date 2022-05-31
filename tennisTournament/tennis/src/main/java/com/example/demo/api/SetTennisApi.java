package com.example.demo.api;


import com.example.demo.entity.SetTennis;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.impl.SetTennisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(SetTennisApi.PATH)
public class SetTennisApi {
    public static final String PATH = "api/sets";
    @Autowired
    SetTennisServiceImpl setService;

    @GetMapping
    public ResponseEntity<List<SetTennis>> getAll(){

        return ResponseEntity.ok(setService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetTennis> getById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        SetTennis setTennis = setService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        return ResponseEntity.ok(setTennis);
    }

    @PostMapping
    public ResponseEntity<SetTennis> create(@RequestBody SetTennis setTennis){
        SetTennis createSetTennis = setService.save(setTennis);
        return ResponseEntity.created(URI.create(SetTennisApi.PATH+"/"+createSetTennis.getId())).body(createSetTennis);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        SetTennis setTennis = setService.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id not found on " + id));
        setService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetTennis> update(@PathVariable(value = "id") Integer id,
                                            @RequestBody SetTennis setTennisDetails) throws ResourceNotFoundException{

        SetTennis setTennis = setService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));

//        setTennis.setId(setTennisDetails.getId());
        setTennis.setMatch(setTennisDetails.getMatch());
        setTennis.setSetNumber(setTennisDetails.getSetNumber());
        setTennis.setStartDate(setTennisDetails.getStartDate());
        setTennis.setEndDate(setTennisDetails.getEndDate());
        setTennis.setPlayer1Score(setTennisDetails.getPlayer1Score());
        setTennis.setPlayer2Score(setTennisDetails.getPlayer2Score());

        return ResponseEntity.ok(setService.save(setTennis));
    }
}
