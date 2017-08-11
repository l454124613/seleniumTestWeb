package com.ciic.test.dao;

import com.ciic.test.bean.Element;

import com.ciic.test.service.GetElementService;


import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/11.
 */
@Service
public class GetElemnetDao  implements GetElementService{

    WebDriver driver;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Override
//    public List<Element> get(String page) {
//     return jdbcTemplate.query("SELECT type,locationMethod,value,name,lastupdatetime,updater from element where isused=1 and pid=?",mycode.prase(new Object[]{page},new BeanPropertyRowMapper<Element>(Element.class));
//    }

    @Override
    public GetElementService setDriver(WebDriver driver) {
      this.driver=driver;
      return  this;
    }

    @Override
    public WebElement getElement(Element element) {
            By by=controlElemnet(element.getType(),element.getValue());
            if(by==null){
//                ((JavascriptExecutor) driver).executeScript("var script=document.createElement(\"script\");script.type=\"text/javascript\";  \n" +
//                        "script.src=\"http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js\";document.getElementsByTagName('head')[0].appendChild(script);  ");
//
//              ((JavascriptExecutor) driver).executeScript(element.getValue());
                //TODO
            }else {
                return driver.findElement(by);
            }

        return null;
    }

    @Override
    public Map<String, WebElement> getElements(List<Element> elements) {
        Map<String, WebElement> map=new HashMap<>();
        elements.forEach(k->{
            By by=controlElemnet(k.getLocationMethod(),k.getValue());
            if(by==null){
//TODO
            }else {
                map.put(k.getName()+"1a0a1"+k.getType(),driver.findElement(by));
            }

        });
        return map;
    }


    By controlElemnet(String location,String value){
        switch (location){
            case "0": return By.id(value);
            case "1": return By.name(value);
            case "2": return  By.tagName(value);
            case "3": return By.linkText(value);
            case "4":return By.className(value);
            case "5":return By.xpath(value);
            case "6":return By.cssSelector(value);


        }


        return null;
    }
}
