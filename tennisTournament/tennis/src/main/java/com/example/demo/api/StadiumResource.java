package com.example.demo.api;

import com.example.demo.entity.Stadium;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.StadiumService;
import com.example.demo.service.dto.StadiumDto;
import com.example.demo.service.mapper.StadiumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(StadiumResource.PATH)
public class StadiumResource {
    public static final String PATH ="/api/stadium";

    @Autowired
    private StadiumService stadiumService;

    @GetMapping ResponseEntity<List<StadiumDto>> getAll(){
        return ResponseEntity.ok(StadiumMapper.INSTANCE.toDtos(stadiumService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stadium>getStadiumById(@PathVariable(value="id") Integer stadiumId) throws ResourceNotFoundException{
        Stadium stadium = stadiumService.findStadiumById(stadiumId).orElseThrow(()-> new ResourceNotFoundException("Stadium not found on this " + stadiumId ));
        return ResponseEntity.ok(stadium);
    }


    @PostMapping()
    public ResponseEntity<Stadium> create(@RequestBody Stadium stadium){
        Stadium createdStadium = stadiumService.saveStadium(stadium);
        return ResponseEntity.created(URI.create(StadiumResource.PATH + "/" + createdStadium.getStadiumId())).body(createdStadium);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Stadium> deleteStadium(@PathVariable Integer stadiumId) throws ResourceNotFoundException {
        Stadium stadium = stadiumService.findStadiumById(stadiumId)
                .orElseThrow(()-> new ResourceNotFoundException("Stadium not found on this " + stadiumId ));
        stadiumService.deleteStadiumById(stadiumId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Stadium> update(@PathVariable(value = "id") Integer stadiumId ,@RequestBody Stadium stadiumDetail ) throws ResourceNotFoundException {
        Stadium editStadium = stadiumService.findStadiumById(stadiumId)
                .orElseThrow(()-> new ResourceNotFoundException("Stadium not found on: " + stadiumId));
        editStadium.setStadiumName(stadiumDetail.getStadiumName());
        editStadium.setStadiumAddress(stadiumDetail.getStadiumAddress());
        editStadium.setSeatCapacity(stadiumDetail.getSeatCapacity());
        editStadium.setStadiumLocation((stadiumDetail.getStadiumLocation()));
        return ResponseEntity.ok(stadiumService.saveStadium(editStadium));
    }



}
