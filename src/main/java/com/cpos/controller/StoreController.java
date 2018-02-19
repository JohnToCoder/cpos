package com.cpos.controller;

import com.cpos.classes.ResultInfo;
import com.cpos.dao.StoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StoreController {
    @Autowired
    private StoreDao storeDao;
    @RequestMapping("/store/upgoodsdtl")
    @ResponseBody
    public ResultInfo<String> upGoodsDtl(HttpServletRequest request){
        return ResultInfo.creatResult(1,1,"OK");
    }
}
