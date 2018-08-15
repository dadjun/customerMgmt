package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/email")
public class EmailController {

    @Autowired
    private CustomerService userService;

    @ResponseBody
    @PostMapping("/batchSend")
    Object sendEmail(
            @RequestParam(name = "jsonData", required = false)
                    String jsonData
    ){

        //获取列表
        //List<UserBean> customerList = JSON.toJSONString(jsonData);
        //发送
        // 改变数据库状态
        // 返回结果

        JSONObject obj   =  JSON.parseObject((String)jsonData);
        String sender = obj.getString("sender");
        JSONArray list = obj.getJSONArray("list");

        System.out.print(sender);
        return jsonData;

    }
}
