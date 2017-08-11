package com.ciic.test.service;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/10.
 */
@Service
public class GetCase implements Runnable {
    @Autowired
    private WebdriverService webdriverService;



    @Override
    public void run() {
//     WebDriver driver= webdriverService.startDrivr();
//
//    int a= webdriverService.run(driver);
//   int b=  webdriverService.closeDriver(driver);
//        System.out.println(a);
//        System.out.println(b);


    }
}
