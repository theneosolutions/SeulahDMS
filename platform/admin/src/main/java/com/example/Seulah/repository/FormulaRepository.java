package com.example.Seulah.repository;

import com.example.Seulah.entity.Formula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormulaRepository extends JpaRepository<Formula,Long> {
    Formula findByEligibilityQuestionSetId(Long setId);
}
