package com.example.demo.service.impl;

import com.example.demo.entity.TennisSet;
import com.example.demo.repository.TennisSetRepository;
import com.example.demo.service.TennisSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TennisSetServiceImpl implements TennisSetService {
    @Autowired
    TennisSetRepository tennisSetRepository;

    @Override
    public TennisSet save(TennisSet tennisSet) {
        return tennisSetRepository.save(tennisSet);
    }

    @Override
    public List<TennisSet> getAll() {
        return tennisSetRepository.findAll();
    }

    @Override
    public Optional<TennisSet> getById(Integer id) {
        return tennisSetRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        tennisSetRepository.deleteById(id);
    }
}
