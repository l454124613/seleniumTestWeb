package com.ciic.test.service;

import com.ciic.test.bean.Http4res;
import org.openqa.selenium.WebDriver;

/**
 * Created by lixuecheng on 2017/8/9.
 */
public interface SeleniumService {
    void run(String tid ,String seriesid,String type);
    void closeDriver(WebDriver driver);
    void stopRun(String seid);
    void test(String cid);
    void stopHttpCase(Http4res[] http4res,String rid);

    /**
     *
     * @param cvid
     * @param status -1空，1未调试，2调试中，3调试成功，4调试失败
     */
    void setStatus4case(String cvid,String status);


}
