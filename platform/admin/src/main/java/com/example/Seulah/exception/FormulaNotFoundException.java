package com.example.Seulah.exception;

public class FormulaNotFoundException extends RuntimeException {

    public FormulaNotFoundException(Long id) {
        super("Formula not found with id: " + id);
    }
}
