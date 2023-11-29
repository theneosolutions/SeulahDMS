package com.example.Seulah.request;

import com.example.Seulah.entity.NumericQuestion;
import com.example.Seulah.entity.OtherQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class BaseQuestionsRequest {

    private List<OtherQuestion> otherQuestionList;
    private List<NumericQuestion> numericQuestionList;

}
