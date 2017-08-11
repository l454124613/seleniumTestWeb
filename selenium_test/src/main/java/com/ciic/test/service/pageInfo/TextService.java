package com.ciic.test.service.pageInfo;

import org.openqa.selenium.WebElement;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface TextService extends ActionService{
    void sendKey(String value);
    void  clear();
    String getText();
    TextService setElemnet(WebElement elemnet);

}
