package com.ciic.test.service;

import com.ciic.test.bean.Element;
import com.ciic.test.service.pageInfo.ActionService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface GetElementService {



    GetElementService setDriver(WebDriver driver);

    WebElement getElement(Element element);
    Map<String,WebElement> getElements(List<Element> elements);


}
