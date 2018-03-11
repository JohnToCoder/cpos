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

    @RequestMapping(value = "/analysis/douptrydata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doUpTryData(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        String strdata = jsonObject.getString("data");
        return analysisDao.doUpTryData(strdata);
    }

    @RequestMapping(value = "/analysis/doanalysisdata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doAnalysisData(HttpServletRequest request){
        return analysisDao.doAnalysisData();
    }

    @RequestMapping(value = "/analysis/getanalysisdata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getTryData(HttpServletRequest request){
        return analysisDao.getAnalysisData();
    }
}
