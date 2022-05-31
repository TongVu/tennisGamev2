package com.example.demo.api;


import com.example.demo.entity.SetTennis;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.dto.SetTennisDto;
import com.example.demo.service.impl.SetTennisServiceImpl;
import com.example.demo.service.mapper.SetTennisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(SetTennisResource.PATH)
public class SetTennisResource {
    public static final String PATH = "/api/sets";
    @Autowired
    SetTennisServiceImpl setService;

    @GetMapping
    public ResponseEntity<List<SetTennisDto>> getAll(){

        return ResponseEntity.ok(SetTennisMapper.INSTANCE.toDtos(setService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SetTennisDto> getById(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        SetTennis setTennis = setService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        return ResponseEntity.ok(SetTennisMapper.INSTANCE.toDto(setTennis));
    }

    @PostMapping
    public ResponseEntity<SetTennisDto> create(@RequestBody SetTennis setTennis){
        SetTennis createSetTennis = setService.save(setTennis);
        return ResponseEntity.created(URI.create(SetTennisResource.PATH+"/"+createSetTennis.getId())).body(SetTennisMapper.INSTANCE.toDto(createSetTennis));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetTennisDto> update(@PathVariable(value = "id") Integer id,
                                            @RequestBody SetTennis setTennisDetails) throws ResourceNotFoundException{

        SetTennis setTennis = setService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        setTennis.setMatch(setTennisDetails.getMatch());
        setTennis.setSetNumber(setTennisDetails.getSetNumber());
        setTennis.setStartTime(setTennisDetails.getStartTime());
        setTennis.setEndTime(setTennisDetails.getEndTime());
        setTennis.setPlayer1Score(setTennisDetails.getPlayer1Score());
        setTennis.setPlayer2Score(setTennisDetails.getPlayer2Score());

        return ResponseEntity.ok(SetTennisMapper.INSTANCE.toDto(setService.save(setTennis)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        SetTennis setTennis = setService.getById(id).orElseThrow(() ->
                new ResourceNotFoundException("Id not found on " + id));
        setService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
