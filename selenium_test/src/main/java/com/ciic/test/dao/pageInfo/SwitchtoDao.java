package com.ciic.test.dao.pageInfo;

import com.ciic.test.bean.Element;
import com.ciic.test.service.pageInfo.SwitchToService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by lixuecheng on 2017/7/12.
 */
@Service
public class SwitchtoDao implements SwitchToService {

   private WebDriver driver;
   private WebElement element;
    @Override
    public void window(String title) {
       Set<String> set= driver.getWindowHandles();
        final String[] aa = {driver.getWindowHandle()};
       set.forEach(k->{
           if(driver.switchTo().window(k).getTitle().equalsIgnoreCase(title)){
               aa[0] =k;
           }

       });
        driver.switchTo().window(aa[0]);
    }

    @Override
    public void frame(String name_id_elemnet) {
        driver.switchTo().frame(name_id_elemnet);

    }

    @Override
    public void frame() {
        driver.switchTo().frame(element);

    }

    @Override
    public void defaultContent() {
        driver.switchTo().defaultContent();

    }

    @Override
    public SwitchToService setdriver(WebDriver driver) {
        this.driver=driver;
        return this;
    }

    @Override
    public SwitchToService setElement(WebElement element) {
        this.element=element;
        return this;
    }
}
