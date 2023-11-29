package com.example.Seulah.request;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class QuestionSetResponse {
    private Long setId;
    private Object formula;
    private List<QuestionValuePair> numericQuestions;
    private List<QuestionValuePair> textQuestions;
    private Set<QuestionValuePair> otherQuestions;

    // Constructors, getters, and setters

    // Example constructor
    public QuestionSetResponse(Long setId,Object formula) {
        this.setId = setId;
        this.formula = formula;
        this.numericQuestions = new ArrayList<>();
        this.textQuestions = new ArrayList<>();
        this.otherQuestions = new HashSet<>();
    }
}