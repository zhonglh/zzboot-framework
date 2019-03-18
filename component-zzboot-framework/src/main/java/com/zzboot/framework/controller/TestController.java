package com.zzboot.framework.controller;


import com.zzboot.framework.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private TestMapper testMapper;


    @RequestMapping("/")
    @ResponseBody
    public String index(){
        testMapper.deleteById("bf0df9d7113d455fa84d6e1001aa1b31");
        return "aaaa";
    }


}
