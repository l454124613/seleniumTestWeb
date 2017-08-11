package com.ciic.test.dao.pageInfo;

import com.ciic.test.service.pageInfo.UploadService;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class UploadDao implements UploadService {
    private WebElement element;
    @Override
    public void send(String FilePath) {
        element.sendKeys(FilePath);

    }

    @Override
    public UploadService setElemnet(WebElement elemnet) {
        this.element=elemnet;
        return this;
    }
}
