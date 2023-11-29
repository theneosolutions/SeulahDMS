package com.example.Seulah.service;

import com.example.Seulah.entity.EligibilityQuestionSet;
import com.example.Seulah.entity.EligibilityQuestions;
import com.example.Seulah.entity.QuestionSet;
import com.example.Seulah.repository.EligibilityQuestionSetRepository;
import com.example.Seulah.repository.EligibilityQuestionsRepository;
import com.example.Seulah.repository.QuestionSetRepository;
import com.example.Seulah.request.EligibilityQuestionsRequest;
import com.example.Seulah.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EligibilityQuestionsService {


    private final EligibilityQuestionsRepository eligibilityQuestionsRepository;
    private final QuestionSetRepository questionSetRepository;

    private final EligibilityQuestionSetRepository eligibilityQuestionSetRepository;

    public EligibilityQuestionsService(EligibilityQuestionsRepository eligibilityQuestionsRepository, QuestionSetRepository questionSetRepository, EligibilityQuestionSetRepository eligibilityQuestionSetRepository) {
        this.eligibilityQuestionsRepository = eligibilityQuestionsRepository;
        this.questionSetRepository = questionSetRepository;
        this.eligibilityQuestionSetRepository = eligibilityQuestionSetRepository;
    }


    public ResponseEntity<MessageResponse> saveQuestion(EligibilityQuestionsRequest eligibilityQuestionsRequest) {
        EligibilityQuestions eligibilityQuestions = new EligibilityQuestions();
        eligibilityQuestions.setQuestion(eligibilityQuestionsRequest.getQuestion());
        eligibilityQuestions.setType(eligibilityQuestionsRequest.getType());
        eligibilityQuestions.setHeading(eligibilityQuestionsRequest.getHeading());
        eligibilityQuestions.setOptions(eligibilityQuestionsRequest.getOptions());
        eligibilityQuestions = eligibilityQuestionsRepository.save(eligibilityQuestions);
        return new ResponseEntity<>(new MessageResponse("Question Created Successfully", eligibilityQuestions, false), HttpStatus.CREATED);

    }
    @Transactional
    public ResponseEntity<MessageResponse> deleteQuestion(Long id) {
        Optional<EligibilityQuestions> optionalEligibilityQuestions = eligibilityQuestionsRepository.findById(id);

        if (optionalEligibilityQuestions.isPresent()) {
            EligibilityQuestions eligibilityQuestion = optionalEligibilityQuestions.get();

            // Update the association and remove the EligibilityQuestions instance from the QuestionSet
            List<QuestionSet> questionSetList = questionSetRepository.findByQuestion(eligibilityQuestion.getQuestion());
            questionSetList.forEach(questionSet -> {
                questionSet.setEligibilityQuestionSet(null); // Remove the association
            });
             questionSetRepository.saveAll(questionSetList);

            eligibilityQuestionsRepository.delete(eligibilityQuestion);

            List<EligibilityQuestionSet> eligibilityQuestionSets = eligibilityQuestionSetRepository.findAll();
            return new ResponseEntity<>(new MessageResponse("Success", eligibilityQuestionSets, false), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("EligibilityQuestions not found", null, true), HttpStatus.OK);
        }
    }


    public ResponseEntity<MessageResponse> getQuestions() {
        List<EligibilityQuestions> eligibilityQuestions = eligibilityQuestionsRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", eligibilityQuestions, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getQuestionById(Long id) {
        Optional<EligibilityQuestions> eligibilityQuestions = eligibilityQuestionsRepository.findById(id);
        if (eligibilityQuestions.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", eligibilityQuestions, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found", eligibilityQuestions, false), HttpStatus.OK);

    }

    public ResponseEntity<MessageResponse> updateQuestion(EligibilityQuestionsRequest eligibilityQuestionsRequest, Long id) {

        Optional<EligibilityQuestions> eligibilityQuestions = eligibilityQuestionsRepository.findById(id);
        if (eligibilityQuestions.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("No Record Against This Id", eligibilityQuestions, false), HttpStatus.OK);
        } else {
            List<QuestionSet> questionSetList = questionSetRepository.findByQuestion(eligibilityQuestions.get().getQuestion());

            questionSetList.forEach(questionSet -> questionSet.setQuestion(eligibilityQuestionsRequest.getQuestion()));
            if (eligibilityQuestionsRequest.getQuestion() != null && !eligibilityQuestionsRequest.getQuestion().isEmpty()) {
                eligibilityQuestions.get().setQuestion(eligibilityQuestionsRequest.getQuestion());
            }
            if (eligibilityQuestionsRequest.getType() != null && !eligibilityQuestionsRequest.getType().isEmpty()) {
                eligibilityQuestions.get().setType(eligibilityQuestionsRequest.getType());
            }
            if (eligibilityQuestionsRequest.getHeading() != null && !eligibilityQuestionsRequest.getHeading().isEmpty()) {
                eligibilityQuestions.get().setHeading(eligibilityQuestionsRequest.getHeading());
            }
            if (eligibilityQuestionsRequest.getQuestion() != null && !eligibilityQuestionsRequest.getOptions().isEmpty()) {
                eligibilityQuestions.get().setOptions(eligibilityQuestionsRequest.getOptions());
            }

        }
        EligibilityQuestions eligibilityQuestionsOptional = eligibilityQuestions.get();
        eligibilityQuestionsOptional = eligibilityQuestionsRepository.save(eligibilityQuestionsOptional);
        return new ResponseEntity<>(new MessageResponse("Success", eligibilityQuestionsOptional, false), HttpStatus.OK);
    }

}