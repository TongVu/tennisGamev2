package com.example.demo.service;

import com.example.demo.entity.Stadium;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StadiumService {
    List<Stadium> getAll();


    void saveStadium(Stadium stadium);

    Optional<Stadium> findStadiumById(Integer id);

    void deleteStadiumById(Integer id);


    Optional<Stadium> getById(Integer id);
}
