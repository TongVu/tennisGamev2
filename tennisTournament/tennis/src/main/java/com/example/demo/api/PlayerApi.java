package com.example.demo.api;

import com.example.demo.entity.Player;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(PlayerApi.PATH)
public class PlayerApi {
    @Autowired
    private PlayerService playerService;
    public static final String PATH = "/api/players";

    @GetMapping
    public ResponseEntity<List<Player>> getAll(){
        return ResponseEntity.ok(playerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> update(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Player player = playerService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<Player> add(@RequestBody Player player){
        Player createdPlayer = playerService.save(player);
        return ResponseEntity.created(URI.create(PlayerApi.PATH+"/"+ player.getId())).body(player);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> update(@PathVariable(value = "id") Integer id,
                                          @RequestBody Player playerDetail) throws ResourceNotFoundException{
        Player player = playerService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        player.setFirstName(playerDetail.getFirstName());
        player.setGender(playerDetail.getGender());
        player.setAddress(playerDetail.getAddress());
        player.setLastName(playerDetail.getLastName());
        player.setPhoneNumber(playerDetail.getPhoneNumber());
        Player playerUpdated = playerService.save(player);
        return ResponseEntity.ok(playerUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Player player = playerService.getById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id not found on " + id));
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
