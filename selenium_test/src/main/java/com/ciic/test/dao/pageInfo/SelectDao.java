package com.ciic.test.dao.pageInfo;

import com.ciic.test.service.pageInfo.SelectService;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class SelectDao implements SelectService{
    private WebElement element;
    Select select;
    @Override
    public void selectByVisibleText(String text) {
        select.selectByVisibleText(text);


    }

    @Override
    public void selectByValue(String text) {
        select.selectByValue(text);

    }

    @Override
    public void deselectAll() {
select.deselectAll();
    }

    @Override
    public SelectService setElemnet(WebElement elemnet) {
this.element=elemnet;
 select= new Select(element);
        return this;
    }
}
