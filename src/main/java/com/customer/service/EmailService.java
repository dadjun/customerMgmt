package com.customer.service;
import com.customer.model.UserBean;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface EmailService {

    int sendEmail(UserBean user);

   // PageInfo<UserBean> findAllUser(int pageNum, int pageSize);
}