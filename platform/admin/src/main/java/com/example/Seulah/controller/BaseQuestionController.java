package com.example.Seulah.controller;

import com.example.Seulah.request.BaseQuestionsRequest;
import com.example.Seulah.request.MessageResponse;
import com.example.Seulah.service.BaseQuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baseQuestion")
public class BaseQuestionController {
    private final BaseQuestionService baseQuestionService;

    public BaseQuestionController(BaseQuestionService baseQuestionService) {
        this.baseQuestionService = baseQuestionService;
    }

    @PostMapping("/save")
    public ResponseEntity<MessageResponse> saveBaseQuestion(@RequestBody BaseQuestionsRequest baseQuestionsRequest){
        return baseQuestionService.saveBaseQuestion(baseQuestionsRequest);

    }
}
