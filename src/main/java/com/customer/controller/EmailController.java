package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.customer.model.CustomerBean;
import com.customer.model.UserBean;
import com.customer.service.CustomerService;
import com.customer.service.UserService;
import com.customer.util.DataWraped;
import com.customer.util.ExceptionCode;
import com.customer.util.FileUtil;
import com.customer.util.SendMailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="/email")
public class EmailController {

    Logger logger = LoggerFactory.getLogger(EmailController.class);
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

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
        DataWraped dataResult = new DataWraped();
        JSONObject obj   =  JSON.parseObject((String)jsonData);
        String sender = obj.getString("sender");
        //JSONArray customerList = obj.getJSONArray("list");
        CustomerBean customerBean = obj.getObject("list",CustomerBean.class);

        String subject = obj.getString("subject");
        try {
        UserBean userBean = userService.findUser(sender);
        boolean bSuccess = false;
        if (userBean != null && customerBean != null) {
            String content =  FileUtil.readMailContent(userBean.getContentPath());
           // List<CustomerBean> customerBeans = JSONObject.parseArray(customerList.toJSONString(), CustomerBean.class);
           // for ( CustomerBean customerBean: customerBeans) {
                logger.debug(customerBean.getEmail());
                SendMailUtil.setSenderParam(userBean, "text/html");
                bSuccess = SendMailUtil.sendMail(userBean.getEmail(), customerBean.getEmail(), subject, content);
                JSONObject resultObj = new JSONObject();
                if (bSuccess) {
                    customerBean.setLastSendDate(new Date());
                    customerService.updateCustomer(customerBean);
                    resultObj.put("id",customerBean.getId());
                    resultObj.put("status",bSuccess);
                    dataResult.setData(resultObj);
                }
            //}
        }
        if (!bSuccess) {
            dataResult.setResultCode(ExceptionCode.ResultCode.OP_ERROR);
        }
        System.out.print(sender);
        }catch (Exception e){
            dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
        }
        return dataResult;
    }

}
