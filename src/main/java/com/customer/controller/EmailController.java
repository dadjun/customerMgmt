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
        JSONArray customerList = obj.getJSONArray("list");

        String subject = obj.getString("subject");
        try {
        UserBean userBean = userService.findUser(sender);
        boolean bSuccess = false;
        if (userBean != null && customerList.size() >0) {
            String content =  FileUtil.readMailContent(userBean.getContentPath());
            List<CustomerBean> customerBeans = JSONObject.parseArray(customerList.toJSONString(), CustomerBean.class);
            for ( CustomerBean customerBean: customerBeans) {
                logger.debug(customerBean.getEmail());
                SendMailUtil.setSenderParam(userBean, "text/html");
                bSuccess = SendMailUtil.sendMail(userBean.getEmail(), customerBean.getEmail(), "1", content);
                customerBean.setLastSendDate(new Date());
                customerService.updateCustomer(customerBean);
            }
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
    //JSON形式返回给结果
    //文件只能用POST方式进行传递
    @ResponseBody
    @PostMapping("/uploadFile")
    public Object filesUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("file") MultipartFile file,
                              @RequestParam(name = "csName", required = false)
                                          String userName
                         /*   @RequestParam("user_id") String user_id, @RequestParam("region_name") String region_name,
                            @RequestParam("region_id") Float region_id*/) {
       //设置返回信息的编码格式及类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String callbackName = request.getParameter("callback");
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject resultJson = new JSONObject();
        DataWraped dataResult = new DataWraped();

        // 获取文件名
        String filename = file.getOriginalFilename();

        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 传入的文件保存的路径，如果没有先进行创建文件
               // ConfigUtil configUtil = new ConfigUtil();
                String curDir =System.getProperty("user.dir");
                System.out.print(curDir);
                String filePath = curDir + File.separator + filename;
                File dir = new File(filePath);
              /*  if (!dir.exists()) {
                    dir.mkdirs();
                }*/
                UserBean userBean = new UserBean();
                userBean.setName(userName);
                userBean.setContentPath(filePath);
                int xx= userService.updateUser(userBean);

                // 转存文件，否则所创建的是个文件夹
                file.transferTo(new File(filePath));
                // 获取需要处理的文件
                resultJson.put("data", "成功");
                dataResult.setResultCode(ExceptionCode.ResultCode.NO_ERROR);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("文件转存失败");
                dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
            }
        } else {
            dataResult.setResultCode(ExceptionCode.ResultCode.OP_ERROR);
        }
        return dataResult;
    }
}
