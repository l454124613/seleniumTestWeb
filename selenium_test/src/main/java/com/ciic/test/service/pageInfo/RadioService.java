package com.ciic.test.service.pageInfo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface RadioService extends ActionService{

    int click(WebDriver driver);
    int clear();
    boolean isSelect();
    RadioService setElemnet(WebElement elemnet);



}
