package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.customer.service.CustomerService;
import com.customer.model.CustomerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @PostMapping("/add")
    public int addUser(CustomerBean customer){
        return customerService.addCustomer(customer);
    }

    @ResponseBody
   // @GetMapping("/selectCustomers")
   // @CrossOrigin(origins = {"http://localhost:8081", "null"})
    @PostMapping("/selectCustomers")
    public Object findAllCustomers(
            @RequestParam(name = "jsonData", required = false)
                    Object jsonData,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){

    JSONObject obj   =  JSON.parseObject((String)jsonData);

        pageNum = obj.getInteger("pageNum");
        return customerService.findAllCustomers(pageNum,pageSize);
    }
}
