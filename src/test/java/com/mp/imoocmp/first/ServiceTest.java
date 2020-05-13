package com.mp.imoocmp.first;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.imoocmp.first.entity.User;
import com.mp.imoocmp.first.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/13 23:21
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getOne() {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);
        System.out.println(user);
    }

    @Test
    public void saveBatch() {
        User u1 = new User();
        u1.setName("王丽1");
        u1.setAge(18);

        User u2 = new User();
        u2.setName("王丽2");
        u2.setAge(19);

        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);

        boolean b = userService.saveBatch(userList);
        System.out.println(b);
    }

    @Test
    public void chain() {
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }

    @Test
    public void chain1() {
        boolean b = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println(b);
    }

    @Test
    public void chain2() {
        boolean b = userService.lambdaUpdate().eq(User::getAge, 24).remove();
        System.out.println(b);
    }
}
