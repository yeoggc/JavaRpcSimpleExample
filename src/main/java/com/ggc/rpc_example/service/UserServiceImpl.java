package com.ggc.rpc_example.service;

import com.ggc.rpc_example.bean.User;

public class UserServiceImpl implements UserService {
    public String saveUser(User user) {
        System.out.println("新增用户: " + user);
        return "SUCCESS";
    }
}