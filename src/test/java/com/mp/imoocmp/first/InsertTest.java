package com.mp.imoocmp.first;

import com.mp.imoocmp.first.dao.UserMapper;
import com.mp.imoocmp.first.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/4/13 21:49
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setName("化工系");
        user.setAge(26);
        user.setEmail("xn@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
//        user.setRemark("我是一个备注信息");
        int rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void insert02() {
        User user = new User();
//        user.setId(123456789L);
        user.setName("钱大富1");
        user.setEmail("wll@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        int rows = userMapper.insert(user);
        System.out.println("影响行数：" + rows);
        System.out.println("自增主键：" + user.getId());

    }

}
