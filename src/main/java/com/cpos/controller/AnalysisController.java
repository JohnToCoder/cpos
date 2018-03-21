package com.cpos.controller;

import com.cpos.dao.AnalysisDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AnalysisController {
    @Autowired
    private AnalysisDao analysisDao;

    //上传试穿数据
    @RequestMapping(value = "/analysis/douptrydata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doUpTryData(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        String strdata = jsonObject.getString("data");
        return analysisDao.doUpTryData(strdata);
    }
    //分析试穿数据
    @RequestMapping(value = "/analysis/doanalysisdata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doAnalysisData(HttpServletRequest request){
        return analysisDao.doAnalysisData();
    }
    //获取分析试穿数据
    @RequestMapping(value = "/analysis/getanalysisdata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getTryData(HttpServletRequest request){
        return analysisDao.getAnalysisData();
    }

    //加载门店销售对比
    @RequestMapping(value = "/analysis/getassalesstore",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAssalesStore(HttpServletRequest request){return analysisDao.getAssalesStore();}
    //加载试穿分析图表
    @RequestMapping(value = "/analysis/getastry",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAstry(HttpServletRequest request){return analysisDao.getAstry();}
    //加载试穿销售比
    @RequestMapping(value = "/analysis/getastrysales",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAsStrySales(HttpServletRequest request){return analysisDao.getAstrySales();}
    //销售分析返回统计总数
    @RequestMapping(value = "/analysis/getassalestotal",produces={"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAssalesTotal(HttpServletRequest request){
        String strstype = request.getParameter("asque");
        return analysisDao.getAssalesTotal(strstype);
    }
    //加载销售分析数据
    @RequestMapping(value = "/analysis/getassales",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAssales(HttpServletRequest request){

        return analysisDao.getAssales();
    }
    //加载门店销售统计
    @RequestMapping(value = "/analysis/getasstoredtl",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAsstoreDtl(HttpServletRequest request){
        return analysisDao.getAsstoreDtl();
    }

    //加载门店销售款式TOP10
    @RequestMapping(value = "/analysis/getasstylenum",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAsstyleTop(HttpServletRequest request){
        String listtype = request.getParameter("type");
        return analysisDao.getAsstyleTop(listtype);
    }

    //加载款式销量统计
    @RequestMapping(value = "/analysis/getassalesstyle",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAssalesStyle(HttpServletRequest request){
        String strssdays = request.getParameter("sdays");
        String strstore = request.getParameter("store");
        return analysisDao.getAssalesStyle(strssdays,strstore);
    }

    //加载时间段内试穿流量
    @RequestMapping(value = "/analysis/getattimes",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAttimes(HttpServletRequest request){
        return analysisDao.getAttimes();
    }

    //加载款式门店对比TOP
    @RequestMapping(value = "/analysis/getasstylestoretop",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAsstoreTop(HttpServletRequest request){
        String strsdays = request.getParameter("sdays");
        return analysisDao.getAsstoreTop(strsdays);
    }

    //加载款式门店对比last
    @RequestMapping(value = "/analysis/getasstylestorelast",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getAsstoreLast(HttpServletRequest request){
        String strsdays = request.getParameter("sdays");
        return analysisDao.getAsstoreLast(strsdays);
    }

}
