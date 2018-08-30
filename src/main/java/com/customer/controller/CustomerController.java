package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.customer.service.CustomerService;
import com.customer.model.CustomerBean;
import com.customer.util.DataWraped;
import com.customer.util.ExceptionCode;
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
                    Object jsonData  ){

        JSONObject obj   =  JSON.parseObject((String)jsonData);

       Integer  pageNum = obj.getInteger("pageNum");
        Integer pageSize = obj.getInteger("pageSize");
        String country = obj.getString("country");
        String email = obj.getString("email");
        CustomerBean customer = new CustomerBean();
        customer.setCountry(country);;
        customer.setEmail(email);
        return customerService.findAllCustomers(pageNum,pageSize,customer);
    }

    @ResponseBody
    @PostMapping("/selectCountryList")
    public Object selectCountryList(
            @RequestParam(name = "jsonData", required = false)
                    Object jsonData,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){

        return customerService.selectCustomerCountry();
    }

    @ResponseBody
    @PostMapping("/deleteCustomer")
    public Object deleteCustomer(@RequestParam(name = "jsonData", required = false)
                                        Object jsonData){
        DataWraped result  = new DataWraped();
        try {
            JSONObject obj   =  JSON.parseObject((String)jsonData);
            String name = obj.getString("name");
            int id = obj.getInteger("id");
            CustomerBean customer = new CustomerBean();
            customer.setName(name);
            customer.setId(id);
            int delete = customerService.deleteCustomer(customer);
        result.setData(delete);
        }catch (Exception ex) {
            result.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
            result.setResultMsg(ex.getMessage());
        }
        return result;
    }
}
