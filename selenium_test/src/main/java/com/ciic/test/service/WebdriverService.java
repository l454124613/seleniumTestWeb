package com.ciic.test.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/10.
 */

public interface WebdriverService {

//    public String aa(){
//        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        //  driver.manage().window().maximize();
//        driver.get("http://www.baidu.com");
//
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        driver.quit();
//        System.out.println(123333);
//return "11";
//    }
WebDriver startDrivr();
int run(WebDriver driver);
int  closeDriver(WebDriver driver);

}
