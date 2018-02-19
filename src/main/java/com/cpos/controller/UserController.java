package com.cpos.controller;

import com.cpos.classes.EmpInfo;
import com.cpos.classes.ResultInfo;
import com.cpos.dao.UserDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

}
