package com.example.Seulah.controller;

import com.example.Seulah.repository.QuestionSetRepository;
import com.example.Seulah.request.EligibilityQuestionsRequest;
import com.example.Seulah.request.MessageResponse;
import com.example.Seulah.service.EligibilityQuestionsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/EligibilityQuestions")
@CrossOrigin
@Slf4j
public class EligibilityQuestionsController {

    private final EligibilityQuestionsService eligibilityQuestionsService;

    public final QuestionSetRepository questionsSetRepo;

    public EligibilityQuestionsController(EligibilityQuestionsService eligibilityQuestionsService, QuestionSetRepository questionsSetRepo) {
        this.eligibilityQuestionsService = eligibilityQuestionsService;
        this.questionsSetRepo = questionsSetRepo;
    }


    @PostMapping("/save-question")
    public ResponseEntity<MessageResponse> saveQuestion(@RequestBody EligibilityQuestionsRequest eligibilityQuestionsRequest) {
        log.info("Save New Eligibility Question ,{}",eligibilityQuestionsRequest);
        return eligibilityQuestionsService.saveQuestion(eligibilityQuestionsRequest);
    }

    @PostMapping("/updateQuestion")
    public ResponseEntity<MessageResponse> updateQuestion(@RequestBody EligibilityQuestionsRequest eligibilityQuestionsRequest, @RequestParam Long id) {
        log.info("Update Eligibility Question, {} by id ,{}",eligibilityQuestionsRequest,id);
        return eligibilityQuestionsService.updateQuestion(eligibilityQuestionsRequest, id);
    }


    @GetMapping("/getAllQuestions")
    public ResponseEntity<MessageResponse> getQuestion() {
        log.info("Get All Eligibility Question ");
        return eligibilityQuestionsService.getQuestions();

    }

    @GetMapping("/getQuestionById")
    public ResponseEntity<MessageResponse> getQuestionById(@RequestParam Long id) {
        log.info("Get Eligibility Question By Id ,{}",id);
        return eligibilityQuestionsService.getQuestionById(id);
    }

    @DeleteMapping("/delete-question")
    public ResponseEntity<MessageResponse> deleteQuestion(@RequestParam Long id) {
        log.info("Delete Eligibility Question By Id ,{}",id);
        return eligibilityQuestionsService.deleteQuestion(id);
    }

}
