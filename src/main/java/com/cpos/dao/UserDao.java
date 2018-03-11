package com.cpos.dao;

import com.cpos.classes.EmpInfo;
import com.cpos.classes.ResultInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResultInfo<String> loginByEmpID(String empCode, String pwd) {
        List<EmpInfo> listEmp = new ArrayList<EmpInfo>();
        String strsql = "SELECT a.emp_code,a.emp_name,c.duty_name,a.emp_mail,a.emp_tel,\n" +
                "b.store_code,b.store_name,b.store_area,b.store_addr,b.store_tel FROM pos_cloud.sta_employee a\n" +
                "left join pos_cloud.sta_store b on a.store_code = b.store_code\n" +
                "left join pos_cloud.sta_empduty c on a.emp_duty = c.duty_code\n" +
                "where a.emp_code ='"+empCode+"' and emp_psw='"+pwd+"';";
        try{
            listEmp = jdbcTemplate.query(strsql, new ParameterizedRowMapper<EmpInfo>() {
                @Override
                public EmpInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                    EmpInfo em = new EmpInfo();
                    em.setEmpCode(rs.getString("emp_code"));
                    em.setEmpName(rs.getString("emp_name"));
                    em.setEmpDuty(rs.getString("duty_name"));
                    em.setEmpMail(rs.getString("emp_mail"));
                    em.setEmpTel(rs.getString("emp_tel"));
                    em.setStoreCode(rs.getString("store_code"));
                    em.setStoreName(rs.getString("store_name"));
                    em.setStoreArea(rs.getString("store_area"));
                    em.setStoreAddr(rs.getString("store_addr"));
                    em.setStoreTel(rs.getString("store_tel"));
                    return em;
                }
            });
        }catch (Exception es){
            return  ResultInfo.creatError(1,1,es.getMessage().toString());
        }
        if(listEmp.size()>0){
            JSONObject jsEmp = JSONObject.fromObject(listEmp.get(0));
            return ResultInfo.creatResult(0,listEmp.size(), jsEmp.toString());
        }
        else {
            return ResultInfo.creatError(1,1, "user/password not match.");
        }
    }

    public void loginlog(final String strhost, final String strip, final int strport, final String empCode, final String strinfo) {
        String strins = "insert into pos_cloud.log_login (log_host,log_ip,log_port,log_empcode,log_info,gmt_creat) values(?,?,?,?,?,?)";
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String strDate = f.format(date);
        jdbcTemplate.update(strins, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setObject(1,strhost);
                ps.setObject(2,strip);
                ps.setObject(3,strport);
                ps.setObject(4,empCode);
                ps.setObject(5,strinfo);
                ps.setObject(6,strDate);
            }
        });
    }
}
