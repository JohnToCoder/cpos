package com.cpos.controller;

import com.cpos.classes.EmpInfo;
import com.cpos.classes.ResultInfo;
import com.cpos.dao.UserDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by john on 2018/1/1.
 */
@Controller
public class UserController {
    @Autowired
    public UserDao userDao;
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("emp")!=null){
            return "index";
        }
        return "login";
    }
    @RequestMapping(value = "/tologin",method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo<String> toLogin(HttpServletRequest request,
                                      @RequestParam(required = true,value = "empcode") String empCode,
                                      @RequestParam(required = true,value = "pwd") String pwd){
        String strip = request.getRemoteAddr();
        String strhost = request.getRemoteHost();
        int strport = request.getRemotePort();

        ResultInfo<String> bizResult = userDao.loginByEmpID(empCode,pwd);
        if(bizResult.getCode()==0){
            EmpInfo employee = (EmpInfo) JSONObject.toBean(JSONObject.fromObject(bizResult.getData()),EmpInfo.class);
            HttpSession session = request.getSession();
            session.setAttribute("emp",employee);
            session.setMaxInactiveInterval(3600);
            userDao.loginlog(strhost,strip,strport,empCode,"OK");
            return ResultInfo.creatResult(0,1,employee.getEmpCode());
        }
        userDao.loginlog(strhost,strip,strport,empCode,bizResult.getData());
        return ResultInfo.creatResult(1,1,bizResult.getData());
    }

    @RequestMapping(value = "index")
    public String index2(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("emp")!=null)
        {
            System.out.println(request.getSession().getAttribute("emp"));
            return "index";
        }
        return "login";
    }
    @RequestMapping(value = "loginout" )
    public String loginOut (HttpServletRequest request){
        if(request.getSession().getAttribute("emp")!=null)
        {
            request.getSession().removeAttribute("emp");
            request.getSession().invalidate();
        }
        return "login";
    }
    @RequestMapping(value = "/pda/tologin")
    @ResponseBody
    public ResultInfo<String> pdaToLogin(HttpServletRequest request ,
                                         @RequestParam(required = true,value = "empcode") String empCode,
                                         @RequestParam(required = true,value = "pwd") String pwd){
        String strip = request.getRemoteAddr();
        String strhost = request.getRemoteHost();
        int strport = request.getRemotePort();

        ResultInfo<String> bizResult = userDao.loginByEmpID(empCode,pwd);
        if(bizResult.getCode() ==0){
            userDao.loginlog(strhost,strip,strport,empCode,"OK");
        } else{
            userDao.loginlog(strhost,strip,strport,empCode,bizResult.getData());
        }
        return bizResult;
    }


    @RequestMapping(value = "/pages/updateapp")
    public String getUpdateApp(){return "updateapp";}
    //获取所有的APP版本信息
    @RequestMapping(value = "/pages/getappversion")
    @ResponseBody
    public String getAppVersion(){
        String strAppInfo = userDao.getAppVersion();
        return  strAppInfo;
    }
    //上传APP到服务器
    @RequestMapping(value = "/uploadapp",method = RequestMethod.POST,produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String UploadAppp(@RequestParam("file") MultipartFile file,
                             HttpServletRequest request){
        String strRe ="";
        String strAppType = request.getParameter("apptype");
        String strVersionCode = request.getParameter("versioncode");
        String strAppMessages = request.getParameter("appmessages");
        //String path ="/usr/tomcat/tomcat8/webapps/updateapp";
        String path = "/Users/john/Desktop/";
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path,fileName);
        String appurl = "http://www.echitec.com/updateapp/"+fileName;
        try{
            if(!targetFile.exists()){
                targetFile.mkdir();
                file.transferTo(targetFile);
                userDao.insertAppInfo(strAppType,strVersionCode,fileName,strAppMessages,appurl);
                strRe = fileName+"-上传成功!";
            }else{
                strRe = fileName+"-已存在!";
            }
        }catch (Exception ex){

            return "上传失败:"+ ex.getMessage().toString();
        }

        return strRe;
    }
    //删除APP
    @RequestMapping(value = "/dltapp" ,method =RequestMethod.POST)
    @ResponseBody
    public String dltApp(@RequestBody JSONObject jsonObject){
        String[] strids = jsonObject.getString("ids").split(",");
        return userDao.dltAPP(strids);
    }
    //更新手机端APP
    @RequestMapping(value="/updateapp")
    @ResponseBody
    public ResultInfo<String> updateApp(HttpServletRequest request){
        String strVersionCode = request.getParameter("versioncode");
        String strAppType = "app";
        return userDao.getAppUrl(strVersionCode,strAppType);
    }
}
