package com.ciic.test.dao;

import com.ciic.test.service.LocationService;
import com.ciic.test.service.WebdriverService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public class LocationDao  implements LocationService{


    private WebDriver driver;



    @Override
    public WebElement byId() {
        return null;
    }

    @Override
    public WebElement byName() {
        return null;
    }

    @Override
    public WebElement byTagName() {
        return null;
    }

    @Override
    public WebElement byLinkText() {
        return null;
    }

    @Override
    public WebElement byClassName() {
        return null;
    }

    @Override
    public WebElement byXpath() {
        return null;
    }

    @Override
    public WebElement byCss() {
        return null;
    }

    @Override
    public void setDriver(WebDriver driver) {
        this.driver=driver;

    }
    @Override
    public int navicate(String URL) {
        return 0;
    }

}
