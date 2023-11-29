package com.example.Seulah.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EligibilityQuestionsRequest {
    private String heading;

    private String question;

    private String type;

    private List<String> options;

}
