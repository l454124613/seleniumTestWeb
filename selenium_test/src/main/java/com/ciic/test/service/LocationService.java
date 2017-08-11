package com.ciic.test.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface LocationService {
    WebElement byId();
    WebElement byName();
    WebElement byTagName();
    WebElement byLinkText();
    WebElement byClassName();
    WebElement byXpath();
    WebElement byCss();
    void setDriver(WebDriver driver);
    int navicate(String URL);



}
