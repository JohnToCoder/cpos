package com.cpos.dao;

import com.cpos.classes.ResultInfo;
import com.cpos.classes.analysis.StoreTryAnalysis;
import com.cpos.classes.analysis.StoreTryInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalysisDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String doUpTryData(String jsonArray) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<StoreTryInfo> storeTryInfoList = new ArrayList<StoreTryInfo>();
        try{
            storeTryInfoList = JSONArray.toList(JSONArray.fromObject(jsonArray),StoreTryInfo.class);
            String sqlinsert = "insert into pos_cloud.stry_screen (store_code,rd_ant,g_unique,g_sku,g_name,g_size,g_style,g_color,read_time,gmt_creat)\n" +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Date insdates = new Date();
            SimpleDateFormat fins = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String strdate = fins.format(insdates);
            final List<StoreTryInfo> finalst = storeTryInfoList;
            int[] ints =jdbcTemplate.batchUpdate(sqlinsert, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, finalst.get(i).getStoreCode());
                    ps.setObject(2,finalst.get(i).getAnt());
                    ps.setObject(3,finalst.get(i).getEpc());
                    ps.setObject(4,finalst.get(i).getSku());
                    ps.setObject(5,finalst.get(i).getProName());
                    ps.setObject(6,finalst.get(i).getSize());
                    ps.setObject(7,finalst.get(i).getStyle());
                    ps.setObject(8,finalst.get(i).getColor());
                    ps.setObject(9,finalst.get(i).getReadTime());
                    ps.setObject(10,strdate);
                }

                @Override
                public int getBatchSize() {
                    return finalst.size();
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(finalst.size());
            resultInfo.setData("OK");
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String doAnalysisData() {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        try {
            List<StoreTryInfo> liststd = new ArrayList<StoreTryInfo>();
            final List<StoreTryAnalysis> listsa = new ArrayList<StoreTryAnalysis>();
            String strlastdt = "";
            String sqltrydata = "";
            String sqllastdt = "SELECT gmt_creat FROM pos_cloud.stry_analysis order by gmt_creat desc limit 1;";
            try {
                strlastdt = jdbcTemplate.queryForObject(sqllastdt, String.class);
            } catch (EmptyResultDataAccessException e) {
                strlastdt = null;
            }
            if (strlastdt == null) {
                sqltrydata = "SELECT * FROM pos_cloud.stry_screen;";
            }else{
                sqltrydata = "SELECT * FROM pos_cloud.stry_screen where gmt_creat > '"+strlastdt+"';";
            }
            liststd = jdbcTemplate.query(sqltrydata, new ParameterizedRowMapper<StoreTryInfo>() {
                @Override
                public StoreTryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    StoreTryInfo st = new StoreTryInfo();
                    st.setStoreCode(rs.getString("store_code"));
                    st.setAnt(rs.getString("rd_ant"));
                    st.setEpc(rs.getString("g_unique"));
                    st.setSku(rs.getString("g_sku"));
                    st.setSize(rs.getString("g_size"));
                    st.setStyle(rs.getString("g_style"));
                    st.setProName(rs.getString("g_name"));
                    st.setColor(rs.getString("g_color"));
                    st.setReadTime(rs.getString("read_time"));
                    st.setGmtCreat(rs.getString("gmt_creat"));
                    return st;
                }
            });

            int i = 0;
            int j = 0;

            while(i<liststd.size()){
                StoreTryInfo sttemp = new StoreTryInfo();
                StoreTryAnalysis satemp = new StoreTryAnalysis();
                sttemp = liststd.get(i);
                j=i+1;
                while (j<liststd.size()){
                    int diff = getTimeDelta(liststd.get(j).getGmtCreat(),liststd.get(i).getGmtCreat());
                    if(liststd.get(j).getSku().equals(liststd.get(i).getSku()) && diff<20){
                        i++;
                        j++;
                    }else{
                        break;
                    }
                }
                int diffsa = getTimeDelta(liststd.get(i).getGmtCreat(),sttemp.getGmtCreat());
                if(diffsa >0) {
                    satemp.setStoreCode(sttemp.getStoreCode());
                    satemp.setAnt(sttemp.getAnt());
                    satemp.setEpc(sttemp.getEpc());
                    satemp.setSku(sttemp.getSku());
                    satemp.setSize(sttemp.getSize());
                    satemp.setStyle(sttemp.getStyle());
                    satemp.setProName(sttemp.getProName());
                    satemp.setColor(sttemp.getColor());
                    satemp.setReadTime(sttemp.getReadTime());
                    satemp.setGmtCreat(sttemp.getGmtCreat());
                    satemp.setDifftime(String.valueOf(diffsa));
                    listsa.add(satemp);
                    i++;
                }else {
                    i++;
                }
            }
            String strins = "insert into pos_cloud.stry_analysis (store_code,rd_ant,g_unique,g_sku,g_name,g_size,g_style,g_color,diff_time,read_time,gmt_creat)\n" +
                    "values (?,?,?,?,?,?,?,?,?,?,?)";
            int[] ins = jdbcTemplate.batchUpdate(strins, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1,listsa.get(i).getStoreCode());
                    ps.setObject(2,listsa.get(i).getAnt());
                    ps.setObject(3,listsa.get(i).getEpc());
                    ps.setObject(4,listsa.get(i).getSku());
                    ps.setObject(5,listsa.get(i).getProName());
                    ps.setObject(6,listsa.get(i).getSize());
                    ps.setObject(7,listsa.get(i).getStyle());
                    ps.setObject(8,listsa.get(i).getColor());
                    ps.setObject(9,listsa.get(i).getDifftime());
                    ps.setObject(10,listsa.get(i).getReadTime());
                    ps.setObject(11,listsa.get(i).getGmtCreat());
                }

                @Override
                public int getBatchSize() {
                    return listsa.size();
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
    /***
     * yyyy-MM-dd HH:mm:ss
     */
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
    /***
     * 两个日期相差多少秒
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getTimeDelta(Date date1,Date date2){
        long timeDelta=(date1.getTime()-date2.getTime())/1000;//单位是秒
        int secondsDelta=timeDelta>0?(int)timeDelta:(int)Math.abs(timeDelta);
        return secondsDelta;
    }

    /***
     * 两个日期相差多少秒
     * @param dateStr1  :yyyy-MM-dd HH:mm:ss
     * @param dateStr2 :yyyy-MM-dd HH:mm:ss

     */
    public static int getTimeDelta(String dateStr1,String dateStr2){
        Date date1=parseDateByPattern(dateStr1, yyyyMMddHHmmss);
        Date date2=parseDateByPattern(dateStr2, yyyyMMddHHmmss);
        return getTimeDelta(date1, date2);
    }

    public static Date parseDateByPattern(String dateStr,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAnalysisData() {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<StoreTryAnalysis> lista = new ArrayList<StoreTryAnalysis>();
        String sql = "SELECT * FROM pos_cloud.stry_analysis order by gmt_creat desc ;";
        try{
            lista = jdbcTemplate.query(sql, new ParameterizedRowMapper<StoreTryAnalysis>() {
                @Override
                public StoreTryAnalysis mapRow(ResultSet rs, int rowNum) throws SQLException {
                    StoreTryAnalysis st = new StoreTryAnalysis();
                    st.setStoreCode(rs.getString("store_code"));
                    st.setAnt(rs.getString("rd_ant"));
                    st.setEpc(rs.getString("g_unique"));
                    st.setSku(rs.getString("g_sku"));
                    st.setSize(rs.getString("g_size"));
                    st.setStyle(rs.getString("g_style"));
                    st.setProName(rs.getString("g_name"));
                    st.setColor(rs.getString("g_color"));
                    st.setDifftime(rs.getString("diff_time"));
                    st.setReadTime(rs.getString("read_time"));
                    st.setGmtCreat(rs.getString("gmt_creat"));
                    return st;
                }
            });
            resultInfo.setCode(0);
            resultInfo.setCount(lista.size());
            resultInfo.setData(JSONArray.fromObject(lista).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
     }
}
