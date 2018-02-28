package com.cpos.dao;

import com.cpos.classes.ResultInfo;
import com.cpos.classes.sales.SalesBill;
import com.cpos.classes.sales.SalesDtl;
import com.cpos.classes.sales.SalesPrice;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getSalesPrice() {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<SalesPrice> listsp = new ArrayList<SalesPrice>();
        String strsql = "SELECT a.g_sku,a.g_name,a.g_color,a.g_size,a.g_style,ifnull(b.s_listprice,0) as s_listprice,ifnull(b.s_curprice,0) as s_curprice,\n" +
                "ifnull(b.s_discount,1) as s_discount,b.gmt_creat \n" +
                "FROM pos_cloud.store_goods a\n" +
                "left join pos_cloud.store_salesprice b on a.g_sku = b.g_sku\n" +
                "group by a.g_sku ;";
        try{
            listsp = jdbcTemplate.query(strsql, new ParameterizedRowMapper<SalesPrice>() {
                @Override
                public SalesPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SalesPrice sp = new SalesPrice();
                    sp.setSku(rs.getString("g_sku"));
                    sp.setSize(rs.getString("g_size"));
                    sp.setProName(rs.getString("g_name"));
                    sp.setColor(rs.getString("g_color"));
                    sp.setStyle(rs.getString("g_style"));
                    sp.setListPrice(rs.getString("s_listprice"));
                    sp.setCurPrice(rs.getString("s_curprice"));
                    sp.setDiscount(rs.getString("s_discount"));
                    return sp;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData(JSONArray.fromObject(listsp).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doUpSalesPrice(String strsku, String strtype, String strlistprice,String strdiscount, String strcurprice) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{
            Date insdates = new Date();
            SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String strdate = fins.format(insdates);
            String sqlc = "SELECT count(*) FROM pos_cloud.store_salesprice where g_sku='"+strsku+"';";
            String sqlins = "";
            int sqlcount = (Integer) jdbcTemplate.queryForObject(sqlc,Integer.class);
            if(sqlcount == 0){
                sqlins="insert into pos_cloud.store_salesprice set s_listprice = '"+strlistprice+"',s_discount = '"+strdiscount+"',s_curprice = '"+strcurprice+"',g_sku='"+strsku+"',gmt_creat='"+strdate+"'";
            }else{
                switch (strtype){
                    case "1":sqlins="update pos_cloud.store_salesprice set s_listprice = '"+strlistprice+"',gmt_creat='"+strdate+"' where g_sku='"+strsku+"'";break;
                    case "2":sqlins="update pos_cloud.store_salesprice set s_discount = '"+strdiscount+"',s_curprice = '"+strcurprice+"',gmt_creat='"+strdate+"' where g_sku='"+strsku+"'";break;
                }
            }
            jdbcTemplate.update(sqlins);
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("OK");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getPDAgoodsInfo(String strepc) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<SalesPrice> salesPrice = new ArrayList<SalesPrice>();
        try{
            String strsql = "SELECT a.g_sku,a.g_name,a.g_style,a.g_color,a.g_size,ifnull(b.s_listprice,0) as s_listprice,ifnull(b.s_curprice,0) as s_curprice,\n" +
                    "ifnull(b.s_discount,0) as s_discount,a.store_code,c.store_name\n" +
                    " FROM pos_cloud.store_goods a \n" +
                    "left join pos_cloud.store_salesprice b on a.g_sku = b.g_sku\n" +
                    "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                    "where a.g_unique = '"+strepc+"';";
            salesPrice = jdbcTemplate.query(strsql, new ParameterizedRowMapper<SalesPrice>() {
                @Override
                public SalesPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SalesPrice sp = new SalesPrice();
                    sp.setSku(rs.getString("g_sku"));
                    sp.setSize(rs.getString("g_size"));
                    sp.setProName(rs.getString("g_name"));
                    sp.setColor(rs.getString("g_color"));
                    sp.setStyle(rs.getString("g_style"));
                    sp.setListPrice(rs.getString("s_listprice"));
                    sp.setCurPrice(rs.getString("s_curprice"));
                    sp.setDiscount(rs.getString("s_discount"));
                    sp.setStoreCode(rs.getString("store_code"));
                    sp.setStoreName(rs.getString("store_name"));
                    return sp;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(salesPrice.size());
            resultInfo.setData(JSONArray.fromObject(salesPrice).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doUpSalesData(String strsalesdata) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        SalesBill salesBill = new SalesBill();
        List<SalesDtl> listsdtl = new ArrayList<SalesDtl>();

        String strlastsb = "";
        String strsbcode = "";
        try{
            String strbill = JSONObject.fromObject(strsalesdata).getString("salesbill");
            String strdtl = JSONObject.fromObject(strsalesdata).getString("salesdtl");
            salesBill= (SalesBill) JSONObject.toBean(JSONObject.fromObject(strbill),SalesBill.class);
            listsdtl = JSONArray.toList(JSONArray.fromObject(strdtl),SalesDtl.class);

            Date insdate = new Date();
            final SimpleDateFormat fin = new SimpleDateFormat("yyMMdd");
            String strl = fin.format(insdate);
            String strlastsql = "SELECT s_billno FROM pos_cloud.store_salesbill where s_storecode = '"+salesBill.getStoreCode()+"' order by s_billno desc limit 1;";
            try{
                strlastsb = jdbcTemplate.queryForObject(strlastsql,String.class);
            }catch (EmptyResultDataAccessException e){
                strlastsb = null;
            }
            if(strlastsb!=null&&strlastsb.contains(strl)){
                int intscb = Integer.parseInt(strlastsb.substring(strlastsb.length()-3,strlastsb.length()));
                intscb++;
                strsbcode = "S"+salesBill.getStoreCode()+strl + String.format("%03d",intscb);
            }else{
                strsbcode = "S"+salesBill.getStoreCode()+strl+String.format("%03d",1);
            }
            Date insdates = new Date();
            SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String strdate = fins.format(insdates);

            String strinsbill = "insert into pos_cloud.store_salesbill(s_billno,s_totalprice,s_totalnum,s_storecode,s_empcode,gmt_creat)values(?,?,?,?,?,?)";
            final String finalStrsbcode = strsbcode;
            final SalesBill finalSalesBill = salesBill;
            jdbcTemplate.update(strinsbill, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1, finalStrsbcode);
                    ps.setObject(2, finalSalesBill.getTotalPrice());
                    ps.setObject(3,finalSalesBill.getTotalNum());
                    ps.setObject(4,finalSalesBill.getStoreCode());
                    ps.setObject(5,finalSalesBill.getEmpCode());
                    ps.setObject(6,strdate);
                }
            });
            String strinsdtl = "insert into pos_cloud.store_salesdtl(s_billno,g_sku,g_unique,g_name,g_size,g_style,g_color,s_listprice,s_curprice,gmt_creat)\n" +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            final List<SalesDtl> finalListsdtl = listsdtl;
            int[] insss = jdbcTemplate.batchUpdate(strinsdtl, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, finalStrsbcode);
                    ps.setObject(2,finalListsdtl.get(i).getSku());
                    ps.setObject(3,finalListsdtl.get(i).getEpc());
                    ps.setObject(4,finalListsdtl.get(i).getProName());
                    ps.setObject(5,finalListsdtl.get(i).getSize());
                    ps.setObject(6,finalListsdtl.get(i).getStyle());
                    ps.setObject(7,finalListsdtl.get(i).getColor());
                    ps.setObject(8,finalListsdtl.get(i).getListPrice());
                    ps.setObject(9,finalListsdtl.get(i).getCurPrice());
                    ps.setObject(10,strdate);
                }

                @Override
                public int getBatchSize() {
                    return finalListsdtl.size();
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("OK");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getPDASalsInfo(String strstorecode, String strstartdt, String strfinishdt) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<SalesBill> listBill = new ArrayList<SalesBill>();
        try{
            String strsql = "SELECT a.*,b.emp_name FROM pos_cloud.store_salesbill a \n" +
                    "left join pos_cloud.sta_employee b on a.s_empcode = b.emp_code\n" +
                    "where a.s_storecode ='"+strstorecode+"' and a.gmt_creat between '"+strstartdt+"' and '"+strfinishdt+"';";
            listBill = jdbcTemplate.query(strsql, new ParameterizedRowMapper<SalesBill>() {
                @Override
                public SalesBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SalesBill sb = new SalesBill();
                    sb.setSalesBillNo(rs.getString("s_billno"));
                    sb.setTotalPrice(rs.getString("s_totalprice"));
                    sb.setTotalNum(rs.getString("s_totalnum"));
                    sb.setStoreCode(rs.getString("s_storecode"));
                    sb.setEmpCode(rs.getString("s_empcode"));
                    sb.setEmpName(rs.getString("emp_name"));
                    sb.setGmtCreat(rs.getString("gmt_creat"));
                    return sb;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listBill.size());
            resultInfo.setData(JSONArray.fromObject(listBill).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getSaleDetailInfo(String strbillno) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<SalesDtl> listBill = new ArrayList<SalesDtl>();
        try{
            String strsql = "SELECT * FROM pos_cloud.store_salesdtl where s_billno like '%"+strbillno+"%';";

            listBill = jdbcTemplate.query(strsql, new ParameterizedRowMapper<SalesDtl>() {
                @Override
                public SalesDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
                    SalesDtl sd = new SalesDtl();
                    sd.setSalesBillNo(rs.getString("s_billno"));
                    sd.setSku(rs.getString("g_sku"));
                    sd.setSize(rs.getString("g_size"));
                    sd.setProName(rs.getString("g_name"));
                    sd.setColor(rs.getString("g_color"));
                    sd.setStyle(rs.getString("g_style"));
                    sd.setListPrice(rs.getString("s_listprice"));
                    sd.setCurPrice(rs.getString("s_curprice"));
                    sd.setEpc(rs.getString("g_unique"));
                    sd.setGmtCreat(rs.getString("gmt_creat"));
                    return sd;
                }
            });

            resultInfo.setCode(0);
            resultInfo.setCount(listBill.size());
            resultInfo.setData(JSONArray.fromObject(listBill).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }
}
