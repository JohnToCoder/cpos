package com.cpos.controller;

import com.cpos.classes.EmpInfo;
import com.cpos.dao.SalesDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class SalesController {
    @Autowired
    private SalesDao salesDao;
    //获取销售价格
    @RequestMapping(value = "/sales/getsalesprice",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getSalesPrice(HttpServletRequest request){
        return salesDao.getSalesPrice();
    }
    //上传销售价格，配置价格
    @RequestMapping(value = "/sales/doupsalesprice",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doUpSalesPrice(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        String strtype = jsonObject.getString("ptype");
        String strsku = jsonObject.getString("strsku");
        String strlistprice = jsonObject.getString("listprice");
        String strdiscount = jsonObject.getString("discount");
        String strcurprice = jsonObject.getString("curprice");

        return salesDao.doUpSalesPrice(strsku,strtype,strlistprice,strdiscount,strcurprice);
    }
    //页面获取门店销售订单列表
    @RequestMapping(value = "/sales/getsalesbills" ,produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getSalesBill(HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        String strbillno = request.getParameter("billno");
        String strstartdt = request.getParameter("startdt");
        String strfinishdt = request.getParameter("enddt");
        if(strstartdt == "" && strfinishdt == ""){
            //获取当前日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            strfinishdt = sdf.format(today);//当前日期
            // 获取三十天前日期
            Calendar theCa = Calendar.getInstance();
            theCa.setTime(today);
            theCa.add(theCa.DATE, -30);//最后一个数字30可改，30天的意思
            Date start = theCa.getTime();
            strstartdt = sdf.format(start);//三十天之前日期
        }

        return salesDao.getSalesBills(empInfo.getStoreCode(),strstartdt,strfinishdt,strbillno);
    }
    //页面获取门店销售订单详细
    @RequestMapping(value = "/sales/getsalesdtl",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getSalesDtl(HttpServletRequest request){
        String strbillno = request.getParameter("billno");
        return salesDao.getSaleDetailInfo(strbillno);
    }
    //PDA获取货品信息包括价格
    @RequestMapping(value = "/sales/pda/getgoodsinfo",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getPDAgoodsInfo(HttpServletRequest request){
        String strepc = request.getParameter("epcNo");
        return salesDao.getPDAgoodsInfo(strepc);
    }
    //PDA上传销售信息，销售单和销售数据
    @RequestMapping(value = "/sales/pda/doupsalesdata",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String doUpSalesData(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        String strsalesdata = jsonObject.getString("data");
        return salesDao.doUpSalesData(strsalesdata);
    }
    //PDA时间段查询销售订单
    @RequestMapping(value = "/sales/pda/getsalesinfo",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getPDASalesInfo(HttpServletRequest request){
        String strstorecode = request.getParameter("storeCode");
        String strstartdt = request.getParameter("startDate");
        String strfinishdt = request.getParameter("finishDate");
        return salesDao.getSalesInfo(strstorecode,strstartdt,strfinishdt);
    }
    //PDA按销售单号查询详细信息
    @RequestMapping(value = "/sales/pda/getsaledetailinfo",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getSaleDetailInfo(HttpServletRequest request){
        String strbillno = request.getParameter("saleBillNo");
        return salesDao.getSaleDetailInfo(strbillno);
    }
    //当月门店销售数据查询
    @RequestMapping(value = "/sales/getstoremothreport",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getStoreMothReport(HttpServletRequest request){
        EmpInfo empInfo = (EmpInfo) request.getSession().getAttribute("emp");
        return salesDao.getStoreMothReport(empInfo.getStoreCode());
    }
}
