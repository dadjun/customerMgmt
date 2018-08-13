package com.homiest.customer.dao;

import com.homiest.customer.model.UserDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserDao {


    int insert(UserDomain record);



    List<UserDomain> selectUsers();
}