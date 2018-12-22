package com.fsy.task.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
    private String name; //创新创业教育（2017级）
    private String validTime; //2018-11-08 10:05:48 至2018-12-31 11:05:48
    private String status; //进行中  已结束
    private String canKaoStatus; // 	已参考  //未参考
    private String score; //成绩　1.-- 代表无成绩　2.100
    private String url; //试卷url
}
