package com.ciic.test.dao.pageInfo;


import com.ciic.test.service.pageInfo.ActionService;
import com.ciic.test.service.pageInfo.ButtonService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class ButtonDao implements ButtonService {

private  WebElement elemnet;


    @Override
    public int click(WebDriver driver) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();",elemnet);

            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public boolean isEnable() {
        return elemnet.isEnabled();
    }


    @Override
    public ButtonService setElemnet(WebElement elemnet) {
        this.elemnet=elemnet;
        return this;
    }


}
