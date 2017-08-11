package com.ciic.test.service.pageInfo;

import org.openqa.selenium.WebElement;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface UploadService extends ActionService{

    void send(String FilePath);
    UploadService setElemnet(WebElement elemnet);


}
