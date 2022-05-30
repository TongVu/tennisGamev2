package com.example.demo.repository;

import com.example.demo.entity.SetTennis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetTennisRepository extends JpaRepository<SetTennis, Integer> {
}
