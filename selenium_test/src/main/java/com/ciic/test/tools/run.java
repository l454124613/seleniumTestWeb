package com.ciic.test.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by lixuecheng on 2017/7/20.
 */
public class run {

        public String aa(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //  driver.manage().window().maximize();
        driver.get("http://10.66.1.28:8090/index.action");
        driver.manage().window().maximize();
        driver.findElement(By.id("os_username")).sendKeys("admin");

        driver.findElement(By.id("os_password")).sendKeys("AAAaaa1234");
        driver.findElement(By.id("loginButton")).click();

        driver.findElement(By.id("space-menu-link")).click();
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        driver.findElements(By.className("aui-icon-container")).get(1).click();
//
//            driver.findElement(By.id("quick-create-page-button")).click();
//
//
//            driver.findElement(By.id("content-title")).sendKeys("admin");
//            driver.findElement(By.id("rte-button-publish")).click();
//
//
//
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        driver.quit();
        System.out.println(123333);
return "11";
    }

    public static void main(String[] args) {

        String aa="aabbcc";
        String[] vv= aa.split("\\d{5}");
        System.out.println(Arrays.toString(vv));
        System.out.println(vv.length);

    }

}
