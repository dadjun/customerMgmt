package com.customer.service;
import com.github.pagehelper.PageInfo;
import com.customer.model.UserBean;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    int addUser(UserBean user);

    PageInfo<UserBean> findAllUser(int pageNum, int pageSize);

    UserBean findUser(String name);
}