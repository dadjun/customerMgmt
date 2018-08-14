package com.homiest.customer.service;
import com.github.pagehelper.PageInfo;
import com.homiest.customer.model.UserDomain;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface EmailService {

    int sendEmail(UserDomain user);

   // PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);
}