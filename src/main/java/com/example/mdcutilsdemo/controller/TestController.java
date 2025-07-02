package com.example.mdcutilsdemo.controller;

import com.example.mdcutilsdemo.entity.User;
import com.example.mdcutilsdemo.mapper.UserMapper;
import com.example.mdcutilsdemo.service.Test;
import com.example.mdcutilsdemo.service.impl.RedisService;
import com.example.mdcutilsdemo.utils.MdcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private Test testService;

    @Autowired
    private RedisService redisService;

    // 添加Mapper注入
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test() {
        testService.test();
        return MdcUtils.getTraceId();
    }

    @GetMapping("/hello")
    public String hello() {
        log.debug("=========");
        return "Hello world!";
    }

    @GetMapping("/test/redis")
    public String testRedis(){
        String key = "test";
        redisService.set(key, "test");
        return (String) redisService.get(key);
    }

    @GetMapping("/test/id")
    public Map<String, String> testIdGenerator() {
        Map<String, String> ids = new HashMap<>();

        // 生成订单ID
        ids.put("order_id", redisService.generateId("1001"));

        // 生成用户ID
        ids.put("user_id", redisService.generateId("2001"));

        // 生成支付ID
        ids.put("payment_id", redisService.generateId("3001"));

        return ids;
    }
    @GetMapping("/test/batch/generator/id")
    public List<String> testBatchIdGenerator() {
        return redisService.batchGenerateIds("4001", 10000);
    }

    // 添加以下接口
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userMapper.findById(id);
    }

    @GetMapping("/user/search")
    public List<User> searchUsers(@RequestParam String name) {
        return userMapper.findByName(name);
    }

    @PostMapping("/user")
    public String addUser(@RequestBody User user) {
        userMapper.insert(user);
        return "添加成功，ID: " + user.getId();
    }

    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userMapper.update(user);
        return "更新成功";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userMapper.delete(id);
        return "删除成功";
    }

}
