package com.fsy.task.domain;

import lombok.*;

/**
 * 具体课程
 */

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    //课程名称 提高就业能力（一）
    private String name ;

    //课程状态 已完成（44 / 44）
    private String status;

    //课后习题正确率  	100.0%
    private String rightPercent;

    //课后习题编号
    private String number;
}
