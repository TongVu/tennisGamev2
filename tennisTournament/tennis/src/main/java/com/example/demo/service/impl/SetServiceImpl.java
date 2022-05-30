package com.example.demo.service.impl;

import com.example.demo.entity.Set;
import com.example.demo.repository.SetRepository;
import com.example.demo.service.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetServiceImpl implements SetService {
    @Autowired
    SetRepository setRepository;

    @Override
    public Set save(Set set) {
        return setRepository.save(set);
    }

    @Override
    public List<Set> getAll() {
        return setRepository.findAll();
    }

    @Override
    public Optional<Set> getById(Integer id) {
        return setRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        setRepository.deleteById(id);
    }
}
