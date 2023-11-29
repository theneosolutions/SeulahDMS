package com.example.Seulah.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String question;

    @ManyToOne
    @JoinColumn(name = "eligibility_question_set_id")
    @JsonIgnore
    private EligibilityQuestionSet eligibilityQuestionSet;

    @ElementCollection
    private List<String> answer;

}