package com.cpos.dao;

import com.cpos.classes.ResultInfo;
import com.cpos.classes.analysis.*;
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
import java.text.NumberFormat;
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
                    satemp.setReadTime(sttemp.getGmtCreat());
                    satemp.setGmtCreat(liststd.get(i).getGmtCreat());
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

    public String getAstry() {
        List<AnalysisTry> listt = new ArrayList<AnalysisTry>();
        try{
            String strsql = "SELECT g_style,count(g_style) as g_times,TRUNCATE(avg(diff_time),2) as g_avg " +
                    "FROM pos_cloud.stry_analysis group by g_style order by g_times DESC limit 15;";
            listt = jdbcTemplate.query(strsql, new ParameterizedRowMapper<AnalysisTry>() {
                @Override
                public AnalysisTry mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AnalysisTry at = new AnalysisTry();
                    at.setgStyle(rs.getString("g_style"));
                    at.setgTimes(rs.getString("g_times"));
                    at.setgAvg(rs.getString("g_avg"));
                    return at;
                }
            });
        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listt).toString();
    }

    public String getAssalesStore() {
        List<AnalysisSales> listS = new ArrayList<AnalysisSales>();
        try{
            String strsql = "SELECT sum(a.s_totalprice) as tprice,sum(a.s_totalnum) as tnum,b.store_name FROM pos_cloud.store_salesbill a \n" +
                    "left join pos_cloud.sta_store b on a.s_storecode = b.store_code group by s_storecode order by tnum DESC ;";
            listS = jdbcTemplate.query(strsql, new ParameterizedRowMapper<AnalysisSales>() {
                @Override
                public AnalysisSales mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AnalysisSales as = new AnalysisSales();
                    as.setgStyle(rs.getString("store_name"));
                    as.setgSalesNum(rs.getString("tnum"));
                    as.setgSalesPrice(rs.getString("tprice"));
                    return  as;
                }
            });

        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listS).toString();
    }

    public String getAstrySales() {
        List<AnalysisTrySales> lists = new ArrayList<AnalysisTrySales>();
        try{
            String strsql = "select a.g_style,a.g_times,a.g_avg,ifnull(b.g_salesnum,0) as g_salesnum,ifnull(b.g_salesprice,0) as g_salesprice from (SELECT g_style,count(g_style) as g_times,TRUNCATE(avg(diff_time),2) as g_avg \n" +
                    "FROM pos_cloud.stry_analysis group by g_style limit 15) a \n" +
                    "left join (SELECT g_style,count(g_style) as g_salesnum,sum(s_curprice) as g_salesprice FROM pos_cloud.store_salesdtl group by g_style) b \n" +
                    "on a.g_style=b.g_style order by g_salesnum DESC ";
            lists = jdbcTemplate.query(strsql, new ParameterizedRowMapper<AnalysisTrySales>() {
                @Override
                public AnalysisTrySales mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AnalysisTrySales as = new AnalysisTrySales();
                    as.setgStyle(rs.getString("g_style"));
                    as.setgTimes(rs.getString("g_times"));
                    as.setgAvg(rs.getString("g_avg"));
                    as.setgSalesNum(rs.getString("g_salesnum"));
                    as.setgSalesPrice(rs.getString("g_salesprice"));
                    return  as;
                }
            });
        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(lists).toString();
    }

    public String getAssalesTotal(String strstype) {
        ResultInfo<String> resultInfo = new ResultInfo<String>();
        List<AssalesTotal> smp = new ArrayList<AssalesTotal>();
        try{
            String sqlselect="";
            if(strstype.equals("moth")){
                sqlselect = " SELECT count(s_billno) as billnums,sum(s_totalprice) as totalprice,sum(s_totalnum) as totalnum\n" +
                        " FROM pos_cloud.store_salesbill where date_format(gmt_creat,'%Y') =date_format(now(),'%Y') ;" ;
            }
            smp = jdbcTemplate.query(sqlselect, new ParameterizedRowMapper<AssalesTotal>() {
                @Override
                public AssalesTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AssalesTotal sm = new AssalesTotal();
                    sm.setAsTotalPrice(rs.getString("totalprice"));
                    sm.setAsTotalBill(rs.getString("billnums"));
                    sm.setAsTotalNum(rs.getString("totalnum"));
                    return sm;
                }
            });
            smp.get(0).setAsProcess("25");
            resultInfo.setCode(0);
            resultInfo.setCount(1);
            resultInfo.setData(JSONArray.fromObject(smp).toString());
        }catch (Exception es){
            resultInfo.setCode(1);
            resultInfo.setCount(1);
            resultInfo.setData(es.getMessage().toString());
        }
        return JSONObject.fromObject(resultInfo).toString();
    }

    public String getAssales() {
        List<AssalesTotal> listS = new ArrayList<AssalesTotal>();
        List<AssalesTotal> smp = new ArrayList<AssalesTotal>();
        try{
            String strsql = "SELECT date_format(gmt_creat,'%Y-%m') as gmt_moth,count(s_billno) as billnums,sum(s_totalprice) as totalprice,sum(s_totalnum) as totalnum\n" +
                    " FROM pos_cloud.store_salesbill where date_format(gmt_creat,'%Y') =date_format(now(),'%Y') group by date_format(gmt_creat, '%Y-%m');";
            listS = jdbcTemplate.query(strsql, new ParameterizedRowMapper<AssalesTotal>() {
                @Override
                public AssalesTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AssalesTotal sm = new AssalesTotal();
                    sm.setAsMoth(rs.getString("gmt_moth"));
                    sm.setAsTotalPrice(rs.getString("totalprice"));
                    sm.setAsTotalBill(rs.getString("billnums"));
                    sm.setAsTotalNum(rs.getString("totalnum"));
                    return sm;
                }
            });
            String sqlselect = " SELECT count(s_billno) as billnums,sum(s_totalprice) as totalprice,sum(s_totalnum) as totalnum\n" +
                        " FROM pos_cloud.store_salesbill where date_format(gmt_creat,'%Y') =date_format(now(),'%Y') ;" ;

            smp = jdbcTemplate.query(sqlselect, new ParameterizedRowMapper<AssalesTotal>() {
                @Override
                public AssalesTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AssalesTotal sm = new AssalesTotal();
                    sm.setAsTotalPrice(rs.getString("totalprice"));
                    sm.setAsTotalBill(rs.getString("billnums"));
                    sm.setAsTotalNum(rs.getString("totalnum"));
                    return sm;
                }
            });
            NumberFormat numberFormat = NumberFormat.getInstance();

            // 设置精确到小数点后2
            numberFormat.setMaximumFractionDigits(2);
            for(int i =0;i<listS.size();i++){
                String nump = numberFormat.format( Float.parseFloat(listS.get(i).getAsTotalNum()) / Float.parseFloat(smp.get(0).getAsTotalNum()) * 100)+"%";
                String pricep = numberFormat.format( Float.parseFloat(listS.get(i).getAsTotalPrice()) / Float.parseFloat(smp.get(0).getAsTotalPrice()) * 100)+"%";
                listS.get(i).setAsNumpercent(nump);
                listS.get(i).setAsPricepercent(pricep);
            }

        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listS).toString();
    }

    public String getAsstoreDtl() {
        List<AsstoreTotal> listas = new ArrayList<AsstoreTotal>();
        List<AssalesTotal> smp = new ArrayList<AssalesTotal>();
        try{
            String sqlselect = " SELECT count(s_billno) as billnums,sum(s_totalprice) as totalprice,sum(s_totalnum) as totalnum\n" +
                    " FROM pos_cloud.store_salesbill where date_format(gmt_creat,'%Y') =date_format(now(),'%Y') ;" ;

            smp = jdbcTemplate.query(sqlselect, new ParameterizedRowMapper<AssalesTotal>() {
                @Override
                public AssalesTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AssalesTotal sm = new AssalesTotal();
                    sm.setAsTotalPrice(rs.getString("totalprice"));
                    sm.setAsTotalBill(rs.getString("billnums"));
                    sm.setAsTotalNum(rs.getString("totalnum"));
                    return sm;
                }
            });
            String strsql = "SELECT a.s_storecode, count(s_billno) as billnums,sum(a.s_totalprice) as tprice,sum(a.s_totalnum) as tnum,b.store_name FROM pos_cloud.store_salesbill a \n" +
                    "left join pos_cloud.sta_store b on a.s_storecode = b.store_code group by s_storecode order by tnum DESC ;";
            listas = jdbcTemplate.query(strsql, new ParameterizedRowMapper<AsstoreTotal>() {
                @Override
                public AsstoreTotal mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AsstoreTotal as = new AsstoreTotal();
                    as.setAsStore(rs.getString("store_name"));
                    as.setAsTotalPrice(rs.getString("tprice"));
                    as.setAsTotalBill(rs.getString("billnums"));
                    as.setAsTotalNum(rs.getString("tnum"));
                    return as;
                }
            });
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2
            numberFormat.setMaximumFractionDigits(2);
            for(int i =0;i<listas.size();i++){
                String nump = numberFormat.format( Float.parseFloat(listas.get(i).getAsTotalNum()) / Float.parseFloat(smp.get(0).getAsTotalNum()) * 100)+"%";
                String pricep = numberFormat.format( Float.parseFloat(listas.get(i).getAsTotalPrice()) / Float.parseFloat(smp.get(0).getAsTotalPrice()) * 100)+"%";
                listas.get(i).setAsNumpercent(nump);
                listas.get(i).setAsPricepercent(pricep);
            }
        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listas).toString();
    }

    public String getAsstyleTop(String listtype) {
        List<AsstyleTop> listtop = new ArrayList<AsstyleTop>();
        try{
            String strsql = "SELECT g_style,count(g_unique) as nums FROM pos_cloud.store_salesdtl group by g_style order by nums  "+listtype+" limit 10;";
            listtop = jdbcTemplate.query(strsql, new ParameterizedRowMapper<AsstyleTop>() {
                @Override
                public AsstyleTop mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AsstyleTop at = new AsstyleTop();
                    at.setAsStyle(rs.getString("g_style"));
                    at.setAsNums(rs.getString("nums"));
                    return at;
                }
            });

        }catch (Exception es){
            return  es.getMessage().toString();
        }
        return JSONArray.fromObject(listtop).toString();
    }

    public String getAttimes() {
        List<Attimes> listat = new ArrayList<Attimes>();
        try{
            String sql = "select hour(gmt_creat) as hour, count(g_unique) as count  \n" +
                    "from pos_cloud.stry_analysis  \n" +
                    "group by hour(gmt_creat);";
            listat = jdbcTemplate.query(sql, new ParameterizedRowMapper<Attimes>() {
                @Override
                public Attimes mapRow(ResultSet rs, int rowNum) throws SQLException {
                   Attimes at = new Attimes();
                   at.setAtHour(rs.getString("hour"));
                   at.setAttimes(rs.getString("count"));
                    return at;
                }
            });

        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listat).toString();
    }

    public String getAssalesStyle(String strssdays, String strstore) {
        List<AnalysisSales> listasstyle = new ArrayList<AnalysisSales>();
        String sqls ="";
        try{
            if(strstore.equals("null") || strstore.equals("0")){
                strstore = "";
            }
            if(strssdays.equals("all")){
                sqls = "SELECT g_style,count(g_unique) as nums FROM pos_cloud.store_salesdtl a \n" +
                        "left join pos_cloud.store_salesbill b on a.s_billno = b.s_billno\n" +
                        "where b.s_storecode like '%"+strstore+"%' \n" +
                        "group by a.g_style order by nums desc;";
            }else if(strssdays.equals("week")){
                sqls = "SELECT g_style,count(g_unique) as nums FROM pos_cloud.store_salesdtl a \n" +
                        "left join pos_cloud.store_salesbill b on a.s_billno = b.s_billno\n" +
                        "where b.s_storecode like '%"+strstore+"%' and  DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(b.gmt_creat)\n" +
                        "group by a.g_style order by nums desc;";
            }else if(strssdays.equals("moth")){
                sqls = "SELECT g_style,count(g_unique) as nums FROM pos_cloud.store_salesdtl a \n" +
                        "left join pos_cloud.store_salesbill b on a.s_billno = b.s_billno\n" +
                        "where b.s_storecode like '%"+strstore+"%' and  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(b.gmt_creat)\n" +
                        "group by a.g_style order by nums desc;";
            }
            listasstyle = jdbcTemplate.query(sqls, new ParameterizedRowMapper<AnalysisSales>() {
                @Override
                public AnalysisSales mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AnalysisSales as = new AnalysisSales();
                    as.setgStyle(rs.getString("g_style"));
                    as.setgSalesNum(rs.getString("nums"));
                    return as;
                }
            });
        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listasstyle).toString();
    }

    public String getAsstoreTop(String strsdays) {
        List<AsstyleStore> listss = new ArrayList<AsstyleStore>();
        String sdays = "";
        String strsqltopstyle = "";
        String strsqltopstore = "";
        String strsqllaststore = "";
        try{
            if(strsdays.equals("all")){
                sdays = "360";
            }else if(strsdays.equals("week")){
                sdays = "7";
            }else if(strsdays.equals("moth")){
               sdays = "30";
            }
            strsqltopstyle = "SELECT g_style,count(g_unique) as nums FROM pos_cloud.store_salesdtl a \n" +
                    "left join pos_cloud.store_salesbill b on a.s_billno = b.s_billno\n" +
                    "where DATE_SUB(CURDATE(), INTERVAL "+sdays+" DAY) <= date(b.gmt_creat)\n" +
                    "group by a.g_style order by nums desc limit 5;";
            listss = jdbcTemplate.query(strsqltopstyle, new ParameterizedRowMapper<AsstyleStore>() {
                @Override
                public AsstyleStore mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AsstyleStore ass = new AsstyleStore();
                    ass.setStyle(rs.getString("g_style"));
                    ass.setTotalsales(rs.getString("nums"));
                    return ass;
                }
            });
            for(int i = 0 ;i <listss.size();i++) {
                List<AsstyleStoreNum> listssn = new ArrayList<AsstyleStoreNum>();
                List<AsstyleStoreNum> listssl = new ArrayList<AsstyleStoreNum>();
                strsqltopstore = "select a.s_storecode ,c.store_name, count(b.g_unique) as storesales from pos_cloud.store_salesbill a \n" +
                        "left join pos_cloud.store_salesdtl b on a.s_billno = b.s_billno\n" +
                        "left join pos_cloud.sta_store c on a.s_storecode = c.store_code\n" +
                        "where b.g_style = '"+listss.get(i).getStyle()+"' and DATE_SUB(CURDATE(), INTERVAL "+sdays+" DAY) <= date(a.gmt_creat)\n" +
                        "group by a.s_storecode desc limit 1";
                listssn =  jdbcTemplate.query(strsqltopstore, new ParameterizedRowMapper<AsstyleStoreNum>() {
                    @Override
                    public AsstyleStoreNum mapRow(ResultSet rs, int rowNum) throws SQLException {
                        AsstyleStoreNum as = new AsstyleStoreNum();
                        as.setStoreCode(rs.getString("s_storecode"));
                        as.setStoreName(rs.getString("store_name"));
                        as.setStoreNum(rs.getString("storesales"));
                        return as;
                    }
                });

                strsqllaststore = "select a.store_code,c.store_name ,ifnull(b.storesales,0) as storesales from\n" +
                        "(select store_code from pos_cloud.store_goods where g_style = '"+listss.get(i).getStyle()+"' group by store_code) a\n" +
                        "left join (select a.s_storecode , count(b.g_unique) as storesales from pos_cloud.store_salesbill a \n" +
                        "left join pos_cloud.store_salesdtl b on a.s_billno = b.s_billno\n" +
                        "where b.g_style = '"+listss.get(i).getStyle()+"' and DATE_SUB(CURDATE(), INTERVAL "+sdays+" DAY) <= date(a.gmt_creat)\n" +
                        "group by a.s_storecode ) b on a.store_code = b.s_storecode\n" +
                        "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                        "order by storesales asc limit 1";
                listssl = jdbcTemplate.query(strsqllaststore, new ParameterizedRowMapper<AsstyleStoreNum>() {
                    @Override
                    public AsstyleStoreNum mapRow(ResultSet rs, int rowNum) throws SQLException {
                        AsstyleStoreNum as = new AsstyleStoreNum();
                        as.setStoreCode(rs.getString("store_code"));
                        as.setStoreName(rs.getString("store_name"));
                        as.setStoreNum(rs.getString("storesales"));
                        return as;
                    }
                });
                listss.get(i).setTopStoreCode(listssn.get(0).getStoreCode());
                listss.get(i).setTopStoreName(listssn.get(0).getStoreName());
                listss.get(i).setTopStoreSales(listssn.get(0).getStoreNum());
                listss.get(i).setLastStoreCode(listssl.get(0).getStoreCode());
                listss.get(i).setLastStoerName(listssl.get(0).getStoreName());
                listss.get(i).setLastStoreSales(listssl.get(0).getStoreNum());
            }

            for(int j =0;j<listss.size();j++){
               String stopnum = "select ifnull(count(g_unique),0) from pos_cloud.store_goods where isstate='1' and g_style = '"+listss.get(j).getStyle()+"' and store_code ='"+listss.get(j).getTopStoreCode()+"'";
               String slastnum = "select ifnull(count(g_unique),0) from pos_cloud.store_goods where isstate='1' and g_style = '"+listss.get(j).getStyle()+"' and store_code ='"+listss.get(j).getLastStoreCode()+"'";
               String topnum = jdbcTemplate.queryForObject(stopnum,String.class);
               String lastnum = jdbcTemplate.queryForObject(slastnum,String.class);
               listss.get(j).setTopStoreNum(topnum);
               listss.get(j).setLastStoreNum(lastnum);
            }

        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listss).toString();
    }

    public String getAsstoreLast(String strsdays) {
        List<AsstyleStore> listss = new ArrayList<AsstyleStore>();
        String sdays = "";
        String strsqltopstyle = "";
        String strsqltopstore = "";
        String strsqllaststore = "";
        try{
            if(strsdays.equals("all")){
                sdays = "360";
            }else if(strsdays.equals("week")){
                sdays = "7";
            }else if(strsdays.equals("moth")){
                sdays = "30";
            }
            strsqltopstyle = "SELECT g_style,count(g_unique) as nums FROM pos_cloud.store_salesdtl a \n" +
                    "left join pos_cloud.store_salesbill b on a.s_billno = b.s_billno\n" +
                    "where DATE_SUB(CURDATE(), INTERVAL "+sdays+" DAY) <= date(b.gmt_creat)\n" +
                    "group by a.g_style order by nums ASC limit 5;";
            listss = jdbcTemplate.query(strsqltopstyle, new ParameterizedRowMapper<AsstyleStore>() {
                @Override
                public AsstyleStore mapRow(ResultSet rs, int rowNum) throws SQLException {
                    AsstyleStore ass = new AsstyleStore();
                    ass.setStyle(rs.getString("g_style"));
                    ass.setTotalsales(rs.getString("nums"));
                    return ass;
                }
            });
            for(int i = 0 ;i <listss.size();i++) {
                List<AsstyleStoreNum> listssn = new ArrayList<AsstyleStoreNum>();
                List<AsstyleStoreNum> listssl = new ArrayList<AsstyleStoreNum>();
                strsqltopstore = "select a.s_storecode ,c.store_name, count(b.g_unique) as storesales from pos_cloud.store_salesbill a \n" +
                        "left join pos_cloud.store_salesdtl b on a.s_billno = b.s_billno\n" +
                        "left join pos_cloud.sta_store c on a.s_storecode = c.store_code\n" +
                        "where b.g_style = '"+listss.get(i).getStyle()+"' and DATE_SUB(CURDATE(), INTERVAL "+sdays+" DAY) <= date(a.gmt_creat)\n" +
                        "group by a.s_storecode desc limit 1";
                listssn =  jdbcTemplate.query(strsqltopstore, new ParameterizedRowMapper<AsstyleStoreNum>() {
                    @Override
                    public AsstyleStoreNum mapRow(ResultSet rs, int rowNum) throws SQLException {
                        AsstyleStoreNum as = new AsstyleStoreNum();
                        as.setStoreCode(rs.getString("s_storecode"));
                        as.setStoreName(rs.getString("store_name"));
                        as.setStoreNum(rs.getString("storesales"));
                        return as;
                    }
                });

                strsqllaststore = "select a.store_code,c.store_name ,ifnull(b.storesales,0) as storesales from\n" +
                        "(select store_code from pos_cloud.store_goods where g_style = '"+listss.get(i).getStyle()+"' group by store_code) a\n" +
                        "left join (select a.s_storecode , count(b.g_unique) as storesales from pos_cloud.store_salesbill a \n" +
                        "left join pos_cloud.store_salesdtl b on a.s_billno = b.s_billno\n" +
                        "where b.g_style = '"+listss.get(i).getStyle()+"' and DATE_SUB(CURDATE(), INTERVAL "+sdays+" DAY) <= date(a.gmt_creat)\n" +
                        "group by a.s_storecode ) b on a.store_code = b.s_storecode\n" +
                        "left join pos_cloud.sta_store c on a.store_code = c.store_code\n" +
                        "order by storesales asc limit 1";
                listssl = jdbcTemplate.query(strsqllaststore, new ParameterizedRowMapper<AsstyleStoreNum>() {
                    @Override
                    public AsstyleStoreNum mapRow(ResultSet rs, int rowNum) throws SQLException {
                        AsstyleStoreNum as = new AsstyleStoreNum();
                        as.setStoreCode(rs.getString("store_code"));
                        as.setStoreName(rs.getString("store_name"));
                        as.setStoreNum(rs.getString("storesales"));
                        return as;
                    }
                });
                listss.get(i).setTopStoreCode(listssn.get(0).getStoreCode());
                listss.get(i).setTopStoreName(listssn.get(0).getStoreName());
                listss.get(i).setTopStoreSales(listssn.get(0).getStoreNum());
                listss.get(i).setLastStoreCode(listssl.get(0).getStoreCode());
                listss.get(i).setLastStoerName(listssl.get(0).getStoreName());
                listss.get(i).setLastStoreSales(listssl.get(0).getStoreNum());
            }

            for(int j =0;j<listss.size();j++){
                String stopnum = "select ifnull(count(g_unique),0) from pos_cloud.store_goods where isstate='1' and g_style = '"+listss.get(j).getStyle()+"' and store_code ='"+listss.get(j).getTopStoreCode()+"'";
                String slastnum = "select ifnull(count(g_unique),0) from pos_cloud.store_goods where isstate='1' and g_style = '"+listss.get(j).getStyle()+"' and store_code ='"+listss.get(j).getLastStoreCode()+"'";
                String topnum = jdbcTemplate.queryForObject(stopnum,String.class);
                String lastnum = jdbcTemplate.queryForObject(slastnum,String.class);
                listss.get(j).setTopStoreNum(topnum);
                listss.get(j).setLastStoreNum(lastnum);
            }

        }catch (Exception es){
            return es.getMessage().toString();
        }
        return JSONArray.fromObject(listss).toString();
    }
}
