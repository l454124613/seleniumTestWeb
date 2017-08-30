package com.ciic.test.dao;

import com.ciic.test.bean.Element;
import com.ciic.test.bean.HttpCase;
import com.ciic.test.bean.Step;
import com.ciic.test.bean.tmp;
import com.ciic.test.service.SeleniumService;
import com.ciic.test.tools.mycode;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuecheng on 2017/8/9.
 */
@Service
public class SeleniumDao implements SeleniumService {

    private  List<tmp> lt=null;//所有用例
    private  List<tmp> ls=null;//所有step
    private String seidStop="0";
@Autowired
    private JdbcTemplate jdbcTemplate;

@Value("${test.driver.path}")
private String driverPath;
    @Value("${test.picture.path}")
private String picPath;

    @Override
    public void run(String tid,String seriesid) {
        //获得用例列表
        init(seriesid);
        //循环运行
        for (int i = 0; i < lt.size(); i++) {
            if(seriesid.equals(seidStop)){
                break;
            }
           String caseListId= lt.get(i).getValue();
           String caseid=lt.get(i).getValue2();
            updateCaseListStatus("1",caseListId);//准备
           String pre=getpre(caseid);
            WebDriver driver=null;
            try {
//                if(!pre.equals("0")){
//     //TODO
//                }
                switch (pre){
                    case "0":break;
                    case "1":driver=startDriver(tid );break;
                    case "2":break;
                    case "3":break;
                    case "4":break;
                }
            } catch (Exception e) {
                updateCaseListRes("3lolo"+"预置条件出错，用例停止运行。出错原因："+e.getLocalizedMessage(),caseListId);
                continue;

            }


            updateCaseListStatus("2",caseListId);//正式运行
            String nowCaseresid="0";
            try {
                getres(caseListId);
                for (int j = 0; j <ls.size() ; j++) {
                    if(seidStop.equals(seriesid)){
                        throw new InterruptedException("运行被中断");
                    }
                    nowCaseresid=    ls.get(j).getValue();
                String sid=ls.get(j).getValue2();
                updateCaseresTime(nowCaseresid);
                if(sid.equals("0")){

                    String res=runHttpCase(caseid,nowCaseresid);
                    if(res.equals("2")){
                        throw new MyException("校验错误");
                    }else {
                        if(res.equals("3")){
                            throw new Exception("运行失败,详细信息：$$$666未找到可执行的用例，请查看具体步骤");
                        }
                    }

                }else {
                    if(driver==null){
                        driver= startDriver(tid );
                    }
                    Step step = getStep(sid);
                    Element element = getElement(step.getEid());
                    WebElement webElement = element2Web(element, driver);//未修改提示框//TODO
                    screenShot(driver, nowCaseresid, seriesid, caseListId, webElement, false);                    //截图
                    action(webElement, step.getCatid(), driver, step.getValue());
                    if (!element.getToframe().equals("-1")) {
                        driver.switchTo().defaultContent();
                    }
                    if (!element.getTopage().equals("-1")) {
                        toWindow(getTitle(element.getTopage()), driver);

                    }
                    updateCaseListRunnum((j + 1) + "", caseListId);
                    if (!step.getExpid().equals("0")) {
                        System.out.println("exp");
                    }
                    updateCaseresRes("1","运行成功",nowCaseresid);

                }






                }
updateCaseListRes("1",caseListId);

            } catch (NoSuchElementException e) {
                System.out.println("fail");
                screenShot(driver,nowCaseresid,seriesid,caseListId,null,true);
                updateCaseresRes("2",e.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid);
                updateCaseListRes("2",caseListId);
            }
            catch (UnhandledAlertException e){

                System.out.println("fail");
                driver.switchTo().alert().accept();
                screenShot(driver,nowCaseresid,seriesid,caseListId,null,true);
                updateCaseresRes("2",e.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid);
                updateCaseListRes("2",caseListId);
            }catch (InterruptedException e2){
                System.out.println("fail");


                updateCaseresRes("2",e2.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid);
                updateCaseListRes("2",caseListId);
                updateCaseListStatus("3",caseListId);
                closeDriver(driver);
                break;
            }catch (MyException e3){
                System.out.println("fail");


               // updateCaseresRes("2",e2.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid);
                updateCaseListRes("2",caseListId);
                updateCaseListStatus("3",caseListId);
                //closeDriver(driver);
                break;
            }
            catch (Exception e1){
                System.out.println("warn");

                e1.printStackTrace();
                updateCaseListRes("3",caseListId);
                updateCaseresRes("3",e1.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid);
                screenShot(driver,nowCaseresid,seriesid,caseListId,null,true);
            }finally {
                updateCaseListStatus("3",caseListId);
                closeDriver(driver);

            }



        }

    }

public void test(String cid) {


        runHttpCase(cid,"214");

}


private String getHttpCon(String url, Header[] head, String type, String con){
    CloseableHttpClient client = HttpClients.createDefault();
    StringBuffer stringBuffer=new StringBuffer();
    RequestConfig config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000).build();
    try {
        if(type.equals("get")){
            HttpGet httpGet=new HttpGet(url);

            httpGet.setConfig(config);
            if(head!=null){
                httpGet.setHeaders(head);
            }


            stringBuffer.append("请求信息："+httpGet.getRequestLine().toString());
            stringBuffer.append("$$$666");
            stringBuffer.append("请求信息头："+ Arrays.asList(httpGet.getAllHeaders()));
            stringBuffer.append("$$$666");
            HttpResponse response= client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String  temp= EntityUtils.toString(entity,"UTF-8");
            stringBuffer.append("响应信息："+response.getStatusLine());
            stringBuffer.append("$$$666");
            stringBuffer.append("响应信息头："+Arrays.asList(response.getAllHeaders()));
            stringBuffer.append("$$$666");
            stringBuffer.append("响应内容："+temp);
          //  stringBuffer.append("--------------------------------------------------\n");


        }else {
            HttpPost httpPost=new HttpPost(url);
            httpPost.setConfig(config);


            StringEntity entity = new StringEntity(con,"utf-8");
            if(con.charAt(0)=='{'){
                entity.setContentType("application/json");
            }else {
                entity.setContentType("application/x-www-form-urlencoded");
            }
            if(head!=null){
                httpPost.setHeaders(head);
            }
            httpPost.setEntity(entity);
            stringBuffer.append("请求信息："+httpPost.getRequestLine().toString());
            stringBuffer.append("$$$666");
            stringBuffer.append("请求信息头："+Arrays.asList(httpPost.getAllHeaders()));
            stringBuffer.append(httpPost.getEntity());
            stringBuffer.append("$$$666");
            stringBuffer.append("请求内容："+con);
            stringBuffer.append("$$$666");
            HttpResponse response= client.execute(httpPost);
            HttpEntity entity2 = response.getEntity();
            String  temp= EntityUtils.toString(entity2,"UTF-8");
            stringBuffer.append("响应信息："+response.getStatusLine());
            stringBuffer.append("$$$666");
            stringBuffer.append("响应信息头："+Arrays.asList(response.getAllHeaders()));
            stringBuffer.append("$$$666");
            stringBuffer.append("响应内容："+temp);
            //stringBuffer.append("--------------------------------------------------\n");
        }
    } catch (Exception e) {
       stringBuffer.append(e.getLocalizedMessage());
    } finally {
        try {
            client.close();
        } catch (IOException e) {

        }
    }
    return  stringBuffer.toString();

}
private Header[] getheaders(String head){
    String[] a=head.split("&");
    if(a.length>0){
        Header[] headers=new Header[a.length];
        for (int i = 0; i <a.length ; i++) {
            String[] b=a[i].split(":");
            headers[i]=new BasicHeader(b[0],b[1]);
        }
        return headers;
    }else {
        return null;
    }


}

    private String runHttpCase(String cid,String resid)  {
     List<HttpCase> lh=   jdbcTemplate.query("select * from httpcase where cid="+cid,new BeanPropertyRowMapper<>(HttpCase.class));

      if(lh.size()>0)          {
             if(lh.get(0).getType().equals("1")){
                    Header[] headers=null;
                 if(lh.get(0).getCon().contains("HEAD")){
                     String head=lh.get(0).getCon();
                     int st=  head.indexOf("HEAD{");
                     int et=  head.indexOf("}",st);
                     head=head.substring(st+5,et);
                    headers= getheaders(head);



                 }


                String res= getHttpCon(lh.get(0).getUrl(),headers,"get","");
                 boolean isok=false;
                 String eq="";
                 switch (lh.get(0).getEq()){
                     case "1":if(res.equals(lh.get(0).getValue()))isok=true;eq="等于";break;
                     case "2":if(!res.equals(lh.get(0).getValue()))isok=true;eq="不等于";break;
                     case "3":if(res.contains(lh.get(0).getValue()))isok=true;eq="包含";break;
                     case "4":if(!res.contains(lh.get(0).getValue()))isok=true;eq="不包含";break;
                     default: isok=false;
                 }
                if (isok){

                     updateCaseresRes("1","运行成功,详细信息：$$$666"+mycode.praseString2(res)+"$$$666"+eq+lh.get(0).getValue(),resid);
                     return "1";
                }else {
                    updateCaseresRes("2","校验失败,详细信息：$$$666"+mycode.praseString2(res)+"$$$666"+eq+lh.get(0).getValue(),resid);
                    return "2";

                }





             }else {
                 String head = "";
                 Header[] headers = null;
                 if (lh.get(0).getCon().contains("HEAD")) {
                     head = lh.get(0).getCon();
                     int st = head.indexOf("HEAD{");
                     int et = head.indexOf("}", st);
                     head = head.substring(st + 5, et);
                     headers = getheaders(head);


                 }
                 String con = lh.get(0).getCon().replace(head, "").replace("HEAD{}", "");
                 String res = getHttpCon(lh.get(0).getUrl(), headers, "post", con);
                 boolean isok=false;
                 String eq="";
                 switch (lh.get(0).getEq()){
                     case "1":if(res.equals(lh.get(0).getValue()))isok=true;eq="等于";break;
                     case "2":if(!res.equals(lh.get(0).getValue()))isok=true;eq="不等于";break;
                     case "3":if(res.contains(lh.get(0).getValue()))isok=true;eq="包含";break;
                     case "4":if(!res.contains(lh.get(0).getValue()))isok=true;eq="不包含";break;
                     default: isok=false;
                 }
                 if (isok){
                     updateCaseresRes("1","运行成功,详细信息：$$$666"+mycode.praseString2(res)+"$$$666"+eq+lh.get(0).getValue(),resid);
                     return "1";
                 }else {
                     updateCaseresRes("2","校验失败,详细信息：$$$666"+mycode.praseString2(res)+"$$$666"+eq+lh.get(0).getValue(),resid);
                     return "2";

                 }

             }

      }      else {


             // updateCaseresRes("3","运行失败,详细信息：$$$666未找到可执行的用例，请查看具体步骤",resid);
              return "3";

      }
        //CloseableHttpClient client = HttpClients.createDefault();


    }

    private void init(String seriesid){
   lt= jdbcTemplate.query("select id value ,cid value2 from casereslist where status =0 and seriesid= "+seriesid+" order by id",new BeanPropertyRowMapper<>(tmp.class));


    }

    private void updateCaseresRes(String res,String text,String id){
        jdbcTemplate.update("UPDATE caseres set res=? , restext=? where id =?",new Object[]{res,text,id});
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
    private void updateCaseListRes(String res,String id){
        jdbcTemplate.update("update casereslist set res=  "+res+"  where id="+id );
    }
    private void updateCaseListRunnum(String runnum,String id){
        jdbcTemplate.update("update casereslist set runnum=  "+runnum+"  where id="+id );
    }

    private void updateCaseresTime( String id){
        jdbcTemplate.update("update caseres set time='"+ LocalDate.now()+" "+ LocalTime.now()+"' where id="+id);
    }
    private void updateCaseresPic(String pic ,String id){
        jdbcTemplate.update("update caseres set pic='"+ pic+"' where id="+id);
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
throw new NoSuchElementException("元素等不到");
    }

    private Element getElement(String eid){
      return  jdbcTemplate.query("select * from element where id ="+eid,new BeanPropertyRowMapper<>(Element.class)).get(0);
    }

    private void screenShot(WebDriver driver,String resid,String seriesid,String listid ,WebElement element,boolean iserr){
        if(!iserr)
        setYellow(driver,element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File file=new File(picPath+seriesid+"_"+listid+"_"+resid+"_"+System.currentTimeMillis()+".jpg");
        srcFile.renameTo(file);
        updateCaseresPic(file.getName(),resid);
        if(!iserr)
        setYellow(driver,element);


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
            throw new NoSuchElementException("no such element: Unable to locate element: {\"method\":\""+getActionName(element.getLocationMethod())+"\",\"selector\":\""+element.getValue()+"\"}");
        }

    }
    private String getActionName(String id){
        switch (id){
            case "1":return "id";
            case "2":return "name";
            case "3":return "tagName";
            case "4":return "Linktext";
            case "5":return "calssName";
            case "6":return "xpath";
            case "7":return "css";
            default:return "unknow";
        }

    }

  private void waitTime(Element element,WebElement webElement)   {
        if(element.getWaitid().equals("-1")){
            return;
        }else {
            if(element.getWaitid().equals("1")){
                try {
                    Thread.sleep(Integer.parseInt(element.getWaitvalue()));
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    Thread.interrupted();
                }
            }else {
                if(element.getWaitid().equals("2")){
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
                case "4": return webElement.isSelected();
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
                case "17": return driver.switchTo().alert().getText();
                default:return 0;
            }
  }

private void click(WebDriver driver,WebElement elemnet){
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();",elemnet);
}
    private void setYellow(WebDriver driver,WebElement elemnet){
        ((JavascriptExecutor) driver).executeScript("   var rgb=arguments[0].style.backgroundColor;" +

                "if(rgb=='yellow'){arguments[0].style.backgroundColor =''}else{arguments[0].style.backgroundColor = \"yellow\";}" +

                "",elemnet);
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

    public void closeDriver(WebDriver driver){
        try {
            driver.quit();
        } catch (Exception e) {

        }
    }

    @Override
    public void stopRun(String seid) {
this.seidStop=seid;
    }
}
