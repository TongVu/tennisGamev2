package com.example.demo.service;

import com.example.demo.entity.Set;


import java.util.List;
import java.util.Optional;

public interface SetService {
    Set save(Set set);
    List<Set> getAll();
    Optional<Set> getById(Integer id);
    void deleteById(Integer id);
}
