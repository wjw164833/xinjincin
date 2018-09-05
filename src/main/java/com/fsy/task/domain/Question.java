package com.fsy.task.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private String id;

    private String answer;

    private List<QuestionOption> options;

    private String title;

    private String userId ;

    private String schoolId;

    @Override
    public boolean equals(Object question ){
        if(question instanceof Question){
            Question obj = (Question) question;
            if(obj.getTitle().equals(this.getTitle())){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
