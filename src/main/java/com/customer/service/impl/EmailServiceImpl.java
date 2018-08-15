package com.customer.service.impl;

import com.customer.service.EmailService;
import com.customer.dao.UserDao;
import com.customer.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Override
    public int sendEmail(UserBean user) {

        return userDao.insert(user);
    }

}