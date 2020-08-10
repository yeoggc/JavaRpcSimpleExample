package com.ggc.rpc_example;

import com.ggc.rpc_example.service.UserServiceImpl;
import lombok.SneakyThrows;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerSideEntrypoint {

    public static void main(String[] args) {
        new ServerSideEntrypoint().startServer();
    }

    @SneakyThrows
    public void startServer() {

        ServerSocket server = new ServerSocket(7711);

        Map<String, Object> beanPool = new HashMap<String, Object>();
        beanPool.put("com.ggc.rpc_example.service.UserService", new UserServiceImpl());

        while (true) {

            //接收客户端请求
            Socket client = server.accept();

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            //服务名称
            String serviceName = in.readUTF();
            //方法名
            String methodName = in.readUTF();
            //参数类型
            Class<?>[] paramType = (Class<?>[]) in.readObject();
            //参数
            Object[] params = (Object[]) in.readObject();

            //获取服务Service
            Object bean = beanPool.get(serviceName);

            Method method = bean.getClass().getMethod(methodName, paramType);
            Object result = method.invoke(bean, params);

            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(result);
        }
    }
}
