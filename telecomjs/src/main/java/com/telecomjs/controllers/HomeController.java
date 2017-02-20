package com.telecomjs.controllers;

import com.alibaba.fastjson.JSON;
import com.telecomjs.entities.Cust;
import com.telecomjs.mappers.CustMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by zark on 16/12/10.
 */
@Controller
public class HomeController {
    Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    CustMapper custMapper;

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        logger.info("HomeController#index be invoked!");
        return "Hello World!";
    }

    @RequestMapping("/cust/{cust_id}")
    @ResponseBody
    public String cust(@PathVariable("cust_id")long custId) {
        logger.info("HomeController#add be invoked!");
        /*Cust cust = new Cust();
        cust.setCustId(11l);
        cust.setStatusDate(new Date());
        cust.setCustCert("123");
        cust.setCustName("张伟");
        cust.setCreateDate(new Date());*/
        Cust cust  = custMapper.selectCust(custId);
        logger.info("cust : "+ JSON.toJSONString(cust));
        return JSON.toJSONString(cust);
    }

    /*@RequestMapping("/error")
    @ResponseBody
    public String basicErrorController() {
        return "Error!";
    }*/
}
