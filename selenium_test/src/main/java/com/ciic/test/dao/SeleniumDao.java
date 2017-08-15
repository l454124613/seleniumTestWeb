package com.ciic.test.dao;

import com.ciic.test.bean.Element;
import com.ciic.test.bean.Series;
import com.ciic.test.bean.Step;
import com.ciic.test.bean.tmp;
import com.ciic.test.service.CaseService;
import com.ciic.test.service.SeleniumService;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuecheng on 2017/8/9.
 */
@Service
public class SeleniumDao implements SeleniumService {
    private String series;
    private  List<tmp> lt=null;//所有用例
    private  List<tmp> ls=null;//所有step
@Autowired
    private JdbcTemplate jdbcTemplate;

@Value("${test.driver.path}")
private String driverPath;

    @Override
    public void run(String tid) {
        init();
        for (int i = 0; i < lt.size(); i++) {
           String caseListId= lt.get(i).getValue();
           String caseid=lt.get(i).getValue2();
            updateCaseListStatus("1",caseListId);//准备
           String pre=getpre(caseid);
            WebDriver driver=startDriver(tid );
           if(!pre.equals("0")){
//TODO
           }
            updateCaseListStatus("2",caseListId);//正式运行
            try {
                getres(caseListId);
                for (int j = 0; j <ls.size() ; j++) {
                String caseresid=    ls.get(j).getValue();
                String sid=ls.get(j).getValue2();
                updateCaseresTime(caseresid);
                Step step=getStep(sid);
                    Element element=getElement(step.getEid());
                    WebElement webElement=  element2Web(element,driver);
                    action(webElement,step.getCatid(),driver,step.getValue());
                    if(!element.getToframe().equals("-1")){
                        driver.switchTo().defaultContent();
                    }
                    if(!element.getTopage().equals("-1")){
                        toWindow(getTitle(element.getTopage()),driver);

                    }
                    if(!step.getExpid().equals("-1")){
                        System.out.println("exp");
                    }
                    updateCaseListRunnum((j+1)+"",caseListId);






                }
                updateCaseListStatus("3",caseListId);
                closeDriver(driver);
            } catch (NoSuchElementException e) {
                System.out.println("fail");
            }catch (Exception e1){
                System.out.println("warn");
            }


        }

    }

    @Override
    public void setSeries(String series) {
        this.series=series;
    }

    private void init(){
   lt= jdbcTemplate.query("select id value ,cid value2 from casereslist where status =0 order by id",new BeanPropertyRowMapper<>(tmp.class));


    }

    private String getTitle(String pid){
     return    jdbcTemplate.query("select pagetitle value from page where id="+pid,new BeanPropertyRowMapper<>(tmp.class)).get(0).getValue();
    }

    private String  getpre(String id){
        List<tmp>ly=  jdbcTemplate.query("select yuid value from caselist where isused=1 and yuid >0 and id="+id,new BeanPropertyRowMapper<>(tmp.class));
if(ly.size()==0){
    return  "0";
}else {
    return  ly.get(0).getValue();
}
    }

    private void getres(String listid){
       ls= jdbcTemplate.query("select id value,sid value2 from caseres where listid="+listid+"  order by id",new BeanPropertyRowMapper<>(tmp.class));
    }

    private  Step getStep(String sid){
       return  jdbcTemplate.query("select * from step where id="+sid,new BeanPropertyRowMapper<>(Step.class)).get(0);

    }

    private void updateCaseListStatus(String status,String id){
        jdbcTemplate.update("update casereslist set status=  "+status+"  where id="+id );
    }
    private void updateCaseListRunnum(String runnum,String id){
        jdbcTemplate.update("update casereslist set runnum=  "+runnum+"  where id="+id );
    }

    private void updateCaseresTime( String id){
        jdbcTemplate.update("update caseres set time="+ LocalDate.now()+" "+ LocalTime.now()+" where id="+id);
    }
    private void updateCaseresPic(String pic ,String id){
        jdbcTemplate.update("update caseres set pic="+ pic+" where id="+id);
    }
    private WebDriver startDriver(String tid ){
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
      tmp tmp= jdbcTemplate.query("select firstpageurl value from item where id ="+tid,new BeanPropertyRowMapper<>(tmp.class)).get(0);
      driver.get(tmp.getValue());
      return driver;

    }
    private  WebElement waitElementExist(WebElement element) throws NoSuchElementException {
        int i=0;
        while (i<10){
            try {
                boolean res=  element.isEnabled();
                if (res){
                    return element;
                }else {
                    throw new Exception("disabled");
                }

            } catch (Exception e) {
                try {
                    Thread.sleep(1000);
                    i++;
                } catch (InterruptedException e1) {

                }
            }

        }
throw new NoSuchElementException();
    }

    private Element getElement(String eid){
      return  jdbcTemplate.query("select * from element where id ="+eid,new BeanPropertyRowMapper<>(Element.class)).get(0);
    }

    private WebElement element2Web(Element element,WebDriver driver) throws NoSuchElementException {
        if(!element.getToframe().equals("-1")){
            driver.switchTo().frame(element2Web(getElement(element.getToframe()),driver));
        }
        //0:id;1:name;2:tagname;3:linktext;4:classname;5:xpath;6:css;
        WebElement webElement=null;
        switch (element.getLocationMethod()){
            case "1" :webElement=driver.findElement(By.id(element.getValue()));waitTime(element,webElement);break;
            case "2" :webElement=driver.findElement(By.name(element.getValue()));waitTime(element,webElement);break;
            case "3" :webElement=driver.findElement(By.tagName(element.getValue()));waitTime(element,webElement);break;
            case "4" :webElement=driver.findElement(By.linkText(element.getValue()));waitTime(element,webElement);break;
            case "5" :webElement=driver.findElement(By.className(element.getValue()));waitTime(element,webElement);break;
            case "6" :webElement=driver.findElement(By.xpath(element.getValue()));waitTime(element,webElement);break;
            case "7" :webElement=driver.findElement(By.cssSelector(element.getValue()));waitTime(element,webElement);break;

        }
        if (webElement!=null){
            return webElement;
        }else {
            throw new NoSuchElementException();
        }

    }

  private void waitTime(Element element,WebElement webElement)   {
        if(element.getWaitid().equals("-1")){
            return;
        }else {
            if(element.getWaitid().equals(1)){
                try {
                    Thread.sleep(Integer.parseInt(element.getWaitvalue()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                if(element.getWaitid().equals(2)){
                    waitElementExist(webElement);

                }
            }
        }

  }

  private Object action(WebElement webElement,String catid,WebDriver driver,String value){
            switch (catid){
                case "1": click(driver,webElement);return 0;
                case "2": return webElement.isEnabled();
                case "3": webElement.clear();return 0;
                case "4": ;return webElement.isSelected();
                case "5": return webElement.getText();
                case "6": driver.switchTo().alert().accept();return 0;
                case "7": driver.switchTo().alert().dismiss();return 0;
               // case "8": return driver.switchTo().alert().getText();
                //case "9": webElement.click();return true;
               // case "10": webElement.click();return true;
                case "11": webElement.sendKeys(value);return 0;
                case "12": webElement.sendKeys(value);return true;
                case "13": return exist(webElement);
                case "14": driver.navigate().to(value);return 0;
                case "15": driver.navigate().back();return 0;
                default:return 0;
            }
  }

private void click(WebDriver driver,WebElement elemnet){
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();",elemnet);
}

private boolean exist(WebElement webElement){
    try {
        webElement.getTagName();
        return true;
    } catch (Exception e) {
        return false;
    }
}

    public void toWindow(String title,WebDriver driver) {
        Set<String> set= driver.getWindowHandles();
        final String[] aa = {driver.getWindowHandle()};
        set.forEach(k->{
            if(driver.switchTo().window(k).getTitle().equalsIgnoreCase(title)){
                aa[0] =k;
            }

        });
        driver.switchTo().window(aa[0]);
    }

    private void closeDriver(WebDriver driver){
driver.quit();
    }
}
