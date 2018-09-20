package com.fsy.task.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {

    private String username;

    private String password;

    private String schoolToken ;

    private String schoolId;

    private String schoolCode; //学校编码

    private String userId;

    private String nickName; //昵称
}
