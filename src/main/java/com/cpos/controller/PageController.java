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
    @RequestMapping(value = "/geers/pgstoremag")
    public String geersStoreMag(){return "/geers/storemag";}
    @RequestMapping(value = "/geers/pgempmag")
    public String geersEmpMag(){return "/geers/empmag";}
    @RequestMapping(value = "/geers/pggeersmag")
    public String geersGeerMag(){return "/geers/geersmag";}
}
