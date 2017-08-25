package com.ciic.test.service;

import org.openqa.selenium.WebDriver;

/**
 * Created by lixuecheng on 2017/8/9.
 */
public interface SeleniumService {
    void run(String tid ,String seriesid);
    void closeDriver(WebDriver driver);
    void stopRun(String seid);


}
