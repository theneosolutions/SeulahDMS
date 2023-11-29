package com.example.Seulah.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EligibilityQuestionSetRequest {
    @NotNull
    @NotBlank
    private String name;
}
