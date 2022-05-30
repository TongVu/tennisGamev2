package com.example.demo.service;

import com.example.demo.entity.Organizer;

import java.util.List;
import java.util.Optional;

public interface OrganizerService {
    List<Organizer> getAll();

    void save(Organizer organizer);

    Optional<Organizer> findById(String name);

    void deleteById(String name);

}
