package com.ggc.rpc_example;

import com.ggc.rpc_example.bean.User;
import com.ggc.rpc_example.service.UserService;

public class ClientSideEntrypoint {

    public static void main(String[] args) {
        UserService userService = (UserService) RemoteProxyObjFactory.createRemoteProxyObj(UserService.class);
        String result = userService.saveUser(new User("ggc", "male"));
        System.out.println("result:" + result);
    }

}
