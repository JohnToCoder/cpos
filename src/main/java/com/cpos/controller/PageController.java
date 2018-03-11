package com.cpos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping(value = "/goods/gdwareinvoice")
    public String gdWareInvoice(){ return "/goods/gdwareinvoice"; }
    @RequestMapping(value = "/goods/gdstorerecipe")
    public String gdStoreRecipe(){ return "/goods/gdstorerecipe";}
    @RequestMapping(value = "/goods/gdstoreware")
    public String gdStoreWare(){return "/goods/gdstoreware";}
    @RequestMapping(value = "/goods/gdstoredeliver")
    public String gdStoreInvoice(){return "/goods/gdstoredeliver";}
    @RequestMapping(value = "/goods/gdstorecheck")
    public String gdStoreCheck(){return "/goods/gdstorecheck";}
    @RequestMapping(value = "/geers/pgstoremag")
    public String geersStoreMag(){return "/geers/storemag";}
    @RequestMapping(value = "/geers/pgempmag")
    public String geersEmpMag(){return "/geers/empmag";}
    @RequestMapping(value = "/geers/pggeersmag")
    public String geersGeerMag(){return "/geers/geersmag";}
    @RequestMapping(value = "/firstpages")
    public String firstPages(){return "firstpages";}
    @RequestMapping(value = "/sales/pgsalesbill")
    public String salesBill(){return "/sales/salesbill";}
    @RequestMapping(value = "/sales/pgsalescount")
    public String salesCount(){return "/sales/salescount";}
    @RequestMapping(value = "/sales/pgsalesgeer")
    public String salesGeer(){return "/sales/salesgeer";}
    @RequestMapping(value = "/analysis/pagebasedata")
    public String tryBaseData(){return "/analysis/analysisdata";}
    @RequestMapping(value = "/analysis/pagesalesdata")
    public String analysisSales(){return "/analysis/analysissales";}
    @RequestMapping(value = "/analysis/pagetryanalysis")
    public String analysisTry(){ return "/analysis/analysistry";}
    @RequestMapping(value = "/analysis/pagesalestry")
    public String analysisTrySales(){return "/analysis/analysistrysales";}

}
