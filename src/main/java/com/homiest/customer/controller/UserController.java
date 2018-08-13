package com.homiest.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.homiest.customer.service.user.UserService;
import com.homiest.customer.model.UserDomain;
import com.homiest.customer.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/customer")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/add")
    public int addUser(UserDomain user){
        return userService.addUser(user);
    }

    @ResponseBody
   // @GetMapping("/selectCustomers")
   // @CrossOrigin(origins = {"http://localhost:8081", "null"})
    @PostMapping("/selectCustomers")
    public Object findAllUser(
            @RequestParam(name = "jsonData", required = false)
                    Object jsonData,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){

    JSONObject obj   =  JSON.parseObject((String)jsonData);

        pageNum = obj.getInteger("pageNum");
        return userService.findAllUser(pageNum,pageSize);
    }
}
