package com.ggc.rpc_example;

import lombok.SneakyThrows;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RemoteProxyObjFactory {

    @SneakyThrows
    public static Object createRemoteProxyObj(final Class<?> service) {

        final Socket socket = new Socket("127.0.0.1", 7711);
        return Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeUTF(service.getName());
                        out.writeUTF(method.getName());
                        out.writeObject(method.getParameterTypes());
                        out.writeObject(args);
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        return in.readObject();
                    }
                });
    }

}
