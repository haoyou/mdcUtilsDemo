package com.example.mdcutilsdemo.service.impl;

import com.example.mdcutilsdemo.service.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class TestImpl implements Test {
    @Override
    public String test() {
        int i = 10;
        String  str = "test2";
        return str;
    }
}
