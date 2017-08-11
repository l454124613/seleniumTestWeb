package com.ciic.test.dao;



import com.ciic.test.service.LocationService;
import com.ciic.test.service.WebdriverService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/10.
 */
@Service
public class WebDriverDao implements WebdriverService{


    @Override
    public WebDriver startDrivr() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");


        return   new ChromeDriver();
    }

    @Override
    public int run(WebDriver driver) {
       // locationService.setDriver(driver);

        try {
            driver.get("http://www.baidu.com");
            driver.manage().window().maximize();

            //var script=document.createElement("script");
            ((JavascriptExecutor) driver).executeScript("var script=document.createElement(\"script\");script.type=\"text/javascript\";  \n" +
                    "script.src=\"http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js\";document.getElementsByTagName('head')[0].appendChild(script);  ");
            Thread.sleep(3000);
            Object w=((JavascriptExecutor) driver).executeScript("$('#su').click()");

         // WebElement w1=  (WebElement)w;
            Thread.sleep(3000);
            System.out.println(w);
            return 0;
        } catch (Exception e) {
            return 1;
        }

    }

    public static void main(String[] args) {
        WebDriverDao w=new WebDriverDao();
       WebDriver driver= w.startDrivr();
                w.run(driver);
                w.closeDriver(driver);
    }

    @Override
    public int closeDriver(WebDriver driver) {
        try {
            Thread.sleep(1000);
            driver.quit();
            return 0;
        } catch (Exception e) {
            return 1;
        }

    }
}
