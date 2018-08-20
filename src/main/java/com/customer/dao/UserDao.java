package com.customer.dao;

import com.customer.model.UserBean;

import java.util.List;

public interface UserDao {


    int insert(UserBean record);


    List<UserBean> selectUsers();

    UserBean selectUser(String name);
    int updateUser(UserBean name);
}