package com.customer.service;
import com.github.pagehelper.PageInfo;
import com.customer.model.CustomerBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface CustomerService {

    int addCustomer(CustomerBean user);

    PageInfo<CustomerBean> findAllCustomers(int pageNum, int pageSize,CustomerBean customer);

   int  updateCustomer(CustomerBean user);

      List<String> selectCustomerCountry();
}
