package com.mp.imoocmp.first;

import com.mp.imoocmp.first.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/7 22:05
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ARTest {

    @Test
    public void insert() {
        User user = new User();
        user.setName("刘华");
        user.setAge(28);
        user.setEmail("lh@baomidou.com");
        user.setCreateTime(LocalDateTime.now());

        boolean result = user.insert();
        System.out.println(result);
    }

    @Test
    public void selectById() {
//        User user = new User();
//        User userSelect = user.selectById(1258789972748652546L);
//        System.out.println(user);
//        System.out.println(userSelect);
//        System.out.println(user == userSelect);
        User user = new User();
        user.setId(1258789972748652546L);
        User user1 = user.selectById();
        System.out.println(user1);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(1258789972748652546L);
        user.setName("王丽丽");
        user.setEmail("wll@baomidou.com");
        boolean b = user.updateById();
        System.out.println(b);
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setId(1258789972748652546L);
        boolean b = user.deleteById();
        System.out.println(b);
    }

    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setId(1258794892176494594L);
        user.setName("梧桐我挺");
        user.setEmail("wutong@bmd.com");
        user.setAge(25);
        boolean b = user.insertOrUpdate();
        System.out.println(b);
    }

}
