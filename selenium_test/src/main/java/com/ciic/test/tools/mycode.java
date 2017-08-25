package com.ciic.test.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/10.
 */
public class mycode {
    public  static String encode(String so){
        char a=so.charAt(0);


        String re=(char)(a+so.length())+"";
        for (int i = 1; i <so.length() ; i++) {
            re=re+(char)(so.charAt(i)+so.charAt(i-1));
        }


        return re;

    }
    public  static String decode(String so){
        char a=so.charAt(0);
//        System.out.println(so.length());
//        System.out.println(a+1);
       String re=(char)(a-so.length())+"";
//        System.out.println(re);
        for (int i = 1; i <so.length() ; i++) {
            re=re+(char)(so.charAt(i)-re.charAt(i-1));
        }

        return re;

    }

    public  static String getTitle(String url){
        WebDriver driver=new HtmlUnitDriver();
        driver.get(url);
     String aa=driver.getTitle();
        driver.quit();
        return aa;
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


    public static void main(String[] args) {
//       String aa="";
//       aa=null;
//       if(aa==null){
//           System.out.println(1);
//       }
     //   System.out.println(System.currentTimeMillis()/1000);
        Map map=new HashMap();
        map.put(11,123);
        map.put(11,12311);
        System.out.println(map);

    }
}
