package com.cpos.controller;

import com.cpos.dao.SalesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SalesController {
    @Autowired
    private SalesDao salesDao;
}
