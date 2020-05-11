package com.mp.imoocmp.first;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.mp.imoocmp.first.dao.UserMapper;
import com.mp.imoocmp.first.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/4/27 21:44
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void updateById() {
        User user = new User();
//        user.setId(1088248166370832385L);
        user.setName("王天风");
        user.setAge(26);
        int rows = userMapper.updateById(user);
        System.out.println(rows);
    }

    @Test
    public void updateByWrapper() {
        User whereUser = new User();
        whereUser.setName("李艺伟");

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>(whereUser);
        updateWrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setEmail("lyw2019@baomidou.com");
        user.setAge(29);
        int rows = userMapper.update(user, updateWrapper);
        System.out.println(rows);
    }

    @Test
    public void updateWrapper2() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "李艺伟").set("age", 30).set("email", "baomidou");
        int rows = userMapper.update(null, updateWrapper);
        System.out.println(rows);
    }

    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = Wrappers.<User>lambdaUpdate();
        lambdaUpdateWrapper.eq(User::getName, "李艺伟").eq(User::getAge, 30).set(User::getAge, 31);
        int rows = userMapper.update(null, lambdaUpdateWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapperLambdaChain() {
        boolean rows = new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getName, "李艺伟").set(User::getAge, 32).update();
        System.out.println(rows ? "操作成功" : "操作失败");
    }

}
