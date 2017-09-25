package com.ciic.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lixuecheng on 2017/9/25.
 */
class ActionSocket extends Thread{
    private Socket socket = null ;
    public ActionSocket(Socket s){
        this.socket = s ;
    }
    public void run(){
        try{
            this.action() ;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void action() throws Exception {
        if (this.socket == null){
            return ;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        for(String temp = br.readLine() ; temp!=null;temp = br.readLine() ){
            System.out.println(temp);
        }
        br.close();
    }
}
public class ServerPrxoy{
    public static void main(String args[])throws Exception{
        ServerSocket server  = new ServerSocket(8102);
        while(true){
            Socket socket = server.accept();
            ActionSocket ap = new ActionSocket(socket);
            ap.start();
        }
    }
}
