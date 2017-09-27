package com.ciic.test.tools;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http 代理程序
 * @author lxc
 *
 */
public class SocketProxy {

    static final int listenPort=8102;

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ServerSocket serverSocket = new ServerSocket(listenPort);
        final ExecutorService tpe= Executors.newCachedThreadPool();
        System.out.println("Proxy Server Start At "+sdf.format(new Date()));
        System.out.println("listening port:"+listenPort+"……");
        System.out.println();
        System.out.println();
        int aa=0;

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
                //加入任务列表，等待处理
                aa++;
               tpe.execute(new ProxyTask(socket));
             //   new Thread(new ProxyTask(socket)).start();
                System.out.println(aa);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
