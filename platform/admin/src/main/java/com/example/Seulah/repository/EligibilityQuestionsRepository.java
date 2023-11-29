package com.example.Seulah.repository;

import com.example.Seulah.entity.EligibilityQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligibilityQuestionsRepository extends JpaRepository<EligibilityQuestions,Long> {
    void deleteByQuestion(String question);

    EligibilityQuestions findByQuestion(String question);
}
