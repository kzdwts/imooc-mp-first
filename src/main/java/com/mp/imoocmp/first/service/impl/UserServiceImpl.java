package com.mp.imoocmp.first.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mp.imoocmp.first.dao.UserMapper;
import com.mp.imoocmp.first.entity.User;
import com.mp.imoocmp.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description:
 * @author: kangyong
 * @date: 2020/5/13 23:17
 * @version: v1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
