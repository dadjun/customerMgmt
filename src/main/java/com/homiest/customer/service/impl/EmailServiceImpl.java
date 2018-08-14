package com.homiest.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homiest.customer.dao.UserDao;
import com.homiest.customer.model.UserDomain;
import com.homiest.customer.service.EmailService;
import com.homiest.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Override
    public int sendEmail(UserDomain user) {

        return userDao.insert(user);
    }

}