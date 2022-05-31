package com.example.demo.service;

import com.example.demo.entity.TennisSet;

import java.util.List;
import java.util.Optional;

public interface TennisSetService {

    TennisSet save(TennisSet tennisSet);

    List<TennisSet> getAll();

    Optional<TennisSet> getById(Integer id);

    void deleteById(Integer id);
}
