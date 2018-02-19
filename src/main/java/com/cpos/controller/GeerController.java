package com.cpos.controller;

import com.cpos.dao.GeerDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class GeerController {
    @Autowired
    private GeerDao geerDao;
    //加载设置人员信息
    @RequestMapping(value = "/geer/getemplist",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGeerEmpList(HttpServletRequest request){
        return geerDao.getGeerEmpList();
    }
    //加载单位选项
    @RequestMapping(value = "/geer/getstorelist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGeerStoreList(HttpServletRequest request){
        return geerDao.getGeerStoreList();
    }
    //加载员工职能
    @RequestMapping(value = "/geer/getdutylist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGeerDutyList(){
        return geerDao.getGeetDutyList();
    }
    //修改用户信息
    @RequestMapping(value = "/geer/doupdateemp",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doGeerUpdateEmp(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        String stremp = jsonObject.getString("empinfo");
        return geerDao.doGeerUpdateEmp(stremp);
    }
    //上传用户信息
    @RequestMapping(value = "/geer/doupempinfo",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doGeerUpEmpinfo(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        HttpSession strsession = request.getSession();
        String stremp = jsonObject.getString("empinfo");
        return geerDao.doGeerUpEmpinfo(stremp);
    }
    //审核员工信息
    @RequestMapping(value = "/geer/doqueryemps", produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doGeerQueryEmps(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        String stremps = jsonObject.getString("emplist");
        return geerDao.doGeerQueryEmps(stremps);
    }
    //删除员工信息
    @RequestMapping(value = "/geer/delempinfo",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String delGeerEmpInfo(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        String stremps = jsonObject.getString("emplist");
        return geerDao.delGeerEmpInfo(stremps);
    }
    //加载单位信息
    @RequestMapping(value = "/geer/getstoretab",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGeerStoretab(){
        return geerDao.getGeerStoretab();
    }
    //加载上级单位信息
    @RequestMapping(value = "/geer/getupstorelist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGeerUpStoreList(){
        return geerDao.getGeerUpStoreList();
    }
    //加载单位类型
    @RequestMapping(value = "/geer/getstypelist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGeerStypeList(){
        return geerDao.getGeetStypeList();
    }
    //修改单位信息
    @RequestMapping(value = "/geer/doupdatestore",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doGeerUpdateStore(@RequestBody JSONObject jsonObject){
        String strmgsinfo = jsonObject.getString("mgsinfo");
        return geerDao.doGeerUpdateStore(strmgsinfo);
    }
    //上传单位信息
    @RequestMapping(value = "/geer/doupstoretab",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doGeerUpStoreTab(@RequestBody JSONObject jsonObject){
        String strmgsinfo = jsonObject.getString("mgsinfo");
        return geerDao.doGeerUpStoreTab(strmgsinfo);
    }
}
