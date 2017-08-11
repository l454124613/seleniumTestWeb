package com.ciic.test.service.pageInfo;

import org.openqa.selenium.WebElement;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface SelectService extends ActionService{

    void  selectByVisibleText(String text);
    void selectByValue(String text);
    void deselectAll();
    SelectService setElemnet(WebElement elemnet);

}
