package com.homiest.customer.dao;

import com.homiest.customer.model.CustomerBean;
import com.homiest.customer.model.UserDomain;

import java.util.List;

public interface CustomerDao {


    int insert(CustomerBean record);



    List<CustomerBean> selectUsers();
}