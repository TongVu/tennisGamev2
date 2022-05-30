package com.example.demo.api;

import com.example.demo.entity.Organizer;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.service.OrganizerService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(OrganizerApi.PATH)

public class OrganizerApi {
    @Autowired
    private OrganizerService organizerService;

    public static final String PATH = "/api/organizers";

    @GetMapping
    public ResponseEntity<List<Organizer>> getAll() {
        return ResponseEntity.ok(organizerService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Organizer> getOrganizerByName(@PathVariable(value = "name") String name)
            throws ResourceNotFoundException {
        Organizer organizer = organizerService.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found on: " + name));
        return ResponseEntity.ok(organizer);
    }

    @PostMapping
    public ResponseEntity<Organizer> create(@RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.save(organizer);
        return ResponseEntity.created(URI.create(OrganizerApi.PATH + "/" + createdOrganizer.getName())).body(createdOrganizer);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable(value = "name") String name) throws ResourceNotFoundException {
        Organizer organizer = organizerService.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found on: " + name));
        organizerService.deleteById(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{name}")
    public ResponseEntity<Organizer> update(@PathVariable(value = "name") String name, @RequestBody Organizer organizerDetail) throws ResourceNotFoundException {
        Organizer editOrganizer = organizerService.findById(name)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer not found on: " + name));
        editOrganizer.setAddress(organizerDetail.getAddress());
        editOrganizer.setPhoneNumber(organizerDetail.getPhoneNumber());
        return ResponseEntity.ok(organizerService.save(editOrganizer));
    }
}
