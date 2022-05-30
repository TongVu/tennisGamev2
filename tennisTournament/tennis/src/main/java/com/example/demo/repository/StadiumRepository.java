package com.example.demo.repository;

import com.example.demo.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium , Integer> {
    List<Stadium> getAll();

    void saveStadium(Stadium stadium);

    Optional<Stadium> findStadiumById(Integer id);

    void deleteStadiumById(Integer id);

    Optional<Stadium> findById();
}
