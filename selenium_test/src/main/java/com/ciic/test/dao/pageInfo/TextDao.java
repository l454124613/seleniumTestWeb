package com.ciic.test.dao.pageInfo;

import com.ciic.test.service.pageInfo.TextService;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class TextDao implements TextService {
    WebElement elemnet;
    @Override
    public void sendKey(String value) {
        elemnet.sendKeys(value);


    }

    @Override
    public void clear() {
        elemnet.clear();

    }

    @Override
    public String getText() {
        return elemnet.getText();

    }

    @Override
    public TextService setElemnet(WebElement elemnet) {
        this.elemnet=elemnet;
        return this;
    }
}
