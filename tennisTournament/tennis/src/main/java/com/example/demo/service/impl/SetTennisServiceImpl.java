package com.example.demo.service.impl;

import com.example.demo.entity.SetTennis;
import com.example.demo.repository.SetTennisRepository;
import com.example.demo.service.SetTennisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetTennisServiceImpl implements SetTennisService {
    @Autowired
    SetTennisRepository setTennisRepository;

    @Override
    public SetTennis save(SetTennis setTennis) {
        return setTennisRepository.save(setTennis);
    }

    @Override
    public List<SetTennis> getAll() {
        return setTennisRepository.findAll();
    }

    @Override
    public Optional<SetTennis> getById(Integer id) {
        return setTennisRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        setTennisRepository.deleteById(id);
    }
}
