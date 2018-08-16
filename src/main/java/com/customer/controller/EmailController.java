package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.customer.model.UserBean;
import com.customer.service.CustomerService;
import com.customer.service.UserService;
import com.customer.util.DataWraped;
import com.customer.util.ExceptionCode;
import com.customer.util.FileUtil;
import com.customer.util.SendMailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping(value="/email")
public class EmailController {

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

        JSONObject obj   =  JSON.parseObject((String)jsonData);
        String sender = obj.getString("sender");
        JSONArray list = obj.getJSONArray("list");
        UserBean userBean = userService.findUser(sender);
        boolean bSuccess = false;
        if (userBean != null) {
            SendMailUtil.setSenderParam(userBean,"text/html");
            bSuccess =  SendMailUtil.sendMail("zhangjunjie@hylink.net.cn","595436259@qq.com","idea","abc");
        }
        System.out.print(sender);
        return bSuccess;

    }

/*    @ResponseBody
    @PostMapping("/uploadFile")
    Object uploadFile(
            @RequestParam(name = "jsonData", required = false)
                    String jsonData
    ){
        List<FileItem> *//* FileItem *//*items = upload.parseRequest(request);
        DataWraped dataResult = new DataWraped();
        return dataResult;
    }*/

    //JSON形式返回给结果
    @ResponseBody
    //文件只能用POST方式进行传递
    //@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @PostMapping("/uploadFile")
    public Object filesUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("file") MultipartFile file/* @RequestParam("user_name") String user_name,*/
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
                String FilePath =  "E:"+ File.separator + filename;
                File dir = new File(FilePath);
              /*  if (!dir.exists()) {
                    dir.mkdirs();
                }
*/
                // 转存文件，否则所创建的是个文件夹
                file.transferTo(new File(FilePath));
                // 获取需要处理的文件
                resultJson.put("data", "成功");
                dataResult.setResultCode(ExceptionCode.ResultCode.NO_ERROR);

            } catch (Exception e) {
                System.out.println("文件转存失败");
                dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
            }
        } else {
            dataResult.setResultCode(ExceptionCode.ResultCode.OP_ERROR);
        }
        return dataResult;
    }
}
