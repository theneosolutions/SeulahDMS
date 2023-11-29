package com.example.Seulah.service;

import com.example.Seulah.entity.NumericQuestion;
import com.example.Seulah.entity.OtherQuestion;
import com.example.Seulah.repository.NumericQuestionRepository;
import com.example.Seulah.repository.OtherQuestionRepository;
import com.example.Seulah.request.BaseQuestionsRequest;
import com.example.Seulah.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseQuestionService {
    private final NumericQuestionRepository numericQuestionRepository;

    private final OtherQuestionRepository otherQuestionRepository;

    public BaseQuestionService(NumericQuestionRepository numericQuestionRepository, OtherQuestionRepository otherQuestionRepository) {
        this.numericQuestionRepository = numericQuestionRepository;
        this.otherQuestionRepository = otherQuestionRepository;
    }

    public ResponseEntity<MessageResponse> saveBaseQuestion(BaseQuestionsRequest baseQuestionsRequest) {
        List<OtherQuestion> otherQuestions = new ArrayList<>();
        baseQuestionsRequest.getOtherQuestionList().forEach(other -> {
            OtherQuestion otherQuestion = new OtherQuestion();
            otherQuestion.setQuestion(other.getQuestion());
            otherQuestion.setAnswer(other.getAnswer());
            otherQuestions.add(otherQuestion);
        });

        List<NumericQuestion> numericQuestions = new ArrayList<>();
        baseQuestionsRequest.getNumericQuestionList().forEach(numeric -> {
            NumericQuestion numericQuestion = new NumericQuestion();
            numericQuestion.setQuestion(numeric.getQuestion());
            numericQuestion.setAnswer(numeric.getAnswer());
            numericQuestions.add(numericQuestion);
        });

        List<OtherQuestion> savedOtherQuestions = otherQuestionRepository.saveAll(otherQuestions);
        List<NumericQuestion> savedNumericQuestions = numericQuestionRepository.saveAll(numericQuestions);

        Map<String, Object> map = new HashMap<>();
        map.put("numericQuestions", savedNumericQuestions);
        map.put("otherQuestions", savedOtherQuestions);

        return new ResponseEntity<>(new MessageResponse("Success", map, false), HttpStatus.CREATED);
    }

}
