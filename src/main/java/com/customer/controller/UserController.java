package com.customer.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.customer.model.UserBean;
import com.customer.service.UserService;
import com.customer.util.DataWraped;
import com.customer.util.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
            String mailHost = "smtp.exmail.qq.com";
            if(email.contains("163")) {
                mailHost="smtp.163.com";
            } else if(email.contains("qq")) {
                mailHost="smtp.qq.com";
            }
            else if(email.contains("126")) {
                mailHost="smtp.126.com";
            }
            userBean.setMailHost(mailHost);
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
                dataResult.setData(userBean);
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
    @ResponseBody
    @PostMapping("/getInfo")
    public Object getUserInfo(@RequestParam(name = "jsonData", required = false)
                                Object jsonData) {
        JSONObject obj = JSON.parseObject((String) jsonData);

        String user = obj.getString("user");
        //String psw = obj.getString("password");
        DataWraped dataResult = new DataWraped();
        try {
            UserBean userBean = userService.findUser(user);
            if (userBean == null )  {
                dataResult.setResultCode(ExceptionCode.ResultCode.INNER_ERROR);
                dataResult.setResultMsg("账户名不存在");
            }else {
                dataResult.setResultMsg("获取成功");
                dataResult.setData(userBean);
                dataResult.setResultCode(ExceptionCode.ResultCode.NO_ERROR);
            }
        } catch (Exception ex) {
            dataResult.setResultMsg(ex.getMessage().toString());
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
                int xx = userService.updateUser(userBean);
                if(filePath != null) {
                    //filePath = filePath.substring( userBean.getContentPath().lastIndexOf("\\")+1);
                    userBean.setContentPath(filename);
                    dataResult.setData(userBean);
                }
                // 转存文件，否则所创建的是个文件夹
                file.transferTo(new File(filePath));
                // 获取需要处理的文件
               // resultJson.put("data", "成功");
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

    @ResponseBody
    @RequestMapping("/downloadFile")
    public ResponseEntity<byte[]> filesDownload(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(name = "jsonData", required = false)
                                                         String jsonData                    ) {
        //设置返回信息的编码格式及类型
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        JSONObject obj = JSON.parseObject((String) jsonData);
        String user = obj.getString("userName");
        UserBean userBean = userService.findUser(user);

        //要下载文件的路径
        String route = userBean.getContentPath();
        //根据路径创建file
        File file=new File(route);  //存放太那些好的excel的绝对路径
        byte[] bytes = {};
        try {
            FileInputStream fis    =  new FileInputStream(file);

            BufferedInputStream bis = new BufferedInputStream(fis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int date = -1;
            while ((date = bis.read()) != -1) {
                bos.write(date);
            }
            bytes = bos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            String fileName=new String(route.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}