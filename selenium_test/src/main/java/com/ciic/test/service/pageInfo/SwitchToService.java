package com.ciic.test.service.pageInfo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface SwitchToService {
    void window(String title);
    void frame(String name_id_elemnet);
    void frame();
    void defaultContent();
    SwitchToService  setdriver(WebDriver driver);
    SwitchToService  setElement(WebElement element);


}
