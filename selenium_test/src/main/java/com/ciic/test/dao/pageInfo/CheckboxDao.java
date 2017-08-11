package com.ciic.test.dao.pageInfo;

import com.ciic.test.service.pageInfo.CheckboxService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class CheckboxDao implements CheckboxService {
    private WebElement elemnet;

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
    public int clear() {
        try {
elemnet.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public boolean isSelect() {
        return elemnet.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return elemnet.isEnabled();
    }

    @Override
    public CheckboxService setElemnet(WebElement elemnet) {
this.elemnet=elemnet;
        return this;
    }
}
