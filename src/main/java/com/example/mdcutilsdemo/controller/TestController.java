package com.example.mdcutilsdemo.controller;

import com.example.mdcutilsdemo.service.Test;
import com.example.mdcutilsdemo.utils.MdcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private Test testService;
    @GetMapping("/test")
    public String test() {
        testService.test();
        return MdcUtils.getTraceId();
    }
}
