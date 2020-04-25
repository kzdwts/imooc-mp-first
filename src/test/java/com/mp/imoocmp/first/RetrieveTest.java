package com.mp.imoocmp.first;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
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

    @Test
    public void testCondition() {
        String name = "王";
        String email = "";
        condition(name, email);
    }

    public void condition(String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotEmpty(name)) {
//            queryWrapper.likeRight("name", name);
//        }
//        if (StringUtils.isNotEmpty(email)) {
//            queryWrapper.likeRight("email", email);
//        }

        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(email), "email", email);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEntity() {
        User whereUser = new User();
        whereUser.setName("刘红雨");
        whereUser.setAge(32);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>(whereUser);
        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
//        params.put("age", null);
        // queryWrapper.allEq(params, false);
        queryWrapper.allEq((k, v) -> !k.equals("name"), params);

        List<User> userList = userMapper.selectList(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄
     * 并且只取年龄总和小于500的组
     * select avg(age), min(age), max(age) from user
     * group by manager_id
     * having sum(age) < 500
     */
    @Test
    public void selectByWrapperMaps2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("AVG(age) avg_age", "MIN(age) min_age", "MAX(age) max_age");
        queryWrapper.groupBy("manager_id");
        queryWrapper.having("SUM(age)<{0}", 500);

        List<Map<String, Object>> userList = userMapper.selectMaps(queryWrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name").like("name", "雨");

        List<Object> userList = userMapper.selectObjs(queryWrapper);
        userList.forEach(System.out::println);
    }


    @Test
    public void selectByWrapperCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨");

        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Test
    public void selectByWrapperObj() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").gt("age", 40);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void seleteLambda() {
        // 3种方式创建对象
        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();

        // 查询
        lambda.like(User::getName, "雨");
        lambda.lt(User::getAge, 40);
        List<User> userList = userMapper.selectList(lambda);
        userList.forEach(System.out::println);
    }

    /**
     * 姓名为王姓，并且（年龄小于40或邮箱不为空）
     */
    @Test
    public void selectLambda2() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        lambdaQueryWrapper.likeRight(User::getName, "王");
        lambdaQueryWrapper.and(wq -> wq.lt(User::getAge, 40).or().isNotNull(User::getEmail));

        List<User> userList = userMapper.selectList(lambdaQueryWrapper);
        userList.forEach(System.out::println);

    }

    @Test
    public void selectLambda3() {
        List<User> userList = new LambdaQueryChainWrapper<User>(userMapper).likeRight(User::getName, "王").list();
        userList.forEach(System.out::println);
    }

}
