package com.example.demo.repository;

import com.example.demo.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface SetRepository extends JpaRepository<Set, Integer> {
}
