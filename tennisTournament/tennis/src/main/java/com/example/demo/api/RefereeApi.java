package com.example.demo.api;

import com.example.demo.Exceptions.ResourceNotFound;
import com.example.demo.entity.Referee;
import com.example.demo.service.impl.RefereeServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/referee")
public class RefereeApi {
    @Autowired
    private RefereeServiceImpl refereeService;

    @GetMapping
    public List<Referee> getAll(){
        return refereeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Referee> update(@PathVariable(value = "id") Integer id) throws ResourceNotFound{
        Referee referee = refereeService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        return ResponseEntity.ok().body(referee);
    }

    @PostMapping("/add")
    public Referee add(@RequestBody Referee referee){
        return refereeService.save(referee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Referee> update(@PathVariable(value = "id") Integer id,
                                          @RequestBody Referee refereeDetails) throws ResourceNotFound{
        Referee referee = refereeService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        referee.setId(refereeDetails.getId());
        referee.setName(refereeDetails.getName());
        referee.setAddress(refereeDetails.getAddress());
        referee.setPhoneNumber(refereeDetails.getPhoneNumber());
        Referee refereeUpdated = refereeService.save(referee);
        return ResponseEntity.ok(refereeUpdated);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Integer id) throws ResourceNotFound{
        Referee referee = refereeService.getById(id).orElseThrow(() -> new ResourceNotFound("Id not found on " + id));
        refereeService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
