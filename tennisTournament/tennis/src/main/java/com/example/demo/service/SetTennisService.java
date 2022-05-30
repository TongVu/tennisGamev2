package com.example.demo.service;

import com.example.demo.entity.SetTennis;

import java.util.List;
import java.util.Optional;

public interface SetTennisService {

    SetTennis save(SetTennis setTennis);

    List<SetTennis> getAll();

    Optional<SetTennis> getById(Integer id);

    void deleteById(Integer id);
}
