package com.homiest.customer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homiest.customer.dao.CustomerDao;
import com.homiest.customer.dao.UserDao;
import com.homiest.customer.model.CustomerBean;
import com.homiest.customer.model.UserDomain;
import com.homiest.customer.service.CustomerService;
import com.homiest.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;//这里会报错，但是并不会影响

    @Override
    public int addUser(CustomerBean user) {

        return customerDao.insert(user);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public PageInfo<CustomerBean> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<CustomerBean> userDomains = customerDao.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }
}