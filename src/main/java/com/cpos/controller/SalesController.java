package com.cpos.controller;

import com.cpos.dao.SalesDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
        return salesDao.getPDASalsInfo(strstorecode,strstartdt,strfinishdt);
    }
    //按销售单号查询详细信息
    @RequestMapping(value = "/sales/pda/getsaledetailinfo",produces = {"text/html;charset=UTF-8;","application/json;"})
    @ResponseBody
    public String getSaleDetailInfo(HttpServletRequest request){
        String strbillno = request.getParameter("saleBillNo");
        return salesDao.getSaleDetailInfo(strbillno);
    }
}
