package com.fsy.task.domain;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExercisesAndTests {
    //课程
    private List<Exercise> exercises;

    //测评
    private List<Test> tests;

    //考试
    private List<String> examIds;


}
