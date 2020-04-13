package com.mp.imoocmp.first.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/4/13 20:47
 * @version: v1.0
 */
@Data
public class User {


    // 主键
    private Long id;

    // 姓名
    private String name;

    // 年龄
    private Integer age;

    // 邮箱
    private String email;

    // 上级id
    private Long managerId;

    // 创建时间
    private LocalDateTime createTime;

}
