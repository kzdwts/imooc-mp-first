package com.mp.imoocmp.first;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.imoocmp.first.dao.UserMapper;
import com.mp.imoocmp.first.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/6 21:55
 * @version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById1() {
        int rows = userMapper.deleteById(1251866279933939714L);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1249697482112311298L);
        int rows = userMapper.deleteByMap(map);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByIds() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1249705590704373762L, 1249705590704373763L));
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        lambdaQueryWrapper.eq(User::getAge, 27).or().gt(User::getAge, 41);
        int rows = userMapper.delete(lambdaQueryWrapper);
        System.out.println("删除条数：" + rows);
    }

}
