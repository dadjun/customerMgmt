package com.customer.dao;

import com.customer.model.CustomerBean;

import java.util.List;

public interface CustomerDao {


    int insert(CustomerBean record);



    List<CustomerBean> selectCustomers(CustomerBean customerBean);

    int updateCustomer(CustomerBean customerBean);

    List<String> selectCustomerCountry();
}