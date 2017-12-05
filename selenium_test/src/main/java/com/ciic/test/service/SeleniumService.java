package com.ciic.test.service;

import com.ciic.test.bean.Http4res;
import org.openqa.selenium.WebDriver;

/**
 * Created by lixuecheng on 2017/8/9.
 */
public interface SeleniumService {
    void run(String tid ,String seriesid);
    void closeDriver(WebDriver driver);
    void stopRun(String seid);
    void test(String cid);
    void stopHttpCase(Http4res[] http4res);


}
