package com.example.Seulah.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Formula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formulaName;
    @ElementCollection
    private List<String> formula;

    private String operation;
    private Double value;

    private Boolean isEligible;

    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "eligibility_question_set_id")
    private EligibilityQuestionSet eligibilityQuestionSet;

}
