package com.example.mdcutilsdemo.service.impl;

import com.example.mdcutilsdemo.service.Test;
import org.springframework.stereotype.Service;

@Service
public class TestImpl implements Test {
    @Override
    public String test() {
        return "test";
    }
}
