package com.cpos.dao;

import com.cpos.classes.ResultInfo;
import com.cpos.classes.StaStoreInfo;
import com.cpos.classes.geers.*;
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

public class GeerDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String geerStoreList;

    public String getGeerEmpList() {
        List<GempInfo> listEmp  = new ArrayList<GempInfo>();
        String str = "SELECT a.*,b.store_name,c.duty_name FROM pos_cloud.sta_employee a\n" +
                "left join pos_cloud.sta_store b on a.store_code = b.store_code\n" +
                "left join pos_cloud.sta_empduty c on a.emp_duty = c.duty_code;";
        try{
            listEmp = jdbcTemplate.query(str, new ParameterizedRowMapper<GempInfo>() {
                @Override
                public GempInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GempInfo gp = new GempInfo();
                    gp.setEmpCode(rs.getString("emp_code"));
                    gp.setEmpName(rs.getString("emp_name"));
                    gp.setStoreCode(rs.getString("store_code"));
                    gp.setStoreName(rs.getString("store_name"));
                    gp.setEmpDuty(rs.getString("emp_Duty"));
                    gp.setDutyName(rs.getString("duty_name"));
                    gp.setEmpMail(rs.getString("emp_mail"));
                    gp.setEmpTel(rs.getString("emp_tel"));
                    gp.setIsquery(rs.getString("isquery"));
                    gp.setGmtCreat(rs.getString("gmt_creat"));
                    gp.setGmtModify(rs.getString("gmt_modify"));
                    return gp;
                }
            });
        }catch (Exception es){
            return es.getMessage().toString();
        }
        JSONArray js = JSONArray.fromObject(listEmp);
        return js.toString();
    }

    public String getGeerStoreList() {
        List<StaStoreInfo> lists = new ArrayList<StaStoreInfo>();
        String strsql = "SELECT store_code,store_name FROM pos_cloud.sta_store;";
        try{
            lists = jdbcTemplate.query(strsql, new ParameterizedRowMapper<StaStoreInfo>() {
                @Override
                public StaStoreInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    StaStoreInfo gs=new StaStoreInfo();
                    gs.setStoreCode(rs.getString("store_code"));
                    gs.setStoreName(rs.getString("store_name"));
                    return gs;
                }
            });
        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(lists).toString();
    }

    public String getGeetDutyList() {
        List<GDutyInfo> listd = new ArrayList<GDutyInfo>();
        String strsql = "SELECT * FROM pos_cloud.sta_empduty;";
        listd = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GDutyInfo>() {
            @Override
            public GDutyInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                GDutyInfo gd = new GDutyInfo();
                gd.setDutyCode(rs.getString("duty_code"));
                gd.setDutyName(rs.getString("duty_name"));
                return gd;
            }
        });
        return JSONArray.fromObject(listd).toString();
    }

    public String doGeerUpdateEmp(String stremp) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{

            final GempInfo gm = (GempInfo) JSONObject.toBean(JSONObject.fromObject(stremp),GempInfo.class);
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String strDate = f.format(date);
            String strsql = "update pos_cloud.sta_employee set emp_name=?,store_code=?,emp_duty=?,emp_mail=?,emp_tel=?,gmt_modify=? where emp_code=?";
            jdbcTemplate.update(strsql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1,gm.getEmpName());
                    ps.setObject(2,gm.getStoreCode());
                    ps.setObject(3,gm.getEmpDuty());
                    ps.setObject(4,gm.getEmpMail());
                    ps.setObject(5,gm.getEmpTel());
                    ps.setObject(6,strDate);
                    ps.setObject(7,gm.getEmpCode());
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("修改一条员工信息成功！");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doGeerUpEmpinfo(String stremp) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{
            String strlastemp = "";
            final GempInfo gm = (GempInfo) JSONObject.toBean(JSONObject.fromObject(stremp),GempInfo.class);
            try{
                String strsqllast = "select emp_code from pos_cloud.sta_employee where store_code ='"+gm.getStoreCode()+"' order by emp_code desc limit 1";
                strlastemp = jdbcTemplate.queryForObject(strsqllast, java.lang.String.class);
            }catch (EmptyResultDataAccessException enull){
                strlastemp = "";
            }
            if(strlastemp == ""){
                gm.setEmpCode(gm.getStoreCode()+String.format("%02d",1));
            }else{
                int intcode = Integer.parseInt(strlastemp.substring(strlastemp.length()-2,strlastemp.length()));
                intcode++;
                gm.setEmpCode(gm.getStoreCode()+String.format("%02d",intcode));
            }
            gm.setEmpPsw("1234");
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String strDate = f.format(date);
            String strinssql = "insert into pos_cloud.sta_employee (emp_code,emp_name,store_code,emp_duty,emp_psw,emp_mail,emp_tel,gmt_creat,gmt_modify,isquery)\n" +
                    "values(?,?,?,?,?,?,?,?,?,?);";
            jdbcTemplate.update(strinssql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1,gm.getEmpCode());
                    ps.setObject(2,gm.getEmpName());
                    ps.setObject(3,gm.getStoreCode());
                    ps.setObject(4,gm.getEmpDuty());
                    ps.setObject(5,gm.getEmpPsw());
                    ps.setObject(6,gm.getEmpMail());
                    ps.setObject(7,gm.getEmpTel());
                    ps.setObject(8,strDate);
                    ps.setObject(9,strDate);
                    ps.setObject(10,gm.getIsquery());
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("上传一条员工信息成功！");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doGeerQueryEmps(String stremps) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{
            int[] ins = null;
            stremps = stremps.substring(0,stremps.length()-1);
            final String[] listemp = stremps.split(",");
            String strsql = "update pos_cloud.sta_employee set isquery = '1' where emp_code =?";
            ins = jdbcTemplate.batchUpdate(strsql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,listemp[i]);
                }

                @Override
                public int getBatchSize() {
                    return listemp.length;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(ins.length);
            resultInfo.setData("审核"+ins.length+"条员工信息！");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String delGeerEmpInfo(String stremps) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{
            int[] ins = null;
            stremps = stremps.substring(0,stremps.length()-1);
            final String[] listemp = stremps.split(",");
            String strsql = "delete from pos_cloud.sta_employee where emp_code =?";
            ins = jdbcTemplate.batchUpdate(strsql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,listemp[i]);
                }

                @Override
                public int getBatchSize() {
                    return listemp.length;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(ins.length);
            resultInfo.setData("删除"+ins.length+"条员工信息！");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getGeerStoretab() {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<GStoreTab> listgs = new ArrayList<GStoreTab>();
        String strsql = "SELECT a.*,b.store_name as store_pname,c.ct_desc  FROM pos_cloud.sta_store a\n" +
                "left join pos_cloud.sta_store b on a.store_pcode = b.store_code\n" +
                "left join pos_cloud.sta_custype c on a.store_type = c.ct_code;";
        try{
            listgs = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GStoreTab>() {
                @Override
                public GStoreTab mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GStoreTab gs = new GStoreTab();
                    gs.setStoreCode(rs.getString("store_code"));
                    gs.setStoreName(rs.getString("store_name"));
                    gs.setStorePcode(rs.getString("store_pcode"));
                    gs.setStorePname(rs.getString("store_pname"));
                    gs.setStoreArea(rs.getString("store_area"));
                    gs.setStoreAddr(rs.getString("store_addr"));
                    gs.setStoreTel(rs.getString("store_tel"));
                    gs.setStoreType(rs.getString("store_type"));
                    gs.setStoreTypeDesc(rs.getString("ct_desc"));
                    gs.setGmtCreat(rs.getString("gmt_creat"));
                    gs.setGmtModify(rs.getString("gmt_modify"));
                    return gs;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(listgs.size());
            resultInfo.setData(JSONArray.fromObject(listgs).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getGeerUpStoreList() {
        List<StaStoreInfo> lists = new ArrayList<StaStoreInfo>();
        String strSql = "SELECT store_code,store_name FROM pos_cloud.sta_store where store_type != '02';";
        lists = jdbcTemplate.query(strSql, new ParameterizedRowMapper<StaStoreInfo>() {
            @Override
            public StaStoreInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                StaStoreInfo gs = new StaStoreInfo();
                gs.setStoreCode(rs.getString("store_code"));
                gs.setStoreName(rs.getString("store_name"));
                return gs;
            }
        });
        return JSONArray.fromObject(lists).toString();
    }

    public String getGeetStypeList() {
        List<GCusTypeInfo> listct = new ArrayList<GCusTypeInfo>();
        String strsql = "SELECT * FROM pos_cloud.sta_custype;";
        listct = jdbcTemplate.query(strsql, new ParameterizedRowMapper<GCusTypeInfo>() {
            @Override
            public GCusTypeInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                GCusTypeInfo ct = new GCusTypeInfo();
                ct.setCtCode(rs.getString("ct_code"));
                ct.setCtDesc(rs.getString("ct_desc"));
                return ct;
            }
        });
        return JSONArray.fromObject(listct).toString();
    }

    public String doGeerUpdateStore(String strmgsinfo) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        GStoreTab gt = new GStoreTab();
        try{
            gt = (GStoreTab) JSONObject.toBean(JSONObject.fromObject(strmgsinfo),GStoreTab.class);
            String strsql = "update pos_cloud.sta_store set store_name=?,store_pcode=?,store_area=?,store_addr=?,\n" +
                    "store_tel=?,store_type=?,gmt_modify=? where store_code=?";
            final GStoreTab finalgt = gt;
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String strDate = f.format(date);
            jdbcTemplate.update(strsql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1,finalgt.getStoreName());
                    ps.setObject(2,finalgt.getStorePcode());
                    ps.setObject(3,finalgt.getStoreArea());
                    ps.setObject(4,finalgt.getStoreAddr());
                    ps.setObject(5,finalgt.getStoreTel());
                    ps.setObject(6,finalgt.getStoreType());
                    ps.setObject(7,strDate);
                    ps.setObject(8,finalgt.getStoreCode());
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

    public String doGeerUpStoreTab(String strmgsinfo) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try{
            final GStoreTab gt = (GStoreTab) JSONObject.toBean(JSONObject.fromObject(strmgsinfo),GStoreTab.class);
            String strsql = "insert into pos_cloud.sta_store (store_code,store_name,store_pcode,store_area,store_addr,store_tel,store_type,gmt_creat,gmt_modify)\n" +
                    "values(?,?,?,?,?,?,?,?,?)";
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String strDate = f.format(date);
            jdbcTemplate.update(strsql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setObject(1,gt.getStoreCode());
                    ps.setObject(2,gt.getStoreName());
                    ps.setObject(3,gt.getStorePcode());
                    ps.setObject(4,gt.getStoreArea());
                    ps.setObject(5,gt.getStoreAddr());
                    ps.setObject(6,gt.getStoreTel());
                    ps.setObject(7,gt.getStoreType());
                    ps.setObject(8,strDate);
                    ps.setObject(9,strDate);
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData("上传一条信息成功");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }
}
