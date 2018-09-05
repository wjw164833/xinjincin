package com.fsy.task.domain;

import lombok.*;

/**
 * 学习任务实体
 */
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeachPlan {
    //任务名称 2016级第三学年第一学期《就业指导》教学计划
    private String taskName;

    //有效时间 2018-08-27至2018-12-20 截止时间：2018-09-01
    private String validTime ;

    //状态 进行中 已结束
    private String status;

    //成绩 null
    private String chengJi;

    //显示具体课程计划的编号 showPlan(9670000047)
    private String showPlanNumber;
}
