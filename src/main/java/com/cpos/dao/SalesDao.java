package com.cpos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class SalesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

}
