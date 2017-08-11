package com.ciic.test.dao.pageInfo;

import com.ciic.test.service.pageInfo.DialogService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class DialogDao implements DialogService{

   private WebDriver driver;
    @Override
    public void accept() {
        driver.switchTo().alert().accept();

    }

    @Override
    public void dismiss() {
        driver.switchTo().alert().dismiss();

    }

    @Override
    public String getText() {
    return driver.switchTo().alert().getText();
    }

    @Override
    public DialogService setdriver(WebDriver driver) {
        this.driver=driver;
        return this;
    }


}
