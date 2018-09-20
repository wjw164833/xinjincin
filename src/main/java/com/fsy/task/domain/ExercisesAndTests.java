package com.fsy.task.domain;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExercisesAndTests {
    private List<Exercise> exercises;

    private List<Test> tests;
}
