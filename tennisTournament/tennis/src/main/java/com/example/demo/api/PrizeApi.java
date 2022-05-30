package com.example.demo.api;


import com.example.demo.entity.Prize;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prize")
public class PrizeApi {
    @Autowired
    private PrizeService prizeService;
    @GetMapping
    public List<Prize> getAll(){
        return prizeService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity <Prize> getById(@PathVariable(value = "id")Integer id)throws ResourceNotFoundException {
        Prize prize=prizeService.getById(id)
                .orElseThrow(()->new ResourceNotFoundException("Id does not exist "+id));
        return ResponseEntity.ok().body(prize);
    }
    @PostMapping("/add")
    public Prize add(@RequestBody Prize prize){
        return prizeService.save(prize);
    }
    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFoundException {
        Prize prize = prizeService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id does not exist " + id));
        prizeService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Prize> update(@PathVariable(value = "id") Integer id,
                                        @RequestBody Prize prizeDetails) throws ResourceNotFoundException{
        Prize prize = prizeService.getById(id).orElseThrow(() -> new ResourceNotFoundException("Id does not exist " + id));
        prize.setId(prizeDetails.getId());
        prize.setPrizeType(prizeDetails.getPrizeType());
        prize.setPrizeAmount(prizeDetails.getPrizeAmount());
        Prize prizeUpdated = prizeService.save(prize);
        return ResponseEntity.ok(prizeUpdated);
    }
}
