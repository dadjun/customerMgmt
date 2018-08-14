package com.homiest.customer.service;
import com.github.pagehelper.PageInfo;
import com.homiest.customer.model.CustomerBean;
import com.homiest.customer.model.UserDomain;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface CustomerService {

    int addUser(CustomerBean user);

    PageInfo<CustomerBean> findAllUser(int pageNum, int pageSize);
}