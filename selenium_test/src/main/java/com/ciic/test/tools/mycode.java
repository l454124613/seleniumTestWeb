package com.ciic.test.tools;

import com.ciic.test.dao.SeleniumDao;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by lixuecheng on 2017/7/10.
 */
public class mycode {
    public  static String encode(String so){
        String re="";
        char a=so.charAt(0);
        int a2=a+so.length();
        if(a2%2==1){
            re+='蠷';
            re+=(char)((a2+1)/2);
        }else {
            re+='硿';
            re+=(char)((a2)/2);
        }



        for (int i = 1; i <so.length() ; i++) {
            int aa=so.charAt(i)+so.charAt(i-1);
            if(aa%2==1){
                re+='蠷';
                re+=(char)((aa+1)/2);
            }else {
                re+='硿';
                re+=(char)((aa)/2);
            }
        }


        return re;

    }

    /**
     * 判断参数是否为空，无内容
     * @param args
     * @return
     */
    public static boolean hasEmpty(String ...args){
        for (String a:args){
            if(a.isEmpty()){
                return true;
            }
        }
        return false;

    }

    /**
     * 创建数组
     * @param args
     * @return
     */
    public static  Object[] getArrayObj(Object...args){
        return args;
    }

    public  static String decode(String so){
        if(so.startsWith("%")){
            try {
               so= URLDecoder.decode(so,"UTF-8");
            } catch (UnsupportedEncodingException e) {

            }
        }

        String re="";
        int n1=0;
        if(so.charAt(0)=='蠷'){
            n1=so.charAt(1)*2-1-so.length()/2;
        }else {
            n1=so.charAt(1)*2-so.length()/2;
        }
        re+=(char)n1;

        for (int i = 3,j=2; i <so.length() ; i+=2,j+=2) {
            if(so.charAt(j)=='蠷'){
                n1=so.charAt(i)*2-1-n1;
            }else {
                n1=so.charAt(i)*2-n1;
            }
            re+=(char)n1;

        }

        return re;

    }

    public  static String getTitle(String url){
        try {
            WebDriver driver=new HtmlUnitDriver();
            driver.get(url);
            String aa=driver.getTitle();
            driver.quit();
            return aa;
        } catch (Exception e) {
return "err";
        }
    }

    public static Object[] prase(Object[] a){
        for (int i = 0; i <a.length ; i++) {
           a[i]= a[i].toString().replace("&","&amp;").replace("<","&lt;").replace(" ","&nbsp;").replace(">","&gt;")
                    .replace("\"","&quot;").replace("'","&apos;");
        }
        return a;

    }

    public static Object[] prase2(Object[] a){
        for (int i = 0; i <a.length ; i++) {
            a[i]= a[i].toString().replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                    .replace("\"","&quot;").replace("'","&apos;").replace("\n","<br/>");
        }
        return a;

    }
    public static Object[] prase3(Object[] a){
        for (int i = 0; i <a.length ; i++) {
            a[i]= a[i].toString().replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                    .replace("\"","%25").replace("'","%26").replace("\n","<br/>");
        }
        return a;

    }

    public static String  praseString2(String a){

         return   a.replace("\n","").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\").replace("<","&lt;").replace(">","&gt;");



    }



    public static void main(String[] args) throws IOException {
//       String aa="";
//       aa=null;
//       if(aa==null){
//           System.out.println(1);
//       }
     //   System.out.println(System.currentTimeMillis()/1000);
//        SeleniumDao d=new SeleniumDao();
//        d.test("20");
        Map<String,Set<String>> map=new HashMap<>();
        Map<String,Set<String>> map2=new HashMap<>();
        Set<String> set=new HashSet<>();
        Set<String> set2=new HashSet<>();
        set.add("5");
        set.add("6");
        set.add("7");
        set.add("8");
        set2.add("1");
        set2.add("4");
        map.put("1",set);
        map.put("2",set2);

        map.forEach((k,v)->{
           // map2.put(k,v);
            String[] y = v.toArray(new String[0]);
            for (int i = 0; i < y.length; i++) {
                if(map.containsKey(y[i])){
                    map.get(y[i]).forEach(k3->{
                        map.get(k).add(k3);
                    });
                 //   map2.get(k).addAll(map.get(k2));
                    //  System.out.println(map2);
                }
            }
            System.out.println(v);





        });


    }


    public static void write(String path, String content, String encoding)
            throws IOException {

        File file = new File(path);
        file.delete();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), encoding));
        writer.write(content);
        writer.close();
    }

}
