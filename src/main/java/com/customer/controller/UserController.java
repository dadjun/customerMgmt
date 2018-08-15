package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.customer.model.UserBean;
import com.customer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/add")
    public int addUser(UserBean user){
        return userService.addUser(user);
    }

    @ResponseBody
   // @GetMapping("/selectCustomers")
   // @CrossOrigin(origins = {"http://localhost:8081", "null"})
    @PostMapping("/selectUsrs")
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

    @ResponseBody
    @PostMapping("/register")
    public int registerUser(   @RequestParam(name = "jsonData", required = false)
                                           Object jsonData)
    {
        JSONObject obj   =  JSON.parseObject((String)jsonData);
        String email = obj.getString("email");
        String user = obj.getString("user");
        String psw = obj.getString("password");
        UserBean userBean = new UserBean();
        userBean.setEmail(email);
        userBean.setPsw(psw);;
        userBean.setName(user);
        userBean.setMailHost("smtp.qq.com");
        return userService.addUser(userBean);
    }

    @ResponseBody
    @PostMapping("/login")
    public int login(   @RequestParam(name = "jsonData", required = false)
                                       Object jsonData)
    {
        JSONObject obj   =  JSON.parseObject((String)jsonData);

        String user = obj.getString("user");
        String psw = obj.getString("password");
        UserBean userBean = userService.findUser(user);
        if(userBean.getPsw().equals(psw)) {
            return 1;
        }else {
            return 0;
        }
    }

}
