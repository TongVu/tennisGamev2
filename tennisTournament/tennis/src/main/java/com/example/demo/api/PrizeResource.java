package com.example.demo.api;

import com.example.demo.entity.Prize;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(PrizeApi.PATH)
public class PrizeResource {
    @Autowired
    private PrizeService prizeService;
    public static final String PATH = "/api/prizes";

    @GetMapping
    public ResponseEntity<List<Prize>> getAll() {
        return ResponseEntity.ok(prizeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prize> getById(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Prize prize = prizeService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist " + id));
        return ResponseEntity.ok(prize);
    }

    @PostMapping
    public ResponseEntity<Prize> add(@RequestBody Prize prize) {
        Prize prizeCreated = prizeService.save(prize);
        return ResponseEntity.created(URI.create(PrizeApi.PATH + "/" + prizeCreated.getId())).body(prizeCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Prize prize = prizeService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id does not exist " + id));
        prizeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Prize> update(@PathVariable(value = "id") Integer id,
                                        @RequestBody Prize prizeDetails) throws ResourceNotFoundException {
        Prize prize = prizeService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id does not exist " + id));
        prize.setId(prizeDetails.getId());
        prize.setPrizeType(prizeDetails.getPrizeType());
        prize.setPrizeAmount(prizeDetails.getPrizeAmount());
        Prize prizeUpdated = prizeService.save(prize);
        return ResponseEntity.ok(prizeUpdated);
    }
}