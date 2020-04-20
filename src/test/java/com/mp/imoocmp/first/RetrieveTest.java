package com.mp.imoocmp.first;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.imoocmp.first.dao.UserMapper;
import com.mp.imoocmp.first.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/4/19 21:46
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RetrieveTest {


    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById() {
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
    }

    @Test
    public void selectByIds() {
        List<Long> idsList = Arrays.asList(1094590409767661570L, 1249700799580221441L, 1251866279933939714L);
        List<User> userList = userMapper.selectBatchIds(idsList);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "尚文杰");
        columnMap.put("age", 25);
        List<User> userList = userMapper.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含“雨”并且年龄小于40
     */
    @Test
    public void selectByWrapper() {
        // 方法一
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("name", "雨");
//        queryWrapper.lt("age", 40);
//        List<User> userList = userMapper.selectList(queryWrapper);

        // 方法二
        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery().like(User::getName, "雨").lt(User::getAge, 40));
        userList.forEach(System.out::println);
    }

    /**
     * 名字中包含“雨”并且年龄大于等于20且小于等于40 并且email不为空
     */
    @Test
    public void selectByWrapper2() {
        // 方法一
//        QueryWrapper<User> queruWrapper = new QueryWrapper<>();
//        queruWrapper.like("name", "雨");
//        queruWrapper.ge("age", 20).le("name", 40);
//        queruWrapper.isNotNull("email");
//        List<User> userList = userMapper.selectList(queruWrapper);

        // 方法二
        List<User> userList = userMapper.selectList(
                Wrappers.<User>lambdaQuery()
                        .like(User::getName, "雨")
                        .between(User::getAge, 20, 40)
                        .isNotNull(User::getEmail));
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓，或者年龄大于等于25，按照年龄降序排序，年靓相同按照id升序排序
     */
    @Test
    public void selectByWrapper3() {
        List<User> userList = userMapper.selectList(
                Wrappers.<User>lambdaQuery()
                        .likeRight(User::getName, "王")
                        .or().ge(User::getAge, 25)
                        .orderByDesc(User::getAge)
                        .orderByAsc(User::getId)
        );
        userList.forEach(System.out::println);
    }

    /**
     * 创建日期为2019年2月14日并且直属上级名字王姓
     */
    @Test
    public void selectByWrapper4() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-02-14")
                .inSql("manager_id", "SELECT id FROM user WHERE name LIKE CONCAT('王', '%')");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     */
    @Test
    public void selectWrapper5() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", "王").and(i -> i.lt("age", 40).or().isNotNull("email"));

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     */
    @Test
    public void selectByWrapper6() {
        QueryWrapper<User> queryWrapper = Wrappers.<User>query();
        queryWrapper.likeRight("name", "王")
                .or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * (年龄小于40或邮箱不为空)并且名字为王姓
     */
    @Test
    public void selectByWrapper7() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 年龄为30、31、34、35
     */
    @Test
    public void selectByWrapper8() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", 30, 31, 34, 35);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     */
    @Test
    public void selectByWrapper9() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("LIMIT 1");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     * 名字中包含“雨”并且年龄小于40
     */
    @Test
    public void selectByWrapperSupper() {
        // 方法一
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨");
        queryWrapper.lt("age", 40);
        queryWrapper.select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
        List<User> userList = userMapper.selectList(queryWrapper);

        userList.forEach(System.out::println);
    }

}
