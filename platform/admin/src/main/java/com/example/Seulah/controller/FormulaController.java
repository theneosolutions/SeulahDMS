package com.example.Seulah.controller;

import com.example.Seulah.request.FormulaRequest;
import com.example.Seulah.request.MessageResponse;
import com.example.Seulah.service.FormulaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formula")
public class FormulaController {

    private final FormulaService formulaService;

    public FormulaController(FormulaService formulaService) {
        this.formulaService = formulaService;
    }


    @PostMapping("create")
    public ResponseEntity<MessageResponse> createFormula(@RequestBody FormulaRequest formulaRequest, @RequestParam Long setId) {
        return formulaService.createCalculation(formulaRequest,setId);

    }

    @GetMapping("get")
    public ResponseEntity<MessageResponse> getFormula( ) {
        return formulaService.getFormula();

    }

    @DeleteMapping("delete")
    public ResponseEntity<MessageResponse> deleteFormula(@RequestParam Long id ) {
        return formulaService.deleteFormula(id);

    }

    @PostMapping("calculateFormula")
    public ResponseEntity<MessageResponse> calculateFormula(@RequestParam Long setId,@RequestBody List<Map<String,Double>> userInput ) {
        return formulaService.calculateFormula(setId,userInput);

    }


}

