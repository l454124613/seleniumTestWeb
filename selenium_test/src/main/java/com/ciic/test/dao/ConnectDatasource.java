package com.ciic.test.dao;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lixuecheng on 2017/8/1.
 */

public class ConnectDatasource {
    private String     link;
    private String     dataname;


    public ConnectDatasource(String link, String dataname, String user, String pass, String type) {
        this.link = link;
        this.dataname = dataname;
        this.user = user;
        this.pass = pass;
        this.type = type;
    }

    private String user;
    private String pass;
    private String type;

    private Connection con;
    private String connectRes;

    public String getConnectRes() {
        return connectRes;
    }





    public Connection Connection(){
        String driver="";
        String url="";
        if(type.equalsIgnoreCase("1")){
            driver="com.mysql.jdbc.Driver";
            url="jdbc:mysql://";
            dataname="/"+dataname;

        }else if(type.equalsIgnoreCase("2")){
            driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            url="jdbc:sqlserver://";
            dataname=";DatabaseName="+dataname;
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con2=null;
        try {
              con2= DriverManager.getConnection(url+link+dataname,user,pass);
            if(con2.isClosed()){
                connectRes="连接失败";
            }else {
                connectRes="连接成功";
                con=con2;

            }
        } catch (SQLException e) {
            connectRes=e.getErrorCode()+":"+e.getMessage();
        }
        return con2;

    }

    public void closeCon(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
