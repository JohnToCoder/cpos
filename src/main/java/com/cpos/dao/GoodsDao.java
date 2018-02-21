package com.cpos.dao;

import com.cpos.classes.*;
import com.cpos.classes.goods.*;
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
import java.util.*;

public class GoodsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String deliverMethod;

    public ResultInfo<String> upWareGoods(String strginfo) {
        List<StoreGoods> listg = new ArrayList<StoreGoods>();
        String strLastIns = null;
        String strl = "";
        int inscount = 0;
        int[] intins = null;
        try {
            listg = JSONArray.toList(JSONArray.fromObject(strginfo), StoreGoods.class);
            try {
                String strins = "select g_unique from pos_cloud.ware_goods order by g_unique desc limit 1";
                strLastIns = jdbcTemplate.queryForObject(strins, java.lang.String.class);
            } catch (EmptyResultDataAccessException e) {
                strLastIns = null;
            }
            if (strLastIns == null) {
                Date insdate = new Date();
                SimpleDateFormat fin = new SimpleDateFormat("yyyyMMdd");
                strl = fin.format(insdate);
                inscount = 1;
            } else {
                strl = strLastIns.substring(0, 8);
                inscount = Integer.parseInt(strLastIns.substring(8, 12));
                inscount++;
            }
            for (int i = 0; i < listg.size(); i++) {
                String instemp = strl + String.format("%04d", inscount) + String.format("%012d", 0);
                inscount++;
                listg.get(i).setgUnique(instemp);
            }
            String strinssql = "insert into pos_cloud.ware_goods (store_code,g_group,g_location,g_unique,g_sku,g_name,g_style,g_color,g_size,isstate,store_indt,store_outdt,gmt_creat)\n" +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            final List<StoreGoods> finalListg = listg;
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String strDate = f.format(date);
            intins = jdbcTemplate.batchUpdate(strinssql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, finalListg.get(i).getStoreCode());
                    ps.setObject(2, finalListg.get(i).getgGroup());
                    ps.setObject(3, finalListg.get(i).getgLocation());
                    ps.setObject(4, finalListg.get(i).getgUnique());
                    ps.setObject(5, finalListg.get(i).getgSku());
                    ps.setObject(6, finalListg.get(i).getgName());
                    ps.setObject(7, finalListg.get(i).getgStyle());
                    ps.setObject(8, finalListg.get(i).getgColor());
                    ps.setObject(9, finalListg.get(i).getgSize());
                    ps.setObject(10, finalListg.get(i).getIsstate());
                    ps.setObject(11, strDate);
                    ps.setObject(12, strDate);
                    ps.setObject(13, strDate);
                }

                @Override
                public int getBatchSize() {
                    return finalListg.size();
                }
            });
        } catch (Exception es) {
            return ResultInfo.creatError(1, 1, es.getMessage().toString());
        }
        return ResultInfo.creatResult(0, intins.length, "OK");
    }

    public ResultInfo<String> getStoreGoods() {
        List<StoreGoodsUp> listsg = new ArrayList<StoreGoodsUp>();
        String sstr = "SELECT * FROM pos_cloud.store_goods;";
        try {
            listsg = jdbcTemplate.query(sstr, new ParameterizedRowMapper<StoreGoodsUp>() {
                @Override
                public StoreGoodsUp mapRow(ResultSet rs, int rowNum) throws SQLException {
                    StoreGoodsUp sg = new StoreGoodsUp();

                    sg.setgGroup(rs.getString("g_group"));
                    sg.setgLocation(rs.getString("g_location"));
                    sg.setgUnique(rs.getString("g_unique"));
                    sg.setgSku(rs.getString("g_sku"));
                    sg.setgName(rs.getString("g_name"));
                    sg.setgStyle(rs.getString("g_style"));
                    sg.setgColor(rs.getString("g_color"));
                    sg.setgSize(rs.getString("g_size"));

                    return sg;
                }
            });
        } catch (Exception es) {
            return ResultInfo.creatError(1, 1, es.getMessage().toString());
        }
        JSONArray ja = JSONArray.fromObject(listsg);
        return ResultInfo.creatResult(0, listsg.size(), ja.toString());
    }

    public String getWareInvoiceTab() {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GWInvoiceBill> listwib = new ArrayList<GWInvoiceBill>();
        String strsql = "SELECT a.* ,b.store_name as ware_name,c.store_name,d.lg_method  FROM pos_cloud.ware_invoicebill a\n" +
                "left join sta_store b on a.ware_code = b.store_code\n" +
                "left join sta_store c on a.store_code = c.store_code \n" +
                "left join sta_logistics d on a.wi_method = d.lg_code;";
        try{
            listwib = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GWInvoiceBill>() {
                @Override
                public GWInvoiceBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GWInvoiceBill gb = new GWInvoiceBill();
                    gb.setWiCode(rs.getString("wi_code"));
                    gb.setWareCode(rs.getString("ware_code"));
                    gb.setWareName(rs.getString("ware_name"));
                    gb.setStoreCode(rs.getString("store_code"));
                    gb.setStoreName(rs.getString("store_name"));
                    gb.setWlCode(rs.getString("wl_code"));
                    gb.setWiMethod(rs.getString("wi_method"));
                    gb.setWilgm(rs.getString("lg_method"));
                    gb.setWiCount(rs.getString("wi_count"));
                    gb.setWiEmp(rs.getString("wi_emp"));
                    gb.setIsverify(rs.getString("isverify"));
                    gb.setIsstorage(rs.getString("isstorage"));
                    gb.setGmtCreat(rs.getString("gmt_creat"));
                    gb.setGmtModify(rs.getString("gmt_modify"));
                    return gb;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listwib.size());
            resultInfo.setData(JSONArray.fromObject(listwib).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getGoodsStoreList(String strstype) {
        List<StaStoreInfo> listwh = new ArrayList<StaStoreInfo>();
        String strsql = "SELECT store_code,store_name FROM pos_cloud.sta_store where store_type = '"+strstype+"';";
        try{
            listwh = jdbcTemplate.query(strsql, new ParameterizedRowMapper<StaStoreInfo>() {
                @Override
                public StaStoreInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    StaStoreInfo wh = new StaStoreInfo();
                    wh.setStoreCode(rs.getString("store_code"));
                    wh.setStoreName(rs.getString("store_name"));
                    return wh;
                }
            });

        }catch (Exception es){
           return "false";
        }
        return JSONArray.fromObject(listwh).toString();
    }

    public String getGoodsWiMethodList() {
        List<StaWLMethod> listwm = new ArrayList<StaWLMethod>();
        String strsql = "SELECT * FROM pos_cloud.sta_logistics;";
        listwm = jdbcTemplate.query(strsql, new ParameterizedRowMapper<StaWLMethod>() {
            @Override
            public StaWLMethod mapRow(ResultSet rs, int rowNum) throws SQLException {
                StaWLMethod swl = new StaWLMethod();
                swl.setWlCode(rs.getString("lg_code"));
                swl.setWlMethod(rs.getString("lg_method"));
                return swl;
            }
        });
        return JSONArray.fromObject(listwm).toString();
    }

    public String doUpwiBillDtl(String empCode, String storeCode, String strwibill, String strwidtl) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        GWInvoiceBill wibill = new GWInvoiceBill();
        List<GWInvoiceDtl> listwidtl = new ArrayList<GWInvoiceDtl>();
        final List<GWInvoiceDtl> listwidtlunique = new ArrayList<GWInvoiceDtl>();

        String strwibcode = "";
        String strlastwicode = "";
        try{
            wibill = (GWInvoiceBill) JSONObject.toBean(JSONObject.fromObject(strwibill),GWInvoiceBill.class);
            listwidtl = JSONArray.toList(JSONArray.fromObject(strwidtl.replace("\\","")),GWInvoiceDtl.class);
            String strl = "";
            Date insdate = new Date();
            SimpleDateFormat fin = new SimpleDateFormat("yyMMdd");
            strl = fin.format(insdate);
            strwibcode = storeCode + strl;
            try{
                String strlsql = "SELECT wi_code FROM pos_cloud.ware_invoicebill where wi_code like '%"+strwibcode+"%' order by wi_code DESC limit 1;";
                strlastwicode = jdbcTemplate.queryForObject(strlsql,String.class);
            }catch (EmptyResultDataAccessException e) {
                strlastwicode = null;
            }
            if(strlastwicode == null){
                strwibcode = strwibcode + String.format("%03d",1);
            }else{
                int intc = Integer.parseInt(strlastwicode.substring(strlastwicode.length()-3, strlastwicode.length()));
                intc++;
                strwibcode = strwibcode + String.format("%03d",intc);
            }
            wibill.setWiCode(strwibcode);
            wibill.setWiEmp(empCode);
            for(int i =0;i<listwidtl.size();i++){
                List<GWTempUnique> listsg = new ArrayList<GWTempUnique>();
                int siq = Integer.parseInt(listwidtl.get(i).getWiSizeQty());
                String strsqlwi = "SELECT g_name,g_style,g_color,g_size, g_unique FROM pos_cloud.ware_goods " +
                        "where g_sku = '"+listwidtl.get(i).getWiSku()+"' and isstate ='1' and store_code = '"+storeCode+"' " +
                        "order by g_sku limit "+listwidtl.get(i).getWiSizeQty()+";";
                listsg = jdbcTemplate.query(strsqlwi, new ParameterizedRowMapper<GWTempUnique>() {
                    @Override
                    public GWTempUnique mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GWTempUnique ss = new GWTempUnique();
                        ss.setgUnique(rs.getString("g_unique"));
                        ss.setWiName(rs.getString("g_name"));
                        ss.setWiStyle(rs.getString("g_style"));
                        ss.setWiColor(rs.getString("g_color"));
                        ss.setWiSize(rs.getString("g_size"));
                        return ss;
                    }
                });
                if(listsg.size()<siq){
                    resultInfo.setCode(2);
                    resultInfo.setCount(1);
                    resultInfo.setData("SKU:" + listwidtl.get(i).getWiSku()+"库存数只有 "+listsg.size()+"对");
                    return JSONObject.fromObject(resultInfo).toString();
                }else {
                    for (int j = 0; j < listsg.size(); j++) {
                        GWInvoiceDtl gtl = new GWInvoiceDtl();
                        gtl.setWiCode(strwibcode);
                        gtl.setWiSku(listwidtl.get(i).getWiSku());
                        gtl.setWiName(listsg.get(j).getWiName());
                        gtl.setWiStyle(listsg.get(j).getWiStyle());
                        gtl.setWiColor(listsg.get(j).getWiColor());
                        gtl.setWiSize(listsg.get(j).getWiSize());
                        gtl.setWiSizeQty(listwidtl.get(i).getWiSizeQty());
                        gtl.setgUnique(listsg.get(j).getgUnique());
                        listwidtlunique.add(gtl);
                    }
                }
            }
            String strinswib = "insert into pos_cloud.ware_invoicebill (wi_code,ware_code,store_code,wl_code,wi_method,wi_count,wi_emp,isverify,isstorage,intype,cargotype,gmt_creat,gmt_modify)\n" +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
            final GWInvoiceBill finalWibill = wibill;
            Date insdates = new Date();
            SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String strdate = fins.format(insdates);
            jdbcTemplate.update(strinswib, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1, finalWibill.getWiCode());
                    ps.setObject(2,finalWibill.getWareCode());
                    ps.setObject(3,finalWibill.getStoreCode());
                    ps.setObject(4,finalWibill.getWlCode());
                    ps.setObject(5,finalWibill.getWiMethod());
                    ps.setObject(6,finalWibill.getWiCount());
                    ps.setObject(7,finalWibill.getWiEmp());
                    ps.setObject(8,finalWibill.getIsverify());
                    ps.setObject(9,finalWibill.getIsstorage());
                    ps.setObject(10,finalWibill.getIntype());
                    ps.setObject(11,finalWibill.getCargotype());
                    ps.setObject(12,strdate);
                    ps.setObject(13,strdate);
                }
            });
            String strinswid = "insert into pos_cloud.ware_invoicedtl(wi_code,wi_sku,wi_name,wi_style,wi_color,wi_size,wi_sizeqty,g_unique,gmt_creat)\n" +
                    "values(?,?,?,?,?,?,?,?,?)";

            int[] ints = jdbcTemplate.batchUpdate(strinswid, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,listwidtlunique.get(i).getWiCode());
                    ps.setObject(2,listwidtlunique.get(i).getWiSku());
                    ps.setObject(3,listwidtlunique.get(i).getWiName());
                    ps.setObject(4,listwidtlunique.get(i).getWiStyle());
                    ps.setObject(5,listwidtlunique.get(i).getWiColor());
                    ps.setObject(6,listwidtlunique.get(i).getWiSize());
                    ps.setObject(7,listwidtlunique.get(i).getWiSizeQty());
                    ps.setObject(8,listwidtlunique.get(i).getgUnique());
                    ps.setObject(9,strdate);
                }

                @Override
                public int getBatchSize() {
                    return listwidtlunique.size();
                }
            });
            for(int m = 0;m<listwidtlunique.size();m++){
                String strupd = "update pos_cloud.ware_goods set isstate = '2' where g_unique = '"+listwidtlunique.get(m).getgUnique()+"';";
                jdbcTemplate.update(strupd);
            }
            resultInfo.setCode(0);
            resultInfo.setCount(listwidtlunique.size());
            resultInfo.setData("成功新建发货单："+strwibcode+"\n 发出库存数："+ints.length);
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doVerifyBill(String strverifylist) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        final String[] strvlist = strverifylist.substring(0,strverifylist.length()-1).split(",");
        String strsql = "update  pos_cloud.ware_invoicebill set isverify = '1' where wi_code =?";
        try{
           int[] ins = jdbcTemplate.batchUpdate(strsql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,strvlist[i]);
                }

                @Override
                public int getBatchSize() {
                    return strvlist.length;
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

    public String getStoreRecipeList(String storeCode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GWInvoiceBill> listwib = new ArrayList<GWInvoiceBill>();
        String strsql = "SELECT a.* ,b.store_name as ware_name,c.store_name,d.lg_method ,e.car_desc FROM pos_cloud.ware_invoicebill a\n" +
                "left join pos_cloud.sta_store b on a.ware_code = b.store_code\n" +
                "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                "left join pos_cloud.sta_logistics d on a.wi_method = d.lg_code\n" +
                "left join pos_cloud.sta_cargotype e on a.cargotype = e.car_code\n" +
                "where a.isverify ='1' and a.store_code = '"+storeCode+"';";
        try{
            listwib = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GWInvoiceBill>() {
                @Override
                public GWInvoiceBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GWInvoiceBill gb = new GWInvoiceBill();
                    gb.setWiCode(rs.getString("wi_code"));
                    gb.setWareCode(rs.getString("ware_code"));
                    gb.setWareName(rs.getString("ware_name"));
                    gb.setStoreCode(rs.getString("store_code"));
                    gb.setStoreName(rs.getString("store_name"));
                    gb.setWlCode(rs.getString("wl_code"));
                    gb.setWiMethod(rs.getString("wi_method"));
                    gb.setWilgm(rs.getString("lg_method"));
                    gb.setWiCount(rs.getString("wi_count"));
                    gb.setWiEmp(rs.getString("wi_emp"));
                    gb.setIsverify(rs.getString("isverify"));
                    gb.setIsstorage(rs.getString("isstorage"));
                    gb.setCargotype(rs.getString("cargotype"));
                    gb.setCargodesc(rs.getString("car_desc"));
                    gb.setIntype(rs.getString("intype"));
                    gb.setGmtCreat(rs.getString("gmt_creat"));
                    gb.setGmtModify(rs.getString("gmt_modify"));
                    return gb;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listwib.size());
            resultInfo.setData(JSONArray.fromObject(listwib).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doVerifyRecipeBill(String strreb) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GoodsRecipeBill> listgrb = new ArrayList<GoodsRecipeBill>();
        try{

            Date insdates = new Date();
            SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String strdate = fins.format(insdates);
            listgrb = JSONArray.toList(JSONArray.fromObject(strreb),GoodsRecipeBill.class);

            String strsqlupdate = "update  pos_cloud.ware_invoicebill set isstorage = '1' where wi_code =?";
            final List<GoodsRecipeBill> finalListgrb = listgrb;
            int[] intupwb = jdbcTemplate.batchUpdate(strsqlupdate, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, finalListgrb.get(i).getWiCode());
                }

                @Override
                public int getBatchSize() {
                    return finalListgrb.size();
                }
            });
            String strinsert = "insert into pos_cloud.goods_recipebill (wi_code,ware_code,store_code,wi_count,isstorage,cargotype,gmt_creat,wi_method,intype)\n" +
                    "values(?,?,?,?,?,?,?,?,?);";
            final int[] intinsert = jdbcTemplate.batchUpdate(strinsert, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,finalListgrb.get(i).getWiCode());
                    ps.setObject(2,finalListgrb.get(i).getWareCode());
                    ps.setObject(3,finalListgrb.get(i).getStoreCode());
                    ps.setObject(4,finalListgrb.get(i).getWiCount());
                    ps.setObject(5,finalListgrb.get(i).getIsstorage());
                    ps.setObject(6,finalListgrb.get(i).getCargotype());
                    ps.setObject(7,strdate);
                    ps.setObject(8,finalListgrb.get(i).getWiMethod());
                    ps.setObject(9,finalListgrb.get(i).getIntype());
                }

                @Override
                public int getBatchSize() {
                    return finalListgrb.size();
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(intinsert.length);
            resultInfo.setData("新建"+intinsert.length+"条手持机校验单，请尽快校验！");
        }catch (Exception es){
            resultInfo.setCount(1);
            resultInfo.setCode(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getStoreVerifyList(String storeCode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GoodsRecipeBill> listgrb = new ArrayList<GoodsRecipeBill>();
        try{
            String strsql = "select a.*,b.store_name as ware_name,c.store_name,d.car_desc,e.lg_method ,f.in_desc from pos_cloud.goods_recipebill a \n" +
                    "left join pos_cloud.sta_store b on a.ware_code = b.store_code\n" +
                    "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                    "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                    "left join pos_cloud.sta_logistics e on a.wi_method = e.lg_code\n" +
                    "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                    "where a.isstorage = '1' and a.store_code ='"+storeCode+"';";
            listgrb = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsRecipeBill>() {
                @Override
                public GoodsRecipeBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsRecipeBill grb = new GoodsRecipeBill();
                    grb.setWiCode(rs.getString("wi_code"));
                    grb.setWareCode(rs.getString("ware_code"));
                    grb.setWareName(rs.getString("ware_name"));
                    grb.setStoreCode(rs.getString("store_code"));
                    grb.setStoreName(rs.getString("store_name"));
                    grb.setWiCount(rs.getString("wi_count"));
                    grb.setWiMethod(rs.getString("wi_method"));
                    grb.setLgMethod(rs.getString("lg_method"));
                    grb.setIsstorage(rs.getString("isstorage"));
                    grb.setCargotype(rs.getString("cargotype"));
                    grb.setCargodesc(rs.getString("car_desc"));
                    grb.setIntype(rs.getString("intype"));
                    grb.setIntypedesc(rs.getString("in_desc"));
                    grb.setRecipeCount(rs.getString("recipe_count"));
                    grb.setRecipeDesc(rs.getString("recipe_desc"));
                    grb.setGmtCreat(rs.getString("gmt_creat"));
                    grb.setGmtModify(rs.getString("gmt_modify"));
                    return grb;
                }
            });
            resultInfo.setCount(listgrb.size());
            resultInfo.setCode(0);
            resultInfo.setData(JSONArray.fromObject(listgrb).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getPDAStoreVerifyList(String strstorecode, String strbilltyoe) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        PDARecipeBill  pdabilldtl = new PDARecipeBill();
        List<GoodsRecipeBill> listgrbill = new ArrayList<GoodsRecipeBill>();
        List<GWInvoiceDtl> listgrdtl = new ArrayList<GWInvoiceDtl>();
        try{
            String strsql ="";
            if(strbilltyoe.equals("0")){
                strsql = "select a.*,b.store_name as ware_name,c.store_name,d.car_desc,e.lg_method ,f.in_desc from pos_cloud.goods_recipebill a \n" +
                        "left join pos_cloud.sta_store b on a.ware_code = b.store_code\n" +
                        "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                        "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                        "left join pos_cloud.sta_logistics e on a.wi_method = e.lg_code\n" +
                        "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                        "where  a.store_code ='"+strstorecode+"' and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.gmt_creat);";
            }else if(strbilltyoe.equals("1")) {
                strsql = "select a.*,b.store_name as ware_name,c.store_name,d.car_desc,e.lg_method ,f.in_desc from pos_cloud.goods_recipebill a \n" +
                        "left join pos_cloud.sta_store b on a.ware_code = b.store_code\n" +
                        "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                        "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                        "left join pos_cloud.sta_logistics e on a.wi_method = e.lg_code\n" +
                        "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                        "where a.isstorage = '1' and a.store_code ='" + strstorecode + "'  and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.gmt_creat);";
            }else if(strbilltyoe.equals("2")){
                strsql = "select a.*,b.store_name as ware_name,c.store_name,d.car_desc,e.lg_method ,f.in_desc from pos_cloud.goods_recipebill a \n" +
                        "left join pos_cloud.sta_store b on a.ware_code = b.store_code\n" +
                        "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                        "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                        "left join pos_cloud.sta_logistics e on a.wi_method = e.lg_code\n" +
                        "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                        "where a.isstorage = '2' and a.store_code ='" + strstorecode + "' and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.gmt_creat);";
            }
            listgrbill = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsRecipeBill>() {
                @Override
                public GoodsRecipeBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsRecipeBill grb = new GoodsRecipeBill();
                    grb.setWiCode(rs.getString("wi_code"));
                    grb.setWareCode(rs.getString("ware_code"));
                    grb.setWareName(rs.getString("ware_name"));
                    grb.setStoreCode(rs.getString("store_code"));
                    grb.setStoreName(rs.getString("store_name"));
                    grb.setWiCount(rs.getString("wi_count"));
                    grb.setWiMethod(rs.getString("wi_method"));
                    grb.setLgMethod(rs.getString("lg_method"));
                    grb.setIsstorage(rs.getString("isstorage"));
                    grb.setCargotype(rs.getString("cargotype"));
                    grb.setCargodesc(rs.getString("car_desc"));
                    grb.setIntype(rs.getString("intype"));
                    grb.setIntypedesc(rs.getString("in_desc"));
                    grb.setRecipeCount(rs.getString("recipe_count"));
                    grb.setRecipeDesc(rs.getString("recipe_desc"));
                    grb.setGmtCreat(rs.getString("gmt_creat"));
                    grb.setGmtModify(rs.getString("gmt_modify"));
                    return grb;
                }
            });
            for(int i =0;i<listgrbill.size();i++){
                List<GWInvoiceDtl> listdtl = new ArrayList<GWInvoiceDtl>();
                String strsqldtl = "SELECT * FROM pos_cloud.ware_invoicedtl where wi_code = '"+listgrbill.get(i).getWiCode()+"';";
                listdtl = jdbcTemplate.query(strsqldtl, new ParameterizedRowMapper<GWInvoiceDtl>() {
                    @Override
                    public GWInvoiceDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GWInvoiceDtl dtl = new GWInvoiceDtl();
                        dtl.setWiCode(rs.getString("wi_code"));
                        dtl.setWiSku(rs.getString("wi_sku"));
                        dtl.setWiName(rs.getString("wi_name"));
                        dtl.setWiStyle(rs.getString("wi_style"));
                        dtl.setWiColor(rs.getString("wi_color"));
                        dtl.setWiSize(rs.getString("wi_size"));
                        dtl.setWiSizeQty(rs.getString("wi_sizeqty"));
                        dtl.setgUnique(rs.getString("g_unique"));
                        dtl.setGmtCreat(rs.getString("gmt_creat"));
                        return dtl;
                    }
                });
               listgrdtl.addAll(listdtl);
            }
            pdabilldtl.setRecipebill(JSONArray.fromObject(listgrbill).toString());
            pdabilldtl.setRecipedtl(JSONArray.fromObject(listgrdtl).toString());
            resultInfo.setCount(1);
            resultInfo.setCode(0);
            resultInfo.setData(JSONObject.fromObject(pdabilldtl).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getPDAStoreVerifyDtl(String strwicode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GWInvoiceDtl> listdtl = new ArrayList<GWInvoiceDtl>();
        String strsql = "SELECT * FROM pos_cloud.ware_invoicedtl where wi_code = '"+strwicode+"';";
        try{
            listdtl = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GWInvoiceDtl>() {
                @Override
                public GWInvoiceDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GWInvoiceDtl dtl = new GWInvoiceDtl();
                    dtl.setWiCode(rs.getString("wi_code"));
                    dtl.setWiSku(rs.getString("wi_sku"));
                    dtl.setWiName(rs.getString("wi_name"));
                    dtl.setWiStyle(rs.getString("wi_style"));
                    dtl.setWiColor(rs.getString("wi_color"));
                    dtl.setWiSize(rs.getString("wi_size"));
                    dtl.setWiSizeQty(rs.getString("wi_sizeqty"));
                    dtl.setgUnique(rs.getString("g_unique"));
                    dtl.setGmtCreat(rs.getString("gmt_creat"));
                    return dtl;
                }
            });
            resultInfo.setCount(listdtl.size());
            resultInfo.setCode(0);
            resultInfo.setData(JSONArray.fromObject(listdtl).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doPADRecipeConfirm(String strwicode, String strrecipecount, String strrecipedesc) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        String strupdate = "update pos_cloud.goods_recipebill set recipe_count ='"+strrecipecount+"',recipe_desc='"+strrecipedesc+"' where wi_code = '"+strwicode+"';";
        Date insdates = new Date();
        SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final String strdate = fins.format(insdates);
        try {
            if (strrecipedesc.equals("OK")) {
                List<StoreGoods> listdtl = new ArrayList<StoreGoods>();
                String strsql = "SELECT b.store_code,a.g_unique,a.wi_sku,a.wi_name,a.wi_style,a.wi_color,a.wi_size FROM pos_cloud.ware_invoicedtl a\n" +
                        "left join pos_cloud.ware_invoicebill b on a.wi_code =b.wi_code\n" +
                        "where a.wi_code='" + strwicode + "';";

                final String state = "1";
                listdtl = jdbcTemplate.query(strsql, new ParameterizedRowMapper<StoreGoods>() {
                    @Override
                    public StoreGoods mapRow(ResultSet rs, int rowNum) throws SQLException {
                        StoreGoods sg = new StoreGoods();
                        sg.setStoreCode(rs.getString("store_code"));
                        sg.setgGroup(state);
                        sg.setgLocation(state);
                        sg.setgUnique(rs.getString("g_unique"));
                        sg.setgSku(rs.getString("wi_sku"));
                        sg.setgName(rs.getString("wi_name"));
                        sg.setgStyle(rs.getString("wi_style"));
                        sg.setgColor(rs.getString("wi_color"));
                        sg.setgSize(rs.getString("wi_size"));
                        sg.setIsstate(state);
                        return sg;
                    }
                });
                String insertsql = " insert into pos_cloud.store_goods (store_code,g_group,g_location,g_unique,g_sku,g_name,g_style,g_color,g_size,isstate,store_indt,store_outdt,gmt_creat)\n" +
                        " values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                final List<StoreGoods> finalListdtl = listdtl;
                int[] intins = jdbcTemplate.batchUpdate(insertsql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setObject(1, finalListdtl.get(i).getStoreCode());
                        ps.setObject(2, finalListdtl.get(i).getgGroup());
                        ps.setObject(3, finalListdtl.get(i).getgLocation());
                        ps.setObject(4, finalListdtl.get(i).getgUnique());
                        ps.setObject(5, finalListdtl.get(i).getgSku());
                        ps.setObject(6, finalListdtl.get(i).getgName());
                        ps.setObject(7, finalListdtl.get(i).getgStyle());
                        ps.setObject(8, finalListdtl.get(i).getgColor());
                        ps.setObject(9, finalListdtl.get(i).getgSize());
                        ps.setObject(10, finalListdtl.get(i).getIsstate());
                        ps.setObject(11, strdate);
                        ps.setObject(12, strdate);
                        ps.setObject(13, strdate);
                    }

                    @Override
                    public int getBatchSize() {
                        return finalListdtl.size();
                    }
                });
            }
            jdbcTemplate.update(strupdate);

            String strvwi = "update pos_cloud.ware_invoicebill set isstorage = '2', gmt_modify='"+strdate+"' where wi_code ='"+strwicode+"'";
            String strgc = "update pos_cloud.goods_recipebill set isstorage ='2', gmt_modify='"+strdate+"' where wi_code='"+strwicode+"'";

            jdbcTemplate.update(strvwi);
            jdbcTemplate.update(strgc);

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

    public String doPDARecipeConfirmList(String strrclist) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<PDARecipeConfirm> listrc = new ArrayList<PDARecipeConfirm>();
        try{
            listrc = JSONArray.toList(JSONArray.fromObject(strrclist),PDARecipeConfirm.class);
            if(listrc.size() > 0){
                for(int i = 0;i<listrc.size();i++){
                    Date insdates = new Date();
                    SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    final String strdate = fins.format(insdates);
                    if (listrc.get(i).getRecipedesc().equals("OK")) {

                        List<StoreGoods> listdtl = new ArrayList<StoreGoods>();
                        String strsql = "SELECT b.store_code,a.g_unique,a.wi_sku,a.wi_name,a.wi_style,a.wi_color,a.wi_size FROM pos_cloud.ware_invoicedtl a\n" +
                                "left join pos_cloud.ware_invoicebill b on a.wi_code =b.wi_code\n" +
                                "where a.wi_code='" + listrc.get(i).getWicode() + "';";
                        final String state = "1";
                        listdtl = jdbcTemplate.query(strsql, new ParameterizedRowMapper<StoreGoods>() {
                            @Override
                            public StoreGoods mapRow(ResultSet rs, int rowNum) throws SQLException {
                                StoreGoods sg = new StoreGoods();
                                sg.setStoreCode(rs.getString("store_code"));
                                sg.setgGroup(state);
                                sg.setgLocation(state);
                                sg.setgUnique(rs.getString("g_unique"));
                                sg.setgSku(rs.getString("wi_sku"));
                                sg.setgName(rs.getString("wi_name"));
                                sg.setgStyle(rs.getString("wi_style"));
                                sg.setgColor(rs.getString("wi_color"));
                                sg.setgSize(rs.getString("wi_size"));
                                sg.setIsstate(state);
                                return sg;
                            }
                        });
                        String insertsql = " insert into pos_cloud.store_goods (store_code,g_group,g_location,g_unique,g_sku,g_name,g_style,g_color,g_size,isstate,store_indt,store_outdt,gmt_creat)\n" +
                                " values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                        final List<StoreGoods> finalListdtl = listdtl;
                        int[] intins = jdbcTemplate.batchUpdate(insertsql, new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                ps.setObject(1, finalListdtl.get(i).getStoreCode());
                                ps.setObject(2, finalListdtl.get(i).getgGroup());
                                ps.setObject(3, finalListdtl.get(i).getgLocation());
                                ps.setObject(4, finalListdtl.get(i).getgUnique());
                                ps.setObject(5, finalListdtl.get(i).getgSku());
                                ps.setObject(6, finalListdtl.get(i).getgName());
                                ps.setObject(7, finalListdtl.get(i).getgStyle());
                                ps.setObject(8, finalListdtl.get(i).getgColor());
                                ps.setObject(9, finalListdtl.get(i).getgSize());
                                ps.setObject(10, finalListdtl.get(i).getIsstate());
                                ps.setObject(11, strdate);
                                ps.setObject(12, strdate);
                                ps.setObject(13, strdate);
                            }

                            @Override
                            public int getBatchSize() {
                                return finalListdtl.size();
                            }
                        });
                    }

                    String strupdate = "update pos_cloud.goods_recipebill set recipe_count ='"+listrc.get(i).getRecipecount()+"'," +
                            "recipe_desc='"+listrc.get(i).getRecipedesc()+"' where wi_code = '"+listrc.get(i).getWicode()+"';";
                    String strvwi = "update pos_cloud.ware_invoicebill set isstorage = '2', gmt_modify='"+strdate+"' where wi_code ='"+listrc.get(i).getWicode()+"'";
                    String strgc = "update pos_cloud.goods_recipebill set isstorage ='2', gmt_modify='"+strdate+"' where wi_code='"+listrc.get(i).getWicode()+"'";
                    jdbcTemplate.update(strupdate);
                    jdbcTemplate.update(strvwi);
                    jdbcTemplate.update(strgc);
                }
            }else{
                resultInfo.setCode(1);
                resultInfo.setCount(1);
                resultInfo.setData("at lest one more info");
            }
            resultInfo.setCode(0);
            resultInfo.setCount(listrc.size());
            resultInfo.setData("Update:"+listrc.size()+"confirminfo");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doRecipeBillGoods(String strwicode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{
            final String[] strvlist = strwicode.substring(0,strwicode.length()-1).split(",");
            String strvwi = "update pos_cloud.ware_invoicebill set isstorage = '2' where wi_code =?";
            String strgc = "update pos_cloud.goods_recipebill set isstorage ='2' where wi_code=?";
            int[] inss= jdbcTemplate.batchUpdate(strvwi, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,strvlist[i]);
                }

                @Override
                public int getBatchSize() {
                    return strvlist.length;
                }
            });
            int[] ins = jdbcTemplate.batchUpdate(strgc, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,strvlist[i]);
                }

                @Override
                public int getBatchSize() {
                    return strvlist.length;
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

    public String doQueryStoreWare(String storeCode, String strsku, String strstyle, String strsize) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GoodsStoreSearch> listsw = new ArrayList<GoodsStoreSearch>();
        try{
            String strsql = "SELECT *,count(g_unique) as g_count,b.store_name FROM pos_cloud.store_goods a\n" +
                    "left join sta_store b on a.store_code = b.store_code \n" +
                    "where a.isstate ='1' and a.store_code like '%"+storeCode+"%' and a.g_sku like '%"+strsku+"%' \n" +
                    "and a.g_style like '%"+strstyle+"%' and a.g_size like '%"+strsize+"%' \n" +
                    "group by a.g_sku;";
            listsw = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsStoreSearch>() {
                @Override
                public GoodsStoreSearch mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsStoreSearch gs = new GoodsStoreSearch();
                    gs.setStoreCode(rs.getString("store_code"));
                    gs.setStoreName(rs.getString("store_name"));
                    gs.setgGroup(rs.getString("g_group"));
                    gs.setgLocation(rs.getString("g_location"));
                    gs.setgCount(rs.getString("g_count"));
                    gs.setgSku(rs.getString("g_sku"));
                    gs.setgName(rs.getString("g_name"));
                    gs.setgStyle(rs.getString("g_style"));
                    gs.setgColor(rs.getString("g_color"));
                    gs.setgSize(rs.getString("g_size"));
                    gs.setIsstate(rs.getString("isstate"));
                    gs.setStoreIndt(rs.getString("store_indt"));
                    gs.setStoreOutdt(rs.getString("store_outdt"));
                    gs.setGmtCreat(rs.getString("gmt_creat"));
                    return gs;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listsw.size());
            resultInfo.setData(JSONArray.fromObject(listsw).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getDeliverMethod() {
        List<StaDeliverM> listdm = new ArrayList<StaDeliverM>();
        String strsql = "SELECT * FROM pos_cloud.sta_cargotype;";
        listdm = jdbcTemplate.query(strsql, new ParameterizedRowMapper<StaDeliverM>() {
            @Override
            public StaDeliverM mapRow(ResultSet rs, int rowNum) throws SQLException {
                StaDeliverM dm = new StaDeliverM();
                dm.setCarCode(rs.getString("car_code"));
                dm.setCarDesc(rs.getString("car_desc"));
                return dm;
            }
        });
        return JSONArray.fromObject(listdm).toString();
    }

    public String doUpDeliverBill(String storeCode, String empCode, String strdebill, String strdedtl) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        GoodsDeliverBill listdbill = new GoodsDeliverBill();
        List<GoodsDeliverDtl> listddtl = new ArrayList<GoodsDeliverDtl>();
        final List<GoodsDeliverDtl> listindtl = new ArrayList<GoodsDeliverDtl>();

        String strgdbcode = "";
        String strgdblastcode = "";
        try {
            listdbill = (GoodsDeliverBill) JSONObject.toBean(JSONObject.fromObject(strdebill),GoodsDeliverBill.class);
            listddtl = JSONArray.toList(JSONArray.fromObject(strdedtl.replace("\\","")),GoodsDeliverDtl.class);
            listdbill.setRecipeStore(storeCode);
            listdbill.setGdbEmp(empCode);

            String strl = "";
            Date insdate = new Date();
            final SimpleDateFormat fin = new SimpleDateFormat("yyMMdd");
            strl = fin.format(insdate);
            strgdbcode = storeCode + strl;
            try{
                String strlsql = "SELECT gdb_code FROM pos_cloud.goods_deliverbill where gdb_code like '%"+strgdbcode+"%' order by gdb_code DESC limit 1;";
                strgdblastcode = jdbcTemplate.queryForObject(strlsql,String.class);
            }catch (EmptyResultDataAccessException e) {
                strgdblastcode = null;
            }
            if(strgdblastcode == null){
                strgdbcode = strgdbcode + String.format("%03d",1);
            }else{
                int intc = Integer.parseInt(strgdblastcode.substring(strgdblastcode.length()-3, strgdblastcode.length()));
                intc++;
                strgdbcode = strgdbcode + String.format("%03d",intc);
            }
            listdbill.setGdbCode(strgdbcode);
            for(int i =0;i<listddtl.size();i++) {
                List<GWTempUnique> listsg = new ArrayList<GWTempUnique>();
                int siq = Integer.parseInt(listddtl.get(i).getGdbSizeQty());
                String strsqlwi = "SELECT g_name,g_style,g_color,g_size FROM pos_cloud.store_goods where g_sku = '" + listddtl.get(i).getGdbSku() + "' and isstate ='1' order by g_sku limit 1;";
                listsg = jdbcTemplate.query(strsqlwi, new ParameterizedRowMapper<GWTempUnique>() {
                    @Override
                    public GWTempUnique mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GWTempUnique ss = new GWTempUnique();
                        ss.setWiName(rs.getString("g_name"));
                        ss.setWiStyle(rs.getString("g_style"));
                        ss.setWiColor(rs.getString("g_color"));
                        ss.setWiSize(rs.getString("g_size"));
                        return ss;
                    }
                });
                for (int j = 0; j < listsg.size(); j++) {
                    GoodsDeliverDtl gtl = new GoodsDeliverDtl();
                    gtl.setGdbCode(strgdbcode);
                    gtl.setGdbSku(listddtl.get(i).getGdbSku());
                    gtl.setGdbName(listsg.get(j).getWiName());
                    gtl.setGdbStyle(listsg.get(j).getWiStyle());
                    gtl.setGdbColor(listsg.get(j).getWiColor());
                    gtl.setGdbSize(listsg.get(j).getWiSize());
                    gtl.setGdbSizeQty(listddtl.get(i).getGdbSizeQty());
                    listindtl.add(gtl);
                }
            }
            Date insdates = new Date();
            SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            final String strdate = fins.format(insdates);
            String strinsbill = "insert into pos_cloud.goods_deliverbill (gdb_code,recipe_store,invoice_store,gdb_count,cargotype,isverify,isinvoice,isstorage,gdb_emp,gmt_creat,intype)\n" +
                    "values(?,?,?,?,?,?,?,?,?,?,?)";
            final GoodsDeliverBill finalListdbill = listdbill;
            jdbcTemplate.update(strinsbill, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1, finalListdbill.getGdbCode());
                    ps.setObject(2,finalListdbill.getRecipeStore());
                    ps.setObject(3,finalListdbill.getInvoiceStore());
                    ps.setObject(4,finalListdbill.getGdbCount());
                    ps.setObject(5,finalListdbill.getCargotype());
                    ps.setObject(6,finalListdbill.getIsverify());
                    ps.setObject(7,finalListdbill.getIsinvoice());
                    ps.setObject(8,finalListdbill.getIsstorage());
                    ps.setObject(9,finalListdbill.getGdbEmp());
                    ps.setObject(10,strdate);
                    ps.setObject(11,finalListdbill.getIntype());
                }
            });
            String strinsdtl = "insert into pos_cloud.goods_deliverdtl (gdb_code,gdb_sku,gdb_name,gdb_style,gdb_color,gdb_size,gdb_sizeqty,gmt_creat)\n" +
                    "values(?,?,?,?,?,?,?,?);";
            int[] ints = jdbcTemplate.batchUpdate(strinsdtl, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,listindtl.get(i).getGdbCode());
                    ps.setObject(2,listindtl.get(i).getGdbSku());
                    ps.setObject(3,listindtl.get(i).getGdbName());
                    ps.setObject(4,listindtl.get(i).getGdbStyle());
                    ps.setObject(5,listindtl.get(i).getGdbColor());
                    ps.setObject(6,listindtl.get(i).getGdbSize());
                    ps.setObject(7,listindtl.get(i).getGdbSizeQty());
                    ps.setObject(8,strdate);
                }

                @Override
                public int getBatchSize() {
                    return listindtl.size();
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("成功新建调货请求单："+strgdbcode+"\n 请审核并通知发货方尽快发货");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();

    }

    public String getDeliverBill(String storeCode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GoodsDeliverBill> listgb = new ArrayList<GoodsDeliverBill>();
        String strsql = "SELECT a.*,b.store_name as recipe_name ,c.store_name as invoice_name ,d.car_desc as cargodesc FROM pos_cloud.goods_deliverbill a\n" +
                "left join pos_cloud.sta_store b on a.recipe_store = b.store_code\n" +
                "left join pos_cloud.sta_store c on a.invoice_store = c.store_code\n" +
                "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                " where a.recipe_store ='"+storeCode+"';";
        try{
            listgb = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsDeliverBill>() {
                @Override
                public GoodsDeliverBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsDeliverBill gb = new GoodsDeliverBill();
                    gb.setGdbCode(rs.getString("gdb_code"));
                    gb.setRecipeStore(rs.getString("recipe_store"));
                    gb.setRecipeName(rs.getString("recipe_name"));
                    gb.setInvoiceStore(rs.getString("invoice_store"));
                    gb.setInvoiceName(rs.getString("invoice_name"));
                    gb.setGdbCount(rs.getString("gdb_count"));
                    gb.setCargotype(rs.getString("cargotype"));
                    gb.setCargodesc(rs.getString("cargodesc"));
                    gb.setIsverify(rs.getString("isverify"));
                    gb.setIsinvoice(rs.getString("isinvoice"));
                    gb.setIsstorage(rs.getString("isstorage"));
                    gb.setGdbEmp(rs.getString("gdb_emp"));
                    gb.setGmtCreat(rs.getString("gmt_creat"));
                    gb.setGmtModify(rs.getString("gmt_modify"));
                    return gb;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listgb.size());
            resultInfo.setData(JSONArray.fromObject(listgb).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getDeliverDtl(String strgdbcode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GoodsDeliverDtl> listgbt = new ArrayList<GoodsDeliverDtl>();
        String strsql = "SELECT * FROM pos_cloud.goods_deliverdtl where gdb_code = '"+strgdbcode+"';";
        try{
            listgbt = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsDeliverDtl>() {
                @Override
                public GoodsDeliverDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsDeliverDtl gt = new GoodsDeliverDtl();
                    gt.setGdbCode(rs.getString("gdb_code"));
                    gt.setGdbSku(rs.getString("gdb_sku"));
                    gt.setGdbName(rs.getString("gdb_name"));
                    gt.setGdbStyle(rs.getString("gdb_style"));
                    gt.setGdbColor(rs.getString("gdb_color"));
                    gt.setGdbSize(rs.getString("gdb_size"));
                    gt.setGdbSizeQty(rs.getString("gdb_sizeqty"));
                    return gt;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData(JSONArray.fromObject(listgbt).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doVerifyDeliverBill(String strgdbcode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        final String[] strvlist = strgdbcode.substring(0,strgdbcode.length()-1).split(",");
        String strsql = "update pos_cloud.goods_deliverbill set isverify = '1' where gdb_code =?";
        try{
            int[] ins = jdbcTemplate.batchUpdate(strsql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,strvlist[i]);
                }
                @Override
                public int getBatchSize() {
                    return strvlist.length;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(ins.length);
            resultInfo.setData("OK");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getNotesDeliverBill(String storeCode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GoodsDeliverBill> listgb = new ArrayList<GoodsDeliverBill>();
        String strsql = "SELECT a.*,b.store_name as recipe_name ,c.store_name as invoice_name ,d.car_desc as cargodesc FROM pos_cloud.goods_deliverbill a\n" +
                "left join pos_cloud.sta_store b on a.recipe_store = b.store_code\n" +
                "left join pos_cloud.sta_store c on a.invoice_store = c.store_code\n" +
                "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                " where a.invoice_store ='"+storeCode+"';";
        try{
            listgb = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsDeliverBill>() {
                @Override
                public GoodsDeliverBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsDeliverBill gb = new GoodsDeliverBill();
                    gb.setGdbCode(rs.getString("gdb_code"));
                    gb.setRecipeStore(rs.getString("recipe_store"));
                    gb.setRecipeName(rs.getString("recipe_name"));
                    gb.setInvoiceStore(rs.getString("invoice_store"));
                    gb.setInvoiceName(rs.getString("invoice_name"));
                    gb.setGdbCount(rs.getString("gdb_count"));
                    gb.setCargotype(rs.getString("cargotype"));
                    gb.setCargodesc(rs.getString("cargodesc"));
                    gb.setIsverify(rs.getString("isverify"));
                    gb.setIsinvoice(rs.getString("isinvoice"));
                    gb.setIsstorage(rs.getString("isstorage"));
                    gb.setGdbEmp(rs.getString("gdb_emp"));
                    gb.setGmtCreat(rs.getString("gmt_creat"));
                    gb.setGmtModify(rs.getString("gmt_modify"));
                    return gb;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listgb.size());
            resultInfo.setData(JSONArray.fromObject(listgb).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doCreatDeliver(String strgdbcode, String strwlmethod, String strwlcode, String empCode) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        String strsql = "update pos_cloud.goods_deliverbill set isinvoice ='1' ,isstorage ='1'," +
                "wl_code='"+strwlcode+"',wl_method='"+strwlmethod+"',gdi_emp='"+empCode+"' where gdb_code ='"+strgdbcode+"'";
        try{
            jdbcTemplate.update(strsql);
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("新建发货确认单成功，请尽快在PDA确认发货");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getPDADeliverInfo(String strstorecode, String strbilltyoe) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        PDADeliverBill  pdabilldtl = new PDADeliverBill();
        List<GoodsDeliverBill> listgdbill = new ArrayList<GoodsDeliverBill>();
        List<GoodsDeliverDtl> listgddtl = new ArrayList<GoodsDeliverDtl>();
        try{
            String strsql ="";
            if(strbilltyoe.equals("0")){
                strsql = "SELECT a.*,b.store_name as recipe_name ,c.store_name as invoice_name ,d.car_desc as cargodesc,e.lg_method ,f.in_desc FROM pos_cloud.goods_deliverbill a\n" +
                        "left join pos_cloud.sta_store b on a.recipe_store = b.store_code\n" +
                        "left join pos_cloud.sta_store c on a.invoice_store = c.store_code\n" +
                        "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                        "left join pos_cloud.sta_logistics e on e.lg_code = a.wl_method\n" +
                        "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                        "where a.isinvoice <>'0' AND a.invoice_store ='"+strstorecode+"' and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.gmt_creat);";
            }else if(strbilltyoe.equals("1")) {
                strsql = "SELECT a.*,b.store_name as recipe_name ,c.store_name as invoice_name ,d.car_desc as cargodesc,e.lg_method ,f.in_desc FROM pos_cloud.goods_deliverbill a\n" +
                        "left join pos_cloud.sta_store b on a.recipe_store = b.store_code\n" +
                        "left join pos_cloud.sta_store c on a.invoice_store = c.store_code\n" +
                        "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                        "left join pos_cloud.sta_logistics e on e.lg_code = a.wl_method\n" +
                        "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                        "where a.isinvoice = '1' and a.invoice_store ='"+strstorecode+"' and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.gmt_creat);";
            }else if(strbilltyoe.equals("2")){
                strsql = "SELECT a.*,b.store_name as recipe_name ,c.store_name as invoice_name ,d.car_desc as cargodesc,e.lg_method ,f.in_desc FROM pos_cloud.goods_deliverbill a\n" +
                        "left join pos_cloud.sta_store b on a.recipe_store = b.store_code\n" +
                        "left join pos_cloud.sta_store c on a.invoice_store = c.store_code\n" +
                        "left join pos_cloud.sta_cargotype d on a.cargotype = d.car_code\n" +
                        "left join pos_cloud.sta_logistics e on e.lg_code = a.wl_method\n" +
                        "left join pos_cloud.sta_invoicetype f on a.intype = f.in_code\n" +
                        "where a.isinvoice = '2' and a.invoice_store ='"+strstorecode+"' and DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(a.gmt_creat);";
            }
            listgdbill =jdbcTemplate.query(strsql, new ParameterizedRowMapper<GoodsDeliverBill>() {
                @Override
                public GoodsDeliverBill mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GoodsDeliverBill gb = new GoodsDeliverBill();
                    gb.setGdbCode(rs.getString("gdb_code"));
                    gb.setRecipeStore(rs.getString("recipe_store"));
                    gb.setRecipeName(rs.getString("recipe_name"));
                    gb.setInvoiceStore(rs.getString("invoice_store"));
                    gb.setInvoiceName(rs.getString("invoice_name"));
                    gb.setGdbCount(rs.getString("gdb_count"));
                    gb.setCargotype(rs.getString("cargotype"));
                    gb.setCargodesc(rs.getString("cargodesc"));
                    gb.setIsverify(rs.getString("isverify"));
                    gb.setIsinvoice(rs.getString("isinvoice"));
                    gb.setIsstorage(rs.getString("isstorage"));
                    gb.setGdbEmp(rs.getString("gdb_emp"));
                    gb.setGmtCreat(rs.getString("gmt_creat"));
                    gb.setGmtModify(rs.getString("gmt_modify"));
                    gb.setWlCode(rs.getString("wl_code"));
                    gb.setWlMethod(rs.getString("wl_method"));
                    gb.setLgMethod(rs.getString("lg_method"));
                    gb.setGdiEmp(rs.getString("gdi_emp"));
                    gb.setIntype(rs.getString("intype"));
                    gb.setIntypedesc(rs.getString("in_desc"));
                    return gb;
                }
            });
            for(int i =0;i<listgdbill.size();i++){
                List<GoodsDeliverDtl> listdtl = new ArrayList<GoodsDeliverDtl>();
                String strsqldtl = "SELECT * FROM pos_cloud.goods_deliverdtl where gdb_code = '"+listgdbill.get(i).getGdbCode()+"';";
                listdtl = jdbcTemplate.query(strsqldtl, new ParameterizedRowMapper<GoodsDeliverDtl>() {
                    @Override
                    public GoodsDeliverDtl mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GoodsDeliverDtl gt = new GoodsDeliverDtl();
                        gt.setGdbCode(rs.getString("gdb_code"));
                        gt.setGdbSku(rs.getString("gdb_sku"));
                        gt.setGdbName(rs.getString("gdb_name"));
                        gt.setGdbStyle(rs.getString("gdb_style"));
                        gt.setGdbColor(rs.getString("gdb_color"));
                        gt.setGdbSize(rs.getString("gdb_size"));
                        gt.setGdbSizeQty(rs.getString("gdb_sizeqty"));
                        return gt;
                    }
                });
                listgddtl.addAll(listdtl);
            }
            pdabilldtl.setDeliverBill(JSONArray.fromObject(listgdbill).toString());
            pdabilldtl.setDeliverDtl(JSONArray.fromObject(listgddtl).toString());
            resultInfo.setCount(1);
            resultInfo.setCode(0);
            resultInfo.setData(JSONObject.fromObject(pdabilldtl).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doPDAUpDeliverInfo(int strcount, String strdata) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        PDADeliverBill  pdabilldtl = new PDADeliverBill();
        List<GoodsDeliverBill> listgdbill = new ArrayList<GoodsDeliverBill>();
        List<GoodsDeliverDtl> listgddtl = new ArrayList<GoodsDeliverDtl>();
        try{
            String strbill = JSONObject.fromObject(strdata).getString("deliverBill");
            String strdtl = JSONObject.fromObject(strdata).getString("deliverDtl");

            listgdbill = JSONArray.toList(JSONArray.fromObject(strbill),GoodsDeliverBill.class);
            listgddtl = JSONArray.toList(JSONArray.fromObject(strdtl),GoodsDeliverDtl.class);

            for(int i =0;i<listgdbill.size();i++){
                String strwibcode = "";
                String strlastwicode = "";
                String strl = "";
                Date insdate = new Date();
                SimpleDateFormat fin = new SimpleDateFormat("yyMMdd");
                strl = fin.format(insdate);
                strwibcode = listgdbill.get(i).getInvoiceStore() + strl;
                try{
                    String strlsql = "SELECT wi_code FROM pos_cloud.ware_invoicebill where wi_code like '%"+strwibcode+"%' order by wi_code DESC limit 1;";
                    strlastwicode = jdbcTemplate.queryForObject(strlsql,String.class);
                }catch (EmptyResultDataAccessException e) {
                    strlastwicode = null;
                }
                if(strlastwicode == null){
                    strwibcode = strwibcode + String.format("%03d",1);
                }else{
                    int intc = Integer.parseInt(strlastwicode.substring(strlastwicode.length()-3, strlastwicode.length()));
                    intc++;
                    strwibcode = strwibcode + String.format("%03d",intc);
                }
                String strinswib = "insert into pos_cloud.ware_invoicebill (wi_code,ware_code,store_code,wl_code,wi_method,wi_count,wi_emp,isverify,isstorage,intype,cargotype,gmt_creat,gmt_modify)\n" +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                Date insdates = new Date();
                SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                final String strdate = fins.format(insdates);
                final String finalStrwibcode = strwibcode;
                final GoodsDeliverBill finalListgdbill = listgdbill.get(i);
                jdbcTemplate.update(strinswib, new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setObject(1, finalStrwibcode);
                        ps.setObject(2, finalListgdbill.getInvoiceStore());
                        ps.setObject(3,finalListgdbill.getRecipeStore());
                        ps.setObject(4,finalListgdbill.getWlCode());
                        ps.setObject(5,finalListgdbill.getWlMethod());
                        ps.setObject(6,finalListgdbill.getGdbCount());
                        ps.setObject(7,finalListgdbill.getGdiEmp());
                        ps.setObject(8,finalListgdbill.getIsverify());
                        ps.setObject(9,finalListgdbill.getIsstorage());
                        ps.setObject(10,finalListgdbill.getIntype());
                        ps.setObject(11,finalListgdbill.getCargotype());
                        ps.setObject(12,strdate);
                        ps.setObject(13,strdate);
                    }
                });

                for(int j= 0;j<listgddtl.size();j++){
                    if(listgddtl.get(j).getGdbCode().equals(listgdbill.get(i).getGdbCode())){
                        String strinswid = "insert into pos_cloud.ware_invoicedtl(wi_code,wi_sku,wi_name,wi_style,wi_color,wi_size,wi_sizeqty,g_unique,gmt_creat)\n" +
                                "values(?,?,?,?,?,?,?,?,?)";
                        final GoodsDeliverDtl finalListgddtl = listgddtl.get(j);
                        jdbcTemplate.update(strinswid, new PreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps) throws SQLException {
                                ps.setObject(1,finalStrwibcode);
                                ps.setObject(2, finalListgddtl.getGdbSku());
                                ps.setObject(3,finalListgddtl.getGdbName());
                                ps.setObject(4,finalListgddtl.getGdbStyle());
                                ps.setObject(5,finalListgddtl.getGdbColor());
                                ps.setObject(6,finalListgddtl.getGdbSize());
                                ps.setObject(7,finalListgddtl.getGdbSizeQty());
                                ps.setObject(8,finalListgddtl.getgUnique());
                                ps.setObject(9,strdate);
                            }
                        });
                    }
                }
                String strupbill = "update pos_cloud.goods_deliverbill set isinvoice ='2'," +
                        "wl_code='"+listgdbill.get(i).getWlCode()+"'," +
                        "wl_method='"+listgdbill.get(i).getWlMethod()+"'," +
                        "gmt_modify='"+strdate+"'," +
                        "gdi_emp='"+listgdbill.get(i).getGdiEmp()+"' where gdb_code ='"+listgdbill.get(i).getGdbCode()+"'";
                jdbcTemplate.update(strupbill);
            }

        } catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }
}