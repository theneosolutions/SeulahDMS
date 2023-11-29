package com.example.Seulah.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EligibilityQuestionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "eligibilityQuestionSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionSet> questions = new ArrayList<>();

    @OneToOne(mappedBy = "eligibilityQuestionSet", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Formula formula;

}
