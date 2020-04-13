package com.mp.imoocmp.first.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("mp_user")
public class User {


    // 主键
    @TableId
    private Long userId;

    // 姓名
    @TableField("name")
    private String realName;

    // 年龄
    private Integer age;

    // 邮箱
    private String email;

    // 上级id
    private Long managerId;

    // 创建时间
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String remark;

}
