package com.example.demo.api;

import com.example.demo.entity.Organizer;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.OrganizerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/organizers")

public class OrganizerRestController {
    private OrganizerService organizerService;

    @GetMapping
    public List<Organizer> getAll() {
        return organizerService.getAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Organizer> getOrganizerByName(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Organizer organizer = organizerService.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found on: " + name));
        return ResponseEntity.ok().body(organizer);
    }

    @PostMapping("/add")
    public void create(@RequestBody Organizer organizer) {
        organizerService.save(organizer);
    }

    @DeleteMapping("/delete/{name}")
    public Map<String, Boolean> delete(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Organizer organizer = organizerService.findById (name)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found on: " + name));
        organizerService.deleteById(name);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
