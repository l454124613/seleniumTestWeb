package com.ciic.test.tools;

import com.ciic.test.bean.tmp;
import com.ciic.test.dao.ConfigDao;
import com.ciic.test.dao.ConnectDatasource;
import com.ciic.test.service.ConfigService;
import com.ciic.test.service.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http 代理程序
 * @author lxc
 *
 */


public class SocketProxy   {

    static final int listenPort=8102;
    private static  List list=new ArrayList();
@Autowired
    private JdbcTemplate jdbcTemplate;

private boolean isf=true;





    public SocketProxy() throws IOException {
      //  this.run();
    }

    public synchronized static int getListnum() {
        return list.size();
    }
    public synchronized static List getList() {
        return list;
    }

    public static void main(String[] args) throws Exception {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        ServerSocket serverSocket = new ServerSocket(listenPort);
//        final ExecutorService tpe= Executors.newCachedThreadPool();
//        System.out.println("Proxy Server Start At "+sdf.format(new Date()));
//        System.out.println("listening port:"+listenPort+"……");
//        System.out.println();
//        System.out.println();
//      //  List<tmp> lt=jdbcTemplate.query("select url value from excepturl where tid=11 and isused=1",new BeanPropertyRowMapper<>(tmp.class));
//
//
//        while (true) {
//            Socket socket = null;
//            try {
//                socket = serverSocket.accept();
//                socket.setKeepAlive(true);
//                //加入任务列表，等待处理
//            //    aa++;
//               // list.add("1");
//               tpe.execute(new ProxyTask(socket,list,lt));
//             //   System.out.println("debug:"+list.size());
//             //   new Thread(new ProxyTask(socket)).start();
//            //    System.out.println(aa);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void  run()  {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(listenPort);
        } catch (IOException e) {
           // e.printStackTrace();
        }

        final ExecutorService tpe= Executors.newCachedThreadPool();
        System.out.println("Proxy Server Start At "+sdf.format(new Date()));
        System.out.println("listening port:"+listenPort+"……");
        System.out.println();
        System.out.println();

List<tmp> lt=jdbcTemplate.query("select url value from excepturl where tid=11 and isused=1",new BeanPropertyRowMapper<>(tmp.class));

isf=false;

        while (!isf) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
                //加入任务列表，等待处理
                //    aa++;
                // list.add("1");
                tpe.execute(new ProxyTask(socket,list,lt));
                //   System.out.println("debug:"+list.size());
                //   new Thread(new ProxyTask(socket)).start();
                //    System.out.println(aa);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        isf=true;
        System.out.println("close proxy......");

    }

}
