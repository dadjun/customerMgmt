package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.customer.model.UserBean;
import com.customer.service.UserService;
import com.customer.util.DataWraped;
import com.customer.util.ExceptionCode;
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
    public int addUser(UserBean user) {
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
                    int pageSize) {

        JSONObject obj = JSON.parseObject((String) jsonData);

        pageNum = obj.getInteger("pageNum");
        return userService.findAllUser(pageNum, pageSize);
    }

    @ResponseBody
    @PostMapping("/register")
    public Object registerUser(@RequestParam(name = "jsonData", required = false)
                                       Object jsonData) {
        JSONObject obj = JSON.parseObject((String) jsonData);
        DataWraped dataResult = new DataWraped();
        try {
            String email = obj.getString("email");
            String user = obj.getString("user");
            String psw = obj.getString("password");
            String mailPsw = obj.getString("mailPsw");
            UserBean userBean = new UserBean();
            userBean.setEmail(email);
            userBean.setPsw(psw);
            ;
            userBean.setName(user);
            userBean.setMailPsw(mailPsw);
            userBean.setMailHost("smtp.exmail.qq.com");
            UserBean existUser = userService.findUser(user);
            if (existUser != null && user.equals(existUser.getName())) {
                dataResult.setResultMsg("该用户名已存在");
                dataResult.setResultCode(ExceptionCode.ResultCode.OP_ERROR);
            } else {
                userService.addUser(userBean);
                dataResult.setResultMsg("注册成功");
            }
        } catch (Exception ex) {
            dataResult.setResultMsg(ex.getMessage().toString());
            dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
        }
        return dataResult;
    }

    @ResponseBody
    @PostMapping("/login")
    public Object login(@RequestParam(name = "jsonData", required = false)
                                Object jsonData) {
        JSONObject obj = JSON.parseObject((String) jsonData);

        String user = obj.getString("user");
        String psw = obj.getString("password");
        DataWraped dataResult = new DataWraped();
        try {
            UserBean userBean = userService.findUser(user);
            if (userBean == null )  {
                dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
                dataResult.setResultMsg("账户名不存在");
            }else if (userBean.getPsw().equals(psw)){
                dataResult.setResultMsg("登录成功");
                dataResult.setResultCode(ExceptionCode.ResultCode.NO_ERROR);
            } else {
                dataResult.setResultMsg("密码错误，请重新输入");
                dataResult.setResultCode(ExceptionCode.ResultCode.OP_ERROR);
            }
        } catch (Exception ex) {
            dataResult.setResultMsg(ex.getMessage().toString());
            dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
        }
        return dataResult;
    }
}