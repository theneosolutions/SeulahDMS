package com.example.Seulah.repository;

import com.example.Seulah.entity.EligibilityQuestionSet;
import com.example.Seulah.entity.EligibilityQuestions;
import com.example.Seulah.entity.QuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EligibilityQuestionSetRepository extends JpaRepository<EligibilityQuestionSet, Long> {

    @Query("SELECT eq FROM EligibilityQuestionSet eq ORDER BY eq.id ASC")
    List<EligibilityQuestionSet> findAllByCustomOrder();
}
