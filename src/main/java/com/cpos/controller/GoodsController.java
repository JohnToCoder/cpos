package com.cpos.controller;

import com.cpos.classes.EmpInfo;
import com.cpos.classes.ResultInfo;
import com.cpos.dao.GoodsDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GoodsController {
    @Autowired
    private GoodsDao goodsDao;
    //页面加载
    //获取发货单
    @RequestMapping(value = "/goods/getwareinvoicetab",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getWareInvoiceTab(HttpServletRequest request){
        return goodsDao.getWareInvoiceTab();
    }
    //获取门店列表
    @RequestMapping(value = "/goods/getstorelist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getGoodsStoreList(HttpServletRequest request){
        String strstype = request.getParameter("storetype");
        return goodsDao.getGoodsStoreList(strstype);
    }
    //获取运货方式表
    @RequestMapping(value = "/goods/getwimethodlist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getWiMethodList(HttpServletRequest request){
        return goodsDao.getGoodsWiMethodList();
    }
    //获取调货方式
    @RequestMapping(value ="/goods/getdelivermethod",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getDeliverMethod(HttpServletRequest request){return goodsDao.getDeliverMethod();}
    //业务逻辑
    //获取仓库发货单
    @RequestMapping(value = "/goods/getwareinvoice")
    @ResponseBody
    public ResultInfo<String> getWareInvoice(HttpServletRequest request){
        return ResultInfo.creatResult(0,0,"OK");
    }
    //上传仓库库存信息
    @RequestMapping(value = "/goods/upwaregoods")
    @ResponseBody
    public ResultInfo<String> upWareGoods(HttpServletRequest request){
        String strginfo = request.getParameter("ginfo");
        return goodsDao.upWareGoods(strginfo);
    }
    //获取库存信息
    @RequestMapping(value = "/goods/getstoregoods",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getStoreGoods(HttpServletRequest request){
        String strstore = request.getParameter("shopCode");
        String strlastdate = request.getParameter("lastUpTime");
        return goodsDao.getStoreGoods(strstore,strlastdate);
    }
    //上传仓库发货单信息
    @RequestMapping(value = "/goods/doupwibilldtl",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doUpwiBillDtl(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        String strwibill = jsonObject.getString("wibill");
        String strwidtl = jsonObject.getString("widtl");
        return goodsDao.doUpwiBillDtl(empInfo.getEmpCode(),empInfo.getStoreCode(),strwibill,strwidtl);
    }
    //审核发货单
    @RequestMapping(value = "/goods/doverifybill",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doVerifyBill(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        String strverifylist = jsonObject.getString("wicodelist");
        return goodsDao.doVerifyBill(strverifylist);
    }
    //门店收货待校验单
    @RequestMapping(value = "/goods/getstorerecipelist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getStoreRecioeList(HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        return goodsDao.getStoreRecipeList(empInfo.getStoreCode());
    }
    //门店校验单发出
    @RequestMapping(value = "/goods/doverifyrecipebill",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doVerifyRecipeBill(@RequestBody JSONObject jsonObject,HttpServletRequest request){
        String strreb = jsonObject.getString("recipelist");
        return goodsDao.doVerifyRecipeBill(strreb);
    }
    //门店在校验单
    @RequestMapping(value = "/goods/getstoreverifylist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getStoreVerifyList(HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        return goodsDao.getStoreVerifyList(empInfo.getStoreCode());
    }
    //PDA获取收货单
    @RequestMapping(value = "/goods/pda/getstoreverifylist",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getPDAsvList(HttpServletRequest request){
        String strstorecode = request.getParameter("storeCode");
        String strbilltyoe = request.getParameter("billType");
        return goodsDao.getPDAStoreVerifyList(strstorecode,strbilltyoe);
    }
    //PDA获取收货单清单
    @RequestMapping(value = "/goods/pda/getstoreverifydtl",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getPDAscDtl(HttpServletRequest request){
        String strwicode = request.getParameter("wicode");
        return goodsDao.getPDAStoreVerifyDtl(strwicode);
    }
    //PDA确认收货信息
    @RequestMapping(value = "/goods/pda/dorecipeconfirm",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doPDARecipeConfirm(HttpServletRequest request){
        String strwicode = request.getParameter("wicode");
        String strrecipecount = request.getParameter("recipecount");
        String strrecipedesc = request.getParameter("recipedesc");
        return goodsDao.doPADRecipeConfirm(strwicode,strrecipecount,strrecipedesc);
    }
    //PDA批量确认收货信息
    @RequestMapping(value = "/goods/pda/dorecipeconfirmlist")
    @ResponseBody
    public String doPDARecipeConfirmList(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strrclist = jsonObject.getString("data");
        return goodsDao.doPDARecipeConfirmList(strrclist);
    }
    //PC确认收货
    @RequestMapping(value = "/goods/dorecipegoods",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doRecipeGoods(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strwicode = jsonObject.getString("wicodelist");
        return goodsDao.doRecipeBillGoods(strwicode);
    }
    //门店查询库存信息
    @RequestMapping(value = "/goods/doquertsw",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doQueryStoreWare(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        String strsku = jsonObject.getString("sku");
        String strstyle = jsonObject.getString("style");
        String strsize = jsonObject.getString("size");
        return goodsDao.doQueryStoreWare(empInfo.getStoreCode(),strsku,strstyle,strsize);
    }
    //所有门店库存信息联查
    @RequestMapping(value = "/goods/doqueralltsw",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doQueryAllStoreWare(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strscode = "";
        String strsku = jsonObject.getString("sku");
        String strstyle = jsonObject.getString("style");
        String strsize = jsonObject.getString("size");
        return goodsDao.doQueryStoreWare(strscode,strsku,strstyle,strsize);
    }
    //上传调货通知单
    @RequestMapping(value = "/goods/doupdeliverbilldtl",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String deoUpDeliverBillDtl(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strdebill = jsonObject.getString("deliverbill");
        String strdedtl = jsonObject.getString("deliverdtl");
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        return goodsDao.doUpDeliverBill(empInfo.getStoreCode(),empInfo.getEmpCode(),strdebill,strdedtl);
    }
    //加载调货单确认表
    @RequestMapping(value = "/goods/gettabdeliverbill",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getTabDelivertab(HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        return goodsDao.getDeliverBill(empInfo.getStoreCode());
    }
    //加载调货单确认表子表
    @RequestMapping(value = "/goods/gettabdeliverdtl",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getTabDeliverdtl(HttpServletRequest request){
        String strgdbcode = request.getParameter("gdbcode");
        return goodsDao.getDeliverDtl(strgdbcode);
    }
    //审核调货通知单
    @RequestMapping(value = "/goods/doverifydeliverbill",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doVerifyDeliverBill (HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strgdbcode = jsonObject.getString("gdblist");
        return goodsDao.doVerifyDeliverBill(strgdbcode);
    }
    //加载调货确认单 getnotesdeliverbill
    @RequestMapping(value = "/goods/getnotesdeliverbill",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getNotesDelivertab(HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        return goodsDao.getNotesDeliverBill(empInfo.getStoreCode());
    }
    //创建发货确认单
    @RequestMapping(value = "/goods/docreatdeliver",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doCreatDeliver(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        String strgdbcode = jsonObject.getString("gdbcode");
        String strwlmethod = jsonObject.getString("wlmethod");
        String strwlcode = jsonObject.getString("wlcode");
        return goodsDao.doCreatDeliver(strgdbcode,strwlmethod,strwlcode,empInfo.getEmpCode());
    }
    //PDA获取发货单及发货信息
    @RequestMapping(value = "/goods/getpdareliverbill",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getPDAReliverBill(HttpServletRequest request){
        String strstorecode = request.getParameter("storeCode");
        String strbilltyoe = request.getParameter("billType");
        return goodsDao.getPDADeliverInfo(strstorecode,strbilltyoe);
    }
    //PDA上传发货信息表
    @RequestMapping(value = "/goods/dopdaupdeliverinfo")
    @ResponseBody
    public String doPDAUpDeliverInfo(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strdata = jsonObject.getString("data");
        int strcount = jsonObject.getInt("count");
        return goodsDao.doPDAUpDeliverInfo(strcount,strdata);
    }
    //PDA上传盘点单
    @RequestMapping(value = "/goods/pda/upcheckbill")
    @ResponseBody
    public String doUpCheckBill(HttpServletRequest request ){
        String strstorecode = request.getParameter("shopCode");
        String strtype = request.getParameter("type");
        return goodsDao.doUpPDACheckBill(strstorecode,strtype);
    }
    //PDA上传盘点单详情
    @RequestMapping(value = "/goods/pda/upcheckdata")
    @ResponseBody
    public String doUpPDACheckData(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strcheckbill = jsonObject.getString("checkbillNo");
        String strcheckdata = jsonObject.getString("data");
        return goodsDao.doUpPDACheckData(strcheckbill,strcheckdata);
    }
}
