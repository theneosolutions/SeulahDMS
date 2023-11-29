package com.example.Seulah.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormulaRequest {


    private String formulaName;

    private List<String> formula;

    private String operation;
    private Double value;
}