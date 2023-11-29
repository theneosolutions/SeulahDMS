package com.example.Seulah.controller;

import com.example.Seulah.request.MessageResponse;
import com.example.Seulah.service.EligibilityQuestionSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/questionSet")
public class EligibilityQuestionSetController {
    private final EligibilityQuestionSetService eligibilityQuestionSetService;

    public EligibilityQuestionSetController(EligibilityQuestionSetService eligibilityQuestionSetService) {
        this.eligibilityQuestionSetService = eligibilityQuestionSetService;
    }


    @PostMapping("/saveSet")
    public ResponseEntity<MessageResponse> saveSet(@RequestParam String setName, @RequestBody List<Long> questionIds) {
        log.info("Save New Question in Set By Question Ids ,{},{}", setName, questionIds);
        return eligibilityQuestionSetService.saveQuestion(setName, questionIds);
    }

    @GetMapping("/getQuestionSetById")
    public ResponseEntity<MessageResponse> getQuestionSetById(@RequestParam Long id) {
        log.info("Get  Question Set By Id ,{}", id);
        return eligibilityQuestionSetService.getQuestionById(id);

    }


    @GetMapping("/getAllDecision")
    public ResponseEntity<MessageResponse> getAllDecision() {
        log.info("Get  Question Set By Id ");
        return eligibilityQuestionSetService.getAllDecision();

    }

    @GetMapping("/getAllQuestionSet")
    public ResponseEntity<MessageResponse> getQuestionSet() {
        log.info("Get All Question Sets ");
        return eligibilityQuestionSetService.getQuestions();

    }

    @GetMapping("/getQuestionSetByNumericAndString")
    public ResponseEntity<MessageResponse> getQuestionSetByNumericAndString(@RequestParam Long id,@RequestParam Boolean forUser) {
        log.info("Get All Question Sets By Numeric And String By id {} ",id);
        if (forUser.equals(Boolean.TRUE)) {
            return eligibilityQuestionSetService.getQuestionSetByNumericAndString(id,true);
        }

        else {
            return eligibilityQuestionSetService.getQuestionSetByNumericAndString(id,false);
        }

    }

    @DeleteMapping("/deleteQuestionSet")
    public ResponseEntity<MessageResponse> deleteQuestionSet(@RequestParam Long id) {
        log.info("Delete Question Sets,{}", id);
        return eligibilityQuestionSetService.deleteQuestion(id);
    }

    @PostMapping("/updateAnswer")
    public ResponseEntity<MessageResponse> updateAnswer(@RequestParam(required = false) String setName, @RequestParam Long id, @RequestParam Long questionId, @RequestBody List<String> answers) {
        log.info("Update Answer in Question Sets By Question Id ,{} and Set Id {},Answer  ,{}", questionId, id, answers);
        return eligibilityQuestionSetService.updateAnswer(setName, id, questionId, answers);
    }

    @GetMapping("/getQuestionByIdAndSetId")
    public ResponseEntity<MessageResponse> getQuestionByIdAndSetId(@RequestParam Long questionId, @RequestParam Long setId) {
        log.info("Get Set Question By Question Id and Set Id ,{} ,{}", questionId, setId);
        return eligibilityQuestionSetService.getQuestionByIdAndSetId(questionId, setId);
    }

    @GetMapping("/getFormula")
    public ResponseEntity<MessageResponse> getFormulaByEligibilityQuestionSetId(@RequestParam Long eligibilityQuestionSetId) {

        return eligibilityQuestionSetService.getFormulaByEligibilityQuestionSetId(eligibilityQuestionSetId);

    }
}
