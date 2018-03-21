package com.cpos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    //地区配货
    @RequestMapping(value="/goods/gdareagoods")
    public String gdAreaGoods(){return "/goods/gdareagoods";}
    //仓库发货
    @RequestMapping(value = "/goods/gdwareinvoice")
    public String gdWareInvoice(){ return "/goods/gdwareinvoice"; }
    //门店收货
    @RequestMapping(value = "/goods/gdstorerecipe")
    public String gdStoreRecipe(){ return "/goods/gdstorerecipe";}
    //门店库存
    @RequestMapping(value = "/goods/gdstoreware")
    public String gdStoreWare(){return "/goods/gdstoreware";}
    //门店调货
    @RequestMapping(value = "/goods/gdstoredeliver")
    public String gdStoreInvoice(){return "/goods/gdstoredeliver";}
    //门店盘点
    @RequestMapping(value = "/goods/gdstorecheck")
    public String gdStoreCheck(){return "/goods/gdstorecheck";}
    //门店管理
    @RequestMapping(value = "/geers/pgstoremag")
    public String geersStoreMag(){return "/geers/storemag";}
    //人员管理
    @RequestMapping(value = "/geers/pgempmag")
    public String geersEmpMag(){return "/geers/empmag";}
    //系统配置
    @RequestMapping(value = "/geers/pggeersmag")
    public String geersGeerMag(){return "/geers/geersmag";}
    //首页
    @RequestMapping(value = "/firstpages")
    public String firstPages(){return "firstpages";}
    //销售开单
    @RequestMapping(value = "/sales/pgsalesbill")
    public String salesBill(){return "/sales/salesbill";}
    //门店销售统计
    @RequestMapping(value = "/sales/pgsalescount")
    public String salesCount(){return "/sales/salescount";}
    //销售配置
    @RequestMapping(value = "/sales/pgsalesgeer")
    public String salesGeer(){return "/sales/salesgeer";}
    //试穿数据采集
    @RequestMapping(value = "/analysis/pagebasedata")
    public String tryBaseData(){return "/analysis/analysisdata";}
    //销售分析---首页
    @RequestMapping(value = "/analysis/pagesalesdata")
    public String analysisSales(){return "/analysis/analysissales";}
    //门店销售额分析
    @RequestMapping(value = "/analysis/pageasstore")
    public String pageAsstore(){return "/analysis/asstore";}
    //款式销售情况统计
    @RequestMapping(value = "/analysis/pageasstyle")
    public String pageAsstyle(){return "/analysis/asstyle";}
    //畅销款统计
    @RequestMapping(value = "/analysis/pageasstyletop")
    public String pageAsstyleTop(){return "/analysis/asstyletop";}
    //滞销款统计
    @RequestMapping(value = "/analysis/pageasstylelast")
    public String pageAsstyleLast(){return "/analysis/asstylelast";}
    //款式销量门店分布对比
    @RequestMapping(value = "/analysis/pageasstylestore")
    public String pageAsstyleStore(){return "/analysis/asstylestore";}
    //试穿数据分析---首页
    @RequestMapping(value = "/analysis/pagetryanalysis")
    public String analysisTry(){ return "/analysis/analysistry";}
    //试穿销售比
    @RequestMapping(value = "/analysis/pagesalestry")
    public String analysisTrySales(){return "/analysis/analysistrysales";}
    //试穿流量
    @RequestMapping(value = "/analysis/pageattimes")
    public String analusisAttimes(){return "/analysis/attimes";}

}
