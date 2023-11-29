package com.example.Seulah.request;

import com.example.Seulah.entity.EligibilityQuestions;

import java.util.*;

public class QuestionValuePair {
    private EligibilityQuestions eligibilityQuestions;
    private final Object answer;

    private Object formula;

    public Object getFormula() {
        return formula;
    }

    public QuestionValuePair(EligibilityQuestions eligibilityQuestions, Object answer) {
        this.eligibilityQuestions = eligibilityQuestions;
        this.answer = answer;

    }

    // Implement equals() and hashCode() for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionValuePair that = (QuestionValuePair) o;
        return Objects.equals(eligibilityQuestions, that.eligibilityQuestions) && Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eligibilityQuestions, answer);
    }

    // Getters for eligibilityQuestions and answer
    public EligibilityQuestions getEligibilityQuestions() {
        return eligibilityQuestions;
    }

    public Object getAnswer() {
        return answer;
    }
}
