package com.example.demo.api;


import com.example.demo.entity.TennisSet;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.dto.TennisSetDto;
import com.example.demo.service.impl.TennisSetServiceImpl;
import com.example.demo.service.mapper.TennisSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TennisSetResource.PATH)
public class TennisSetResource {
    public static final String PATH = "/api/sets";
    @Autowired
    TennisSetServiceImpl setService;

    @GetMapping
    public ResponseEntity<List<TennisSetDto>> getAll(){

        return ResponseEntity.ok(TennisSetMapper.INSTANCE.toDtos(setService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TennisSetDto> getById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        TennisSet tennisSet = setService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        return ResponseEntity.ok(TennisSetMapper.INSTANCE.toDto(tennisSet));
    }

    @PostMapping
    public ResponseEntity<TennisSetDto> create(@RequestBody TennisSet tennisSet){
        TennisSet createTennisSet = setService.save(tennisSet);
        return ResponseEntity.created(URI.create(TennisSetResource.PATH+"/"+ createTennisSet.getId())).body(TennisSetMapper.INSTANCE.toDto(createTennisSet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TennisSetDto> update(@PathVariable(value = "id") Integer id,
                                               @RequestBody TennisSet tennisSetDetails) throws ResourceNotFoundException{

        TennisSet tennisSet = setService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        tennisSet.setMatch(tennisSetDetails.getMatch());
        tennisSet.setSetNumber(tennisSetDetails.getSetNumber());
        tennisSet.setStartTime(tennisSetDetails.getStartTime());
        tennisSet.setEndTime(tennisSetDetails.getEndTime());
        tennisSet.setPlayer1Score(tennisSetDetails.getPlayer1Score());
        tennisSet.setPlayer2Score(tennisSetDetails.getPlayer2Score());

        return ResponseEntity.ok(TennisSetMapper.INSTANCE.toDto(setService.save(tennisSet)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        TennisSet tennisSet = setService.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id not found on " + id));
        setService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
