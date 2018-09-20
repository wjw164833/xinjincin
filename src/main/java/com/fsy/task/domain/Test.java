package com.fsy.task.domain;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//测评
public class Test {

    private String name ; //测评名称

    private String status;//测评状态

    private int id;
}
