package com.ciic.test.dao;

import com.alibaba.fastjson.JSON;
import com.ciic.test.bean.*;
import com.ciic.test.service.*;
import com.ciic.test.tools.SocketProxy;
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
import org.apache.regexp.RE;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuecheng on 2017/8/9.
 */
@Service
public class SeleniumDao implements SeleniumService {

    private  List<tmp>  lt       =null;//所有用例
    private  List<tmp3> ls       =null;//所有step
    private String      seidStop ="0";
    private String      isWin ="0";
    private  Http4res[] http4res;
    private String rid="-1";

    public boolean isIshttpStop() {
        return ishttpStop;
    }

    public void setIshttpStop(boolean ishttpStop,String rid) {
        this.ishttpStop = ishttpStop;
        this.rid=rid;
    }

    private boolean ishttpStop =false;



    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
private ConfigService configService;

@Autowired
private com.ciic.test.service.Proxy proxy;

@Value("${test.driver.path}")
private String driverPath;
    @Value("${test.picture.path}")
private String picPath;
    @Value("${test.file.path}")
    private String filePath;
    @Value("${server.port}")
    private String webport;


    @Override
    public void run(String tid,String seriesid,String type,String vid) {
        //获得用例列表
        init(seriesid);
        //循环运行
        for (int i = 0; i < lt.size(); i++) {
            if(seriesid.equals(seidStop)){ //是否中断
                break;
            }
           String caseListId= lt.get(i).getValue();
           String caseid=lt.get(i).getValue2();
            updateCaseListStatus("1",caseListId);//准备
         //  String pre=getpre(caseid);
            WebDriver[] driver={null};
         //   driver[0].findElements(By.tagName("")).get(0).getCssValue()

//            try {
//
//                runPre(pre,tid);
//            } catch (Exception e) {
//                updateCaseListRes("3lolo"+"预置条件出错，用例停止运行。出错原因："+e.getLocalizedMessage(),caseListId);
//                continue;
//
//            }


            updateCaseListStatus("2",caseListId);//正式运行
            String[] nowCaseresid={"0"};
            try {

                getres(caseListId);
                runStep(seriesid,driver,tid,caseListId,nowCaseresid,caseid,vid);

                updateCaseListRes("1",caseListId);
                setStatus4case(type,"3");
            } catch (NoSuchElementException e) {
               setStatus4case(type,"4");
                System.out.println("fail");
                screenShot(driver[0],nowCaseresid[0],seriesid,caseListId,null,true);
                updateCaseresRes("2",e.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid[0]);
                updateCaseListRes("2",caseListId);
                List<tmp> lt2= jdbcTemplate.query("select cida value,cidb value2 from runtimecase where tid="+tid+"  and cida=(SELECT cid from caseres where id="+nowCaseresid[0]+")",new BeanPropertyRowMapper<>(tmp.class));
                if(lt2.size()>1){
                    for (int j = 1; j < lt2.size(); j++) {
                        List<tmp> lt3 =  jdbcTemplate.query("select listid value,id value2 from caseres where id > "+nowCaseresid[0]+" and cid = "+lt2.get(j).getValue2(),new BeanPropertyRowMapper<>(tmp.class));
                        for (int k = 0; k <lt3.size() ; k++) {
                            jdbcTemplate.update("update casereslist set res=3 where res=-1 and  id="+lt3.get(k).getValue());
                            List<tmp> lt5= jdbcTemplate.query("select id value from caseres where listid="+lt3.get(k).getValue()+" order by id ",new BeanPropertyRowMapper<>(tmp.class));
                            if(lt5.size()>0){
                                jdbcTemplate.update("UPDATE caseres set res=3 , restext='预置条件运行失败', time='"+LocalDate.now()+" "+LocalTime.now()+"' where res =-1 and id="+lt5.get(0).getValue());

                            }
                        }
                    }
                    init(seriesid);
                    i=0;

                }
            }
            catch (UnhandledAlertException e){
                setStatus4case(type,"4");
                System.out.println("fail");
                driver[0].switchTo().alert().accept();
                screenShot(driver[0],nowCaseresid[0],seriesid,caseListId,null,true);
                updateCaseresRes("2",e.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid[0]);
                updateCaseListRes("2",caseListId);
                List<tmp> lt2= jdbcTemplate.query("select cida value,cidb value2 from runtimecase where tid="+tid+"  and cida=(SELECT cid from caseres where id="+nowCaseresid[0]+")",new BeanPropertyRowMapper<>(tmp.class));
                if(lt2.size()>1){
                    for (int j = 1; j < lt2.size(); j++) {
                        List<tmp> lt3 =  jdbcTemplate.query("select listid value,id value2 from caseres where id > "+nowCaseresid[0]+" and cid = "+lt2.get(j).getValue2(),new BeanPropertyRowMapper<>(tmp.class));
                        for (int k = 0; k <lt3.size() ; k++) {
                            jdbcTemplate.update("update casereslist set res=3 where id="+lt3.get(k).getValue());
                            List<tmp> lt5= jdbcTemplate.query("select id value from caseres where listid="+lt3.get(k).getValue()+" order by id ",new BeanPropertyRowMapper<>(tmp.class));
                            if(lt5.size()>0){
                                jdbcTemplate.update("UPDATE caseres set res=3 , restext='预置条件运行失败', time='"+LocalDate.now()+" "+LocalTime.now()+"' where id="+lt5.get(0).getValue());

                            }
                        }
                    }
                    init(seriesid);
                    i=0;

                }
            }catch (InterruptedException e2){
                System.out.println("fail");
                setStatus4case(type,"4");

                updateCaseresRes("2",e2.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid[0]);
                updateCaseListRes("2",caseListId);
                updateCaseListStatus("3",caseListId);
               // closeDriver(driver);
                break;
            }catch (MyException e3){
                System.out.println("fail");
                setStatus4case(type,"4");

               // updateCaseresRes("2",e2.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid);
                updateCaseListRes("2",caseListId);
                updateCaseListStatus("3",caseListId);
                //closeDriver(driver);
               // String a="select cida value,cidb value2 from runtimecase where tid="+tid+" cida=(SELECT cid from caseres where id="+nowCaseresid[0]+")";

                List<tmp> lt2= jdbcTemplate.query("select cida value,cidb value2 from runtimecase where tid="+tid+" and cida=(SELECT cid from caseres where id="+nowCaseresid[0]+")",new BeanPropertyRowMapper<>(tmp.class));
                if(lt2.size()>1){
                    for (int j = 1; j < lt2.size(); j++) {
                        List<tmp> lt3 =  jdbcTemplate.query("select listid value,id value2 from caseres where id > "+nowCaseresid[0]+" and cid = "+lt2.get(j).getValue2(),new BeanPropertyRowMapper<>(tmp.class));
                        for (int k = 0; k <lt3.size() ; k++) {
                            jdbcTemplate.update("update casereslist set res=3 where id="+lt3.get(k).getValue());
                            List<tmp> lt5= jdbcTemplate.query("select id value from caseres where listid="+lt3.get(k).getValue()+" order by id ",new BeanPropertyRowMapper<>(tmp.class));
                            if(lt5.size()>0){
                                jdbcTemplate.update("UPDATE caseres set res=3 , restext='预置条件运行失败', time='"+LocalDate.now()+" "+LocalTime.now()+"' where restext='-1' and id="+lt5.get(0).getValue());

                            }
                        }
                    }
                    init(seriesid);
                    i=0;

                }
            }
            catch (Exception e1){
                System.out.println("warn");
                setStatus4case(type,"4");
                e1.printStackTrace();
                updateCaseListRes("3",caseListId);
                updateCaseresRes("3",e1.getLocalizedMessage().replace("\n","<br>").replace("(","%21").replace(")","%22").replace("{","%23").replace("}","%24").replace("\"","%25").replace("'","%26").replace("\\","\\\\"),nowCaseresid[0]);
                screenShot(driver[0],nowCaseresid[0],seriesid,caseListId,null,true);
                List<tmp> lt2= jdbcTemplate.query("select cida value,cidb value2 from runtimecase where tid="+tid+"  and cida=(SELECT cid from caseres where id="+nowCaseresid[0]+")",new BeanPropertyRowMapper<>(tmp.class));
                if(lt2.size()>1){
                    for (int j = 1; j < lt2.size(); j++) {
                        List<tmp> lt3 =  jdbcTemplate.query("select listid value,id value2 from caseres where id > "+nowCaseresid[0]+" and cid = "+lt2.get(j).getValue2(),new BeanPropertyRowMapper<>(tmp.class));
                        for (int k = 0; k <lt3.size() ; k++) {
                            jdbcTemplate.update("update casereslist set res=3 where id="+lt3.get(k).getValue());
                           List<tmp> lt5= jdbcTemplate.query("select id value from caseres where listid="+lt3.get(k).getValue()+" order by id ",new BeanPropertyRowMapper<>(tmp.class));
                           if(lt5.size()>0){
                               jdbcTemplate.update("UPDATE caseres set res=3 , restext='预置条件运行失败', time='"+LocalDate.now()+" "+LocalTime.now()+"' where id="+lt5.get(0).getValue());

                           }
                        }
                    }
                    init(seriesid);
                    i=0;

                }
            }finally {
                updateCaseListStatus("3",caseListId);
                closeDriver(driver[0]);

            }



        }

    }

    private void  checkSql(String sid,String resid) throws Exception {
        List<tmp3> lt=jdbcTemplate.query("select a value1 ,b value2,c value3 from exp where sid="+sid,new BeanPropertyRowMapper<>(tmp3.class));
if(lt.size()==1){
    String iscon=configService.connectDatasource(lt.get(0).getValue1());
    if(iscon.equals("连接成功")){
        ResultSet rs= configService.selectData(lt.get(0).getValue2());
        String[] st2=lt.get(0).getValue3().split("、");
        boolean isok=true;
        String text="";
        if(rs.next()){
            for (String s2:st2){
                String[] s3=s2.split("=");
                String rs2=rs.getString(s3[0]);
               if(rs2.equals(s3[1])){
                   text+=s2+" ";
               }else {
                   text+="期望："+s2+",实际为:"+rs2;
                   isok=false;
               }
            }


        }
        if(isok){
            updateCaseresRes("1",text,resid);

        }else {
            updateCaseresRes("2",text,resid);
            throw new MyException(text);

        }




    }else {
        throw new Exception(iscon);
    }




}else {
    throw new Exception("没有找到期望结果。");
}


    }

    private String runSql(String resid){
        List<tmp> lt=jdbcTemplate.query("SELECT a value ,b value2 from precondition where type=2 and cid =(SELECT cid from caseres where id="+resid+")",new BeanPropertyRowMapper<>(tmp.class));
        if(lt.size()==1){
        String daid=    lt.get(0).getValue();
        String sqls=   lt.get(0).getValue2();

String iscon=configService.connectDatasource(daid);
if(iscon.equals("连接成功")){
    String[] datas=sqls.replace("<br/>","").replace("%25","\"").replace("%26","'").split(";");
    String re="";
    boolean isok=true;
    for (String d:datas){
        if(d.length()<6){
            continue;
        }
        String[] con1=  configService.updateDate(d);
        re+="<br/>执行sql为："+d.replace("'","%26").replace("\"","%25");
        if(con1[1].length()>0){
            re+="<br/>"+con1[1].replace("\n","<br/>").replace("\"","\\\"")+"<br/>";
            isok=false;
        }else {
            re+="<br/>修改数据:"+con1[0]+"条<br/>";

        }
    }

if(isok){
    updateCaseresRes("1",re,resid);
        return "";
}else {
   // updateCaseresRes("3",re,resid);
    return re;
}


}else {
  //  updateCaseresRes("3",iscon.replace("\"","\\\""),resid);

    return iscon.replace("\"","\\\"");
}

        }else {

            return "查不到对应的预置条件";
        }


    }

    private  void  runStep(String seriesid,WebDriver[] driver,String tid,String caseListId,String[] nowCaseresid,String caseid,String vid) throws Exception {
        for (int j = 0; j <ls.size() ; j++) {
            if(seidStop.equals(seriesid)){
                throw new InterruptedException("运行被中断");
            }
          //  System.out.println();
            int nnu=0;
            while (proxy.getMap().size()!=0){
                Thread.sleep(1000);
                nnu++;
               // System.out.println(proxy.getMap());
                if(nnu>40){
                    proxy.getMap().clear();
                    break;
                }
            }

            nowCaseresid[0]=    ls.get(j).getValue1();
            String sid=ls.get(j).getValue2();
            String type=ls.get(j).getValue3();
            updateCaseresTime(nowCaseresid[0]);
            if(type.equals("3")){
                //System.out.println(caseid);
              //  System.out.println(nowCaseresid[0]);
                runHttpCase(nowCaseresid[0],2);//1,预置条件2,用例3，



            }else {
                if(type.equals("1")){
                    if(driver[0]==null){
                        driver[0]= startDriver(tid );
                    }
                    Step step = getStep(sid);
                    Element element = getElement(step.getEid(),vid);
                    WebElement webElement ;//未修改提示框//TODO
                    if(!isWin.equals("0")){

                      webElement=  toWindow(driver[0],isWin,element,vid);
                        isWin="0";
                    }else {
                        webElement=element2Web(element, driver[0],false,vid);
                    }
                    screenShot(driver[0], nowCaseresid[0], seriesid, caseListId, webElement, false);                    //截图
                    action(webElement, step.getCatid(), driver[0], step.getValue());
                    if (!element.getToframe().equals("-1")) {
                        driver[0].switchTo().defaultContent();
                    }
                    if (!element.getTopage().equals("-1")) {
                       // toWindow( driver[0],element.getTopage());
                        isWin=element.getTopage();

                    }
                    updateCaseListRunnum((j + 1) + "", caseListId);
                    updateCaseresRes("1","运行成功",nowCaseresid[0]);
                    if (!step.getExpid().equals("0")) {
                        List<Expected> le=jdbcTemplate.query("select * from exp where sid="+step.getId(),new BeanPropertyRowMapper<>(Expected.class));
                       int a12=jdbcTemplate.update("INSERT INTO \"caseres\" (\"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\", \"type\") VALUES ( "+caseid+", "+sid+", '', '预期结果', -1, -1, '"+LocalDate.now()+" "+LocalTime.now()+"', "+caseListId+", 6)");
                       if(a12!=1){
                           throw new  Exception("创建期望结果失败。");
                       }
                      List<tmp> lt6= jdbcTemplate.query("select id value from caseres where listid="+caseListId+" and type=6 and sid="+sid,new BeanPropertyRowMapper<>(tmp.class));
                       if(lt6.size()==0){
                           throw new  Exception("创建期望结果失败!");
                       }
                       if(le.size()==1){
                           nowCaseresid[0]= lt6.get(0).getValue();
                            switch (le.get(0).getType()){
                                case "1": Element element2 = getElement(le.get(0).getB().split(":")[0],vid);
                                WebElement webElement2 = element2Web(element2, driver[0],false,vid);
                                screenShot(driver[0], lt6.get(0).getValue(), seriesid, caseListId, webElement2, false);
                                Object o1=action(webElement2, le.get(0).getC(), driver[0], "");
                                boolean res=false;
                                 String act=   getOneAction(le.get(0).getC());
                                    String eq="";
                                    String t1="";
                                if(o1 instanceof Boolean){
                                    if(((Boolean) o1).booleanValue()){
                                        res=true;
                                    }
                                }else {
                                 t1=((String) o1);


                                    switch (le.get(0).getD()){
                                        case "1":if(t1.equals(le.get(0).getE()))res=true;eq="等于";break;
                                        case "2":if(!t1.equals(le.get(0).getE()))res=true;eq="不等于";break;
                                        case "3":if(t1.contains(le.get(0).getE()))res=true;eq="包含";break;
                                        case "4":if(!t1.contains(le.get(0).getE()))res=true;eq="不包含";break;}

                                }
                                if(res){
                                    updateCaseresRes("1",act+eq+t1,lt6.get(0).getValue());
                                }else {
                                    updateCaseresRes("2",act+eq+t1,lt6.get(0).getValue());
                                    throw new MyException("");
                                }

                                break;
                                case  "2":checkSql(sid,lt6.get(0).getValue());break;

                                case  "4":jdbcTemplate.update("DELETE from caseres where id="+lt6.get(0).getValue());break;
                            }
                        }else {
                            throw new Exception("未找到预期结果。");
                        }
                       // System.out.println("exp");
                    }

                }else if(type.equals("2")){
                    String re2=runSql(nowCaseresid[0]);
                    if(re2.length()>0){
                        throw new Exception(re2);
                    }



                }else if(type.equals("4")){

                            runHttpCase(nowCaseresid[0],3);//1,预置条件2,用例3，







                    }else {
                        throw new Exception("no case!");
                    }



                }



            }








    }

    private void runPre(String pre,String tid){
        switch (pre){
            case "0":break;
            case "1":break;
            case "2":break;
            case "3":break;
            case "4":break;
        }

    }

public void test(String cid) {


    //    runHttpCase("214");

}

    @Override
    public void stopHttpCase(Http4res[] http4res,String rid) {
        this.http4res=http4res;
        setIshttpStop(true,rid);
    }

    @Override
    public void setStatus4case(String cvid, String status) {
        jdbcTemplate.update("update case_version set status=? where id=?",new Object[]{status,cvid});
    }


//private String getHttpCon(String url, Header[] head, String type, String con){
//    CloseableHttpClient client = HttpClients.createDefault();
//    StringBuffer stringBuffer=new StringBuffer();
//    RequestConfig config = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(20000).build();
//    try {
//        if(type.equals("get")){
//            HttpGet httpGet=new HttpGet(url);
//
//            httpGet.setConfig(config);
//            if(head!=null){
//                httpGet.setHeaders(head);
//            }
//
//
//            stringBuffer.append("请求信息："+httpGet.getRequestLine().toString());
//            stringBuffer.append("$$$666");
//            stringBuffer.append("请求信息头："+ Arrays.asList(httpGet.getAllHeaders()));
//            stringBuffer.append("$$$666");
//            HttpResponse response= client.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            String  temp= EntityUtils.toString(entity,"UTF-8");
//            stringBuffer.append("响应信息："+response.getStatusLine());
//            stringBuffer.append("$$$666");
//            stringBuffer.append("响应信息头："+Arrays.asList(response.getAllHeaders()));
//            stringBuffer.append("$$$666");
//            stringBuffer.append("响应内容："+temp);
//          //  stringBuffer.append("--------------------------------------------------\n");
//
//
//        }else {
//            HttpPost httpPost=new HttpPost(url);
//            httpPost.setConfig(config);
//
//
//            StringEntity entity = new StringEntity(con,"utf-8");
//            if(con.charAt(0)=='{'){
//                entity.setContentType("application/json");
//            }else {
//                entity.setContentType("application/x-www-form-urlencoded");
//            }
//            if(head!=null){
//                httpPost.setHeaders(head);
//            }
//            httpPost.setEntity(entity);
//            stringBuffer.append("请求信息："+httpPost.getRequestLine().toString());
//            stringBuffer.append("$$$666");
//            stringBuffer.append("请求信息头："+Arrays.asList(httpPost.getAllHeaders()));
//            stringBuffer.append(httpPost.getEntity());
//            stringBuffer.append("$$$666");
//            stringBuffer.append("请求内容："+con);
//            stringBuffer.append("$$$666");
//            HttpResponse response= client.execute(httpPost);
//            HttpEntity entity2 = response.getEntity();
//            String  temp= EntityUtils.toString(entity2,"UTF-8");
//            stringBuffer.append("响应信息："+response.getStatusLine());
//            stringBuffer.append("$$$666");
//            stringBuffer.append("响应信息头："+Arrays.asList(response.getAllHeaders()));
//            stringBuffer.append("$$$666");
//            stringBuffer.append("响应内容："+temp);
//            //stringBuffer.append("--------------------------------------------------\n");
//        }
//    } catch (Exception e) {
//       stringBuffer.append(e.getCause()==null?e.getLocalizedMessage():e.getCause().getLocalizedMessage());
//    } finally {
//        try {
//            client.close();
//        } catch (IOException e) {
//
//        }
//    }
//    return  stringBuffer.toString();
//
//}
//private Header[] getheaders(String head){
//    String[] a=head.split("&");
//    if(a.length>0){
//        Header[] headers=new Header[a.length];
//        for (int i = 0; i <a.length ; i++) {
//            String[] b=a[i].split(":");
//            headers[i]=new BasicHeader(b[0],b[1]);
//        }
//        return headers;
//    }else {
//        return null;
//    }
//
//
//}


    private String getjm(int a,Http4j h,String resid){
        switch (a){
            case 1:return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<jmeterTestPlan version=\"1.2\" properties=\"3.2\" jmeter=\"3.3 r1808647\">\n" +
                    "\t <hashTree>\n" +
                    "\t   <TestPlan guiclass=\"TestPlanGui\" testclass=\"TestPlan\" testname=\"测试计划\" enabled=\"true\">\n" +
                    "      <stringProp name=\"TestPlan.comments\"></stringProp>\n" +
                    "      <boolProp name=\"TestPlan.functional_mode\">true</boolProp>\n" +
                    "      <boolProp name=\"TestPlan.serialize_threadgroups\">true</boolProp>\n" +
                    "      <elementProp name=\"TestPlan.user_defined_variables\" elementType=\"Arguments\" guiclass=\"ArgumentsPanel\" testclass=\"Arguments\" testname=\"用户定义的变量\" enabled=\"true\">\n" +
                    "        <collectionProp name=\"Arguments.arguments\"/>\n" +
                    "      </elementProp>\n" +
                    "      <stringProp name=\"TestPlan.user_define_classpath\"></stringProp>\n" +
                    "    </TestPlan>\n" +
                    "\t<hashTree>\n" +
                    "\t <CookieManager guiclass=\"CookiePanel\" testclass=\"CookieManager\" testname=\"HTTP Cookie 管理器\" enabled=\"true\">\n" +
                    "        <collectionProp name=\"CookieManager.cookies\"/>\n" +
                    "        <boolProp name=\"CookieManager.clearEachIteration\">false</boolProp>\n" +
                    "      </CookieManager>\n" +
                    "\t  <hashTree/>\n" +
                    "\t  <ThreadGroup guiclass=\"ThreadGroupGui\" testclass=\"ThreadGroup\" testname=\"线程组\" enabled=\"true\">\n" +
                    "        <stringProp name=\"ThreadGroup.on_sample_error\">continue</stringProp>\n" +
                    "        <elementProp name=\"ThreadGroup.main_controller\" elementType=\"LoopController\" guiclass=\"LoopControlPanel\" testclass=\"LoopController\" testname=\"循环控制器\" enabled=\"true\">\n" +
                    "          <boolProp name=\"LoopController.continue_forever\">false</boolProp>\n" +
                    "          <stringProp name=\"LoopController.loops\">1</stringProp>\n" +
                    "        </elementProp>\n" +
                    "        <stringProp name=\"ThreadGroup.num_threads\">1</stringProp>\n" +
                    "        <stringProp name=\"ThreadGroup.ramp_time\">1</stringProp>\n" +
                    "        <longProp name=\"ThreadGroup.start_time\">1511918674000</longProp>\n" +
                    "        <longProp name=\"ThreadGroup.end_time\">1511918674000</longProp>\n" +
                    "        <boolProp name=\"ThreadGroup.scheduler\">false</boolProp>\n" +
                    "        <stringProp name=\"ThreadGroup.duration\"></stringProp>\n" +
                    "        <stringProp name=\"ThreadGroup.delay\"></stringProp>\n" +
                    "      </ThreadGroup>\n" +
                    "\t  <hashTree>\n" +
                    "\t  <BeanShellSampler guiclass=\"BeanShellSamplerGui\" testclass=\"BeanShellSampler\" testname=\"BeanShell Sampler\" enabled=\"true\">\n" +
                    "          <stringProp name=\"BeanShellSampler.query\">List l=new ArrayList();\n" +
                    "vars.putObject(&quot;dat&quot;,l);\n" +
                    "SampleResult.setResponseCodeOK();</stringProp>\n" +
                    "          <stringProp name=\"BeanShellSampler.filename\"></stringProp>\n" +
                    "          <stringProp name=\"BeanShellSampler.parameters\"></stringProp>\n" +
                    "          <boolProp name=\"BeanShellSampler.resetInterpreter\">false</boolProp>\n" +
                    "        </BeanShellSampler>\n" +
                    "\t\t<hashTree/>";
            case 2:{ String re= "<HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\""+h.getName()+"\" enabled=\"true\">\n" ;
            String body=h.getBody();
            boolean isJson=false;
            if(body.trim().isEmpty()||body.contains("(格式是xxx=yyy，完成一条数据再回车，或者使用json，{...},在第一行标明#JSON)")){
                re+="\t\t <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"用户定义的变量\" enabled=\"true\">\n" +
                        "            <collectionProp name=\"Arguments.arguments\"/>\n" +
                        "          </elementProp>\n";
            }else if(body.startsWith("#JSON&#xd;")){
                isJson=true;
                re+= "          <boolProp name=\"HTTPSampler.postBodyRaw\">true</boolProp>\n" +
                        "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\">\n" +
                        "            <collectionProp name=\"Arguments.arguments\">\n" +
                        "              <elementProp name=\"\" elementType=\"HTTPArgument\">\n" +
                        "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
                        "                <stringProp name=\"Argument.value\">"+body.replace("#JSON&#xd;","")+"</stringProp>\n" +
                        "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                        "              </elementProp>\n" +
                        "            </collectionProp>\n" +
                        "          </elementProp>\n";
            }else {
               String[] b2= body.split("&#xd;");
                re+="\t\t <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\" guiclass=\"HTTPArgumentsPanel\" testclass=\"Arguments\" testname=\"用户定义的变量\" enabled=\"true\">\n" +
                        "            <collectionProp name=\"Arguments.arguments\">\n";
                for(String b3:b2){
                    String[] b4=b3.split("=");
                    re+="              <elementProp name=\""+b4[0]+"\" elementType=\"HTTPArgument\">\n" +
                            "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
                            "                <stringProp name=\"Argument.value\">"+b3.replace(b4[0]+"=","")+"</stringProp>\n" +
                            "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                            "                <boolProp name=\"HTTPArgument.use_equals\">true</boolProp>\n" +
                            "                <stringProp name=\"Argument.name\">"+b4[0]+"</stringProp>\n" +
                            "              </elementProp>\n";
                }
                re+= "            </collectionProp>\n" +
                        "          </elementProp>\n";

            }
            re+="          <stringProp name=\"HTTPSampler.domain\">"+h.getBase().getHost()+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.port\">"+h.getBase().getPort()+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.protocol\">"+h.getBase().getHttp().getValue()+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.contentEncoding\">"+h.getBase().getEncoding()+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.path\">"+h.getBase().getPath()+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.method\">"+h.getBase().getMethod().getValue()+"</stringProp>\n" +
                    "          <boolProp name=\"HTTPSampler.follow_redirects\">false</boolProp>\n" +
                    "          <boolProp name=\"HTTPSampler.auto_redirects\">false</boolProp>\n" +
                    "          <boolProp name=\"HTTPSampler.use_keepalive\">false</boolProp>\n" +
                    "          <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
                    "          <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.connect_timeout\">"+h.getBase().getTime4req()+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.response_timeout\">"+h.getBase().getTime4res()+"</stringProp>\n";
            re+= "        </HTTPSamplerProxy>\n" +
                    "        <hashTree>\n" +
                    "          <BeanShellPostProcessor guiclass=\"TestBeanGUI\" testclass=\"BeanShellPostProcessor\" testname=\"BeanShell PostProcessor\" enabled=\"true\">\n" +
                    "            <boolProp name=\"resetInterpreter\">false</boolProp>\n" +
                    "            <stringProp name=\"parameters\"></stringProp>\n" +
                    "            <stringProp name=\"filename\"></stringProp>\n" +
                    "            <stringProp name=\"script\">String name=&quot;"+h.getName()+"&quot;;\n" +
                    "String reqh=prev.getRequestHeaders().replace(&quot;\\n&quot;,&quot;\\\\n&quot;).replace(&quot;\\&quot;&quot;,&quot;%shuang&quot;).replace(&quot;\\&apos;&quot;,&quot;%dan&quot;);\n" +
                    "String rescode=prev.getResponseCode().replace(&quot;\\&quot;&quot;,&quot;%shuang&quot;).replace(&quot;\\&apos;&quot;,&quot;%dan&quot;);\n" +
                    "String resds=prev.getResponseDataAsString().replace(&quot;\\&quot;&quot;,&quot;%shuang&quot;).replace(&quot;\\&apos;&quot;,&quot;%dan&quot;).replace(&quot;\\n&quot;,&quot;\\\\n&quot;).replace(&quot; &quot;,&quot;&quot;).replace(&quot;\\r&quot;,&quot;&quot;).replace(&quot;\\t&quot;,&quot;&quot;).replace(&quot;\\\\&quot;,&quot;%gang&quot;);\n" +
                    "String resh=prev.getResponseHeaders().replace(&quot;\\n&quot;,&quot;\\\\n&quot;).replace(&quot;\\&quot;&quot;,&quot;%shuang&quot;).replace(&quot;\\&apos;&quot;,&quot;%dan&quot;);\n" +
                    "String resmsg=prev.getResponseMessage().replace(&quot;\\n&quot;,&quot;\\\\n&quot;).replace(&quot;\\r&quot;,&quot;&quot;).replace(&quot;\\&quot;&quot;,&quot;%shuang&quot;).replace(&quot;\\&apos;&quot;,&quot;%dan&quot;);\n" +
                    "String reqb=prev.getSamplerData().replace(&quot;\\&quot;&quot;,&quot;%shuang&quot;).replace(&quot;\\&apos;&quot;,&quot;%dan&quot;).replace(&quot;\\n&quot;,&quot;\\\\n&quot;).replace(&quot;\\r&quot;,&quot;&quot;).replace(&quot;\\\\&quot;,&quot;%gang&quot;);\n" +
                    "long sby=prev.getSentBytes();\n" +
                    "long rby=prev.getBytesAsLong();\n" +
                    "String url=prev.getUrlAsString();\n" +
                    "boolean isok=prev.isSuccessful();\n" +
                    " StringBuilder sb = new StringBuilder(&quot;{&quot;);\n" +
                    "        sb.append(&quot;\\&quot;requestHeaders\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(reqh).append(&apos;\\&quot;&apos;);\n" +
                    "                sb.append(&quot;,\\&quot;requestdata\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(reqb).append(&apos;\\&quot;&apos;);\n" +
                    "        sb.append(&quot;,\\&quot;responseCode\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(rescode).append(&apos;\\&quot;&apos;);\n" +
                    "                sb.append(&quot;,\\&quot;name\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(name).append(&apos;\\&quot;&apos;);\n" +
                    "        sb.append(&quot;,\\&quot;responseDataAsString\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(resds).append(&apos;\\&quot;&apos;);\n" +
                    "        sb.append(&quot;,\\&quot;responseHeaders\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(resh).append(&apos;\\&quot;&apos;);\n" +
                    "        sb.append(&quot;,\\&quot;responseMessage\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(resmsg).append(&apos;\\&quot;&apos;);\n" +
                    "         sb.append(&quot;,\\&quot;sentBytes\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(sby).append(&apos;\\&quot;&apos;);\n" +
                    "                sb.append(&quot;,\\&quot;bytesAsLong\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(rby).append(&apos;\\&quot;&apos;);\n" +
                    "                sb.append(&quot;,\\&quot;urlAsString\\&quot;:\\&quot;&quot;)\n" +
                    "                .append(url).append(&apos;\\&quot;&apos;);\n" +
                    "                sb.append(&quot;,\\&quot;isok\\&quot;:&quot;)\n" +
                    "                .append(isok);\n" +
                    "        sb.append(&apos;}&apos;);\n" +
                    " List l=vars.getObject(&quot;dat&quot;);\n" +
                    " l.add(sb);\n" +
                    " vars.putObject(&quot;dat&quot;,l);\n" +
                    "</stringProp>\n" +
                    "          </BeanShellPostProcessor>\n" +
                    "          <hashTree/>\n";

            String head= h.getHeader();
            if((head.trim().isEmpty()||head.contains("(格式是xxx: (注意此处空格)yyy，完成一条数据再回车)"))&&!isJson){
                re+= "          <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP信息头管理器\" enabled=\"true\">\n" +
                        "            <collectionProp name=\"HeaderManager.headers\"/>\n" +
                        "          </HeaderManager>\n";
            }else {
                re+="\t\t  <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP信息头管理器\" enabled=\"true\">\n" +
                        "            <collectionProp name=\"HeaderManager.headers\">\n";
                String[] h1=head.split("&#xd;");
                for(String h2:h1){
                    String[] h3=h2.split(": ");
                    if(h3[0].contains("(格式是xxx")){
                        break;
                    }
                    re+="              <elementProp name=\"\" elementType=\"Header\">\n" +
                            "                <stringProp name=\"Header.name\">"+h3[0]+"</stringProp>\n" +
                            "                <stringProp name=\"Header.value\">"+h3[1]+"</stringProp>\n" +
                            "              </elementProp>\n";

                }
                if(isJson){
                    re+="              <elementProp name=\"\" elementType=\"Header\">\n" +
                            "                <stringProp name=\"Header.name\">Content-Type</stringProp>\n" +
                            "                <stringProp name=\"Header.value\">application/json;charset=UTF-8</stringProp>\n" +
                            "              </elementProp>\n";
                }
                re+="            </collectionProp>\n" +
                        "          </HeaderManager>\n";
                        }
                        re+= "\t\t  <hashTree/>\n";
                        if(h.getReg().getDef().trim().isEmpty()){
                re+="<RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"正则表达式提取器\" enabled=\"true\">\n" +
                        "            <stringProp name=\"RegexExtractor.useHeaders\">"+h.getReg().getPath().getKey()+"</stringProp>\n" +
                        "            <stringProp name=\"RegexExtractor.refname\">"+h.getReg().getName()+"</stringProp>\n" +
                        "            <stringProp name=\"RegexExtractor.regex\">"+h.getReg().getReg()+"</stringProp>\n" +
                        "            <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
                        "            <stringProp name=\"RegexExtractor.default\"></stringProp>\n" +
                        "            <stringProp name=\"RegexExtractor.match_number\">"+h.getReg().getNum()+"</stringProp>\n"+
                        "\t\t\t <boolProp name=\"RegexExtractor.default_empty_value\">true</boolProp>\n";
                        }else {
                            re+="<RegexExtractor guiclass=\"RegexExtractorGui\" testclass=\"RegexExtractor\" testname=\"正则表达式提取器\" enabled=\"true\">\n" +
                                    "            <stringProp name=\"RegexExtractor.useHeaders\">"+h.getReg().getPath().getKey()+"</stringProp>\n" +
                                    "            <stringProp name=\"RegexExtractor.refname\">"+h.getReg().getName()+"</stringProp>\n" +
                                    "            <stringProp name=\"RegexExtractor.regex\">"+h.getReg().getReg()+"</stringProp>\n" +
                                    "            <stringProp name=\"RegexExtractor.template\">$1$</stringProp>\n" +
                                    "            <stringProp name=\"RegexExtractor.default\">"+h.getReg().getDef()+"</stringProp>\n" +
                                    "            <stringProp name=\"RegexExtractor.match_number\">"+h.getReg().getNum()+"</stringProp>\n";
                        }

                        re+="          </RegexExtractor>\n" +
                                "          <hashTree/>\n" +
                                "        </hashTree>";

                  return re;}
            case 3:return "<HTTPSamplerProxy guiclass=\"HttpTestSampleGui\" testclass=\"HTTPSamplerProxy\" testname=\"HTTP请求\" enabled=\"true\">\n" +
                    "          <boolProp name=\"HTTPSampler.postBodyRaw\">true</boolProp>\n" +
                    "          <elementProp name=\"HTTPsampler.Arguments\" elementType=\"Arguments\">\n" +
                    "            <collectionProp name=\"Arguments.arguments\">\n" +
                    "              <elementProp name=\"\" elementType=\"HTTPArgument\">\n" +
                    "                <boolProp name=\"HTTPArgument.always_encode\">false</boolProp>\n" +
                    "                <stringProp name=\"Argument.value\">${ddd}</stringProp>\n" +
                    "                <stringProp name=\"Argument.metadata\">=</stringProp>\n" +
                    "              </elementProp>\n" +
                    "            </collectionProp>\n" +
                    "          </elementProp>\n" +
                    "          <stringProp name=\"HTTPSampler.domain\">localhost</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.port\">"+webport+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.protocol\">http</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.contentEncoding\">UTF-8</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.path\">/getjmeter/"+resid+"</stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.method\">POST</stringProp>\n" +
                    "          <boolProp name=\"HTTPSampler.follow_redirects\">false</boolProp>\n" +
                    "          <boolProp name=\"HTTPSampler.auto_redirects\">true</boolProp>\n" +
                    "          <boolProp name=\"HTTPSampler.use_keepalive\">false</boolProp>\n" +
                    "          <boolProp name=\"HTTPSampler.DO_MULTIPART_POST\">false</boolProp>\n" +
                    "          <stringProp name=\"HTTPSampler.embedded_url_re\"></stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.connect_timeout\"></stringProp>\n" +
                    "          <stringProp name=\"HTTPSampler.response_timeout\"></stringProp>\n" +
                    "        </HTTPSamplerProxy>\n" +
                    "        <hashTree>\n" +
                    "          <BeanShellPreProcessor guiclass=\"TestBeanGUI\" testclass=\"BeanShellPreProcessor\" testname=\"BeanShell PreProcessor\" enabled=\"true\">\n" +
                    "            <boolProp name=\"resetInterpreter\">false</boolProp>\n" +
                    "            <stringProp name=\"parameters\"></stringProp>\n" +
                    "            <stringProp name=\"filename\"></stringProp>\n" +
                    "            <stringProp name=\"script\">List l=vars.getObject(&quot;dat&quot;);\n" +
                    "vars.put(&quot;ddd&quot;,l.toString());\n" +
                    "System.out.println(&quot;111111111111111111&quot;);</stringProp>\n" +
                    "          </BeanShellPreProcessor>\n" +
                    "          <hashTree/>\n" +
                    "          <HeaderManager guiclass=\"HeaderPanel\" testclass=\"HeaderManager\" testname=\"HTTP信息头管理器\" enabled=\"true\">\n" +
                    "            <collectionProp name=\"HeaderManager.headers\">\n" +
                    "              <elementProp name=\"Content-Type\" elementType=\"Header\">\n" +
                    "                <stringProp name=\"Header.name\">Content-Type</stringProp>\n" +
                    "                <stringProp name=\"Header.value\">application/json</stringProp>\n" +
                    "              </elementProp>\n" +
                    "            </collectionProp>\n" +
                    "          </HeaderManager>\n" +
                    "          <hashTree/>\n" +
                    "        </hashTree>\n" +
                    "\t </hashTree>\n" +
                    "\t </hashTree>\n" +
                    "\t </hashTree>\n" +
                    "</jmeterTestPlan>";
                    default:return "";
        }

    }


    private void  runHttpCase(String resid,int type) throws Exception {

        List<HttpCase> lh=null;
        if (type==2) {
            lh = jdbcTemplate.query("select * from httpcase where cid=(SELECT cid from caseres where id="+resid+")",new BeanPropertyRowMapper<>(HttpCase.class));
        } else {

            lh = jdbcTemplate.query("select * from httpcase where cid=(select a from  precondition where cid=( SELECT cid from caseres where id="+resid+"))",new BeanPropertyRowMapper<>(HttpCase.class));

        }

        List<Http4j> lh4=JSON.parseArray(lh.get(0).getCon().replace("\\&quot","\\\\&quot"),Http4j.class);
        StringBuffer sb=new StringBuffer();
        sb.append(getjm(1,null,null));
        if(lh.size()>0){
            for(Http4j http4j:lh4){
                sb.append(getjm(2,http4j,null));
            }
            sb.append(getjm(3,null,resid));
            mycode.write(filePath+"basetools/run.jmx",sb.toString(),"UTF-8");

//            Process p=   Runtime.getRuntime().exec(aasd,new String[]{filePath+"basetools/run.jmx","-n"});

            Process p1= Runtime.getRuntime().exec("cmd /k  start  "+filePath+"basetools/run.bat");
            int ni=0;
            while ((!ishttpStop||!resid.equals(rid))&&ni<120){
                Thread.sleep(1000);
                ni++;

            }

            p1.destroy();
            p1=null;
            Runtime.getRuntime().exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
            Thread.sleep(1000);
            if(ni>=120){
    throw new Exception("等待超时，请检查");
}
            boolean isok=true;
            for(Http4j http4j:lh4){
                for (int i = 0; i < http4res.length; i++) {
                    boolean isok2=true;

                    if(http4res[i].getName().equals(http4j.getName())){
                        String bu="";
                        if(http4j.getAss().isIsfan()){
                            bu="不";
                        }
                        http4res[i].setRestype(http4j.getAss().getPath().getValue()+bu+http4j.getAss().getType().getValue());
                        http4res[i].setRes(http4j.getAss().getPa());
                        if(!http4j.getAss().isIsigst()){
                            if(!http4res[i].isIsok()){


                                http4res[i].setResid("1");

                                updateCaseresRes("2",Arrays.toString(http4res).replace("\"","\\\"").replace("\n","\\n").replace(" ",""),resid);
                               throw  new MyException("");

                            }
                        }
                        String mubiao="";

                        switch (http4j.getAss().getPath().getKey()){
                            case "1":mubiao=http4res[i].getResponseDataAsString();break;
                            case "2":mubiao=http4res[i].getResponseCode();break;
                            case "3":mubiao=http4res[i].getRequestHeaders();break;
                            case "4":mubiao=http4res[i].getRequestHeaders();break;
                            case "5":mubiao=http4res[i].getUrlAsString();break;
                        }

                        http4res[i].setRes(http4res[i].getRes().replace("&amp;","&").replace("&quot;","%shuang").replace("&lt;","<").replace("&gt;",">").replace("&apos; ","%dan").replace("\\ ","%gang"));
                        switch (http4j.getAss().getType().getKey()){
                            case "1":{String v[]=mubiao.split(http4j.getAss().getPa().replace("&amp;","&").replace("&quot;","%shuang").replace("&lt;","<").replace("&gt;",">").replace("&apos; ","%dan").replace("\\ ","%gang"));
                                if(v.length==1){
                                    isok2=false;
                                }

                                break;}

                            case "2":isok2=mubiao.matches(http4j.getAss().getPa().replace("&amp;","&").replace("&quot;","%shuang").replace("&lt;","<").replace("&gt;",">").replace("&apos; ","%dan").replace("\\ ","%gang"));

                            break;


                        }
                        if(http4j.getAss().isIsfan()){
                            isok2=!isok;
                        }
                        isok=isok2&isok;
                        if(isok2){
                            http4res[i].setResid("2");
                        }else {
                            http4res[i].setResid("3");
                        }
                        break;


                    }
                }

            }
        if(isok){


            updateCaseresRes("1",Arrays.toString(http4res).replace(" ","").replace("&amp;","&").replace("&quot;","\"").replace("&lt;","<").replace("&gt;",">").replace("&apos; ","'").replace("\"","\\\"").replace("\n","\\n"),resid);

        }else {

            updateCaseresRes("2",Arrays.toString(http4res).replace(" ","").replace("&amp;","&").replace("&quot;","\"").replace("&lt;","<").replace("&gt;",">").replace("&apos; ","'").replace("\"","\\\"").replace("\n","\\n"),resid);
            throw  new MyException("");
        }


        }else{
            updateCaseresRes("3","请求数据出错，请检查",resid);
            throw new Exception("请求数据出错，请检查");



        }







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
       ls= jdbcTemplate.query("select id value1,sid value2,type value3 from caseres where listid="+listid+"  order by id",new BeanPropertyRowMapper<>(tmp3.class));
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
        // 创建DesiredCapabilities类的一个对象实例
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("no-sandbox");
        options.addArguments("disable-extensions");
        options.addArguments("no-default-browser-check");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
//
        DesiredCapabilities cap=DesiredCapabilities.chrome();
        Proxy proxy=new Proxy();
        proxy.setHttpProxy("localhost:8102").setSslProxy("localhost:8102");
        cap.setCapability(CapabilityType.ForSeleniumServer.AVOIDING_PROXY, true);
        cap.setCapability(CapabilityType.ForSeleniumServer.ONLY_PROXYING_SELENIUM_TRAFFIC, true);

        // 设置变量ACCEPT_SSL_CERTS的值为True
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        System.setProperty("webdriver.chrome.driver", driverPath);
        cap.setCapability(CapabilityType.PROXY, proxy);
        cap.setCapability(ChromeOptions.CAPABILITY,options);

        WebDriver driver = new ChromeDriver(cap);
        driver.manage().window().maximize();
       // driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

    private Element getElement(String eid ,String vid ){

      return  jdbcTemplate.query("SELECT max(id) aasd,* FROM element where vid<=? and baseid=?",new Object[]{vid,eid},new BeanPropertyRowMapper<>(Element.class)).get(0);
    }

    private void screenShot(WebDriver driver,String resid,String seriesid,String listid ,WebElement element,boolean iserr){
        if(!iserr)
        setYellow(driver,element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            File file=new File(picPath+seriesid+"_"+listid+"_"+resid+"_"+System.currentTimeMillis()+".jpg");
            srcFile.renameTo(file);
            updateCaseresPic(file.getName(),resid);
            if(!iserr)
            setYellow(driver,element);
        } catch (NullPointerException e) {

        }


    }

    private WebElement element2Web(Element element,WebDriver driver,boolean isToWin,String vid) throws NoSuchElementException {
        if(!element.getToframe().equals("-1")){
            driver.switchTo().frame(element2Web(getElement(element.getToframe(),vid),driver,false,vid));
        }
        //0:id;1:name;2:tagname;3:linktext;4:classname;5:xpath;6:css;
        WebElement webElement=null;

            By by=null;
            switch (element.getLocationMethod()){
                case "1" :by=By.id(element.getValue());break;
                case "2" :by=By.name(element.getValue());break;
                case "3" :by=By.tagName(element.getValue());break;
                case "4" :by=By.linkText(element.getValue());break;
                case "5" :by=By.className(element.getValue());break;
                case "6" :by=By.xpath(element.getValue().replace("%78","\""));break;
                case "7" :by=By.cssSelector(element.getValue());break;

            }
            webElement=waitTime(element,by,element.getNum(),driver,isToWin);



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

  private WebElement waitTime(Element element,By by,String num,WebDriver driver,boolean isWin)   {
        boolean isS=false;
        int ne=0;
        if(!num.equals("-1")){
            isS=true;
        }
        WebElement element1=null;
        while (true){
            try {
                if(element.getWaitid().equals("2")){

                    if(isS){
                        element1=   driver.findElements(by).get(Integer.parseInt(num));

                    }else {
                        element1=driver.findElement(by);
                    }

                    try {
                        waitElementExist(element1).getLocation();
                    } catch (InvalidElementStateException e) {
                        element1.getText();
                    }
                    break;

                }else {
                    if (element.getWaitid().equals("1")){
                        try {
                            Thread.sleep(Integer.parseInt(element.getWaitvalue()));
                        } catch (InterruptedException e) {
                            //e.printStackTrace();
                            Thread.interrupted();
                        }
                    }
                    if(isS){
                        element1=   driver.findElements(by).get(Integer.parseInt(num));

                    }else {
                        element1=driver.findElement(by);
                    }
                    try {
                        element1.getLocation();
                    } catch (InvalidElementStateException e) {
                        element1.getText();
                    }
                    break;

                }
            } catch (NumberFormatException e) {
                break;

            } catch (NoSuchElementException e) {
                if(ne>40){
                    throw new NoSuchElementException("no such element: Unable to locate element and wait for 30s: {\"method\":\""+getActionName(element.getLocationMethod())+"\",\"selector\":\""+element.getValue().replace("%78","\"")+"\"}");
                }
                if(isWin&&ne>3){
                    throw new NoSuchElementException("no such element: Unable to locate element and wait for 3s: {\"method\":\""+getActionName(element.getLocationMethod())+"\",\"selector\":\""+element.getValue().replace("%78","\"")+"\"}");

                }
                ne++;
                driver=driver.switchTo().window(driver.getWindowHandle());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    //e1.printStackTrace();
                }


            }catch (StaleElementReferenceException e){
                if(ne>40){
                    throw new StaleElementReferenceException("stale element reference and wait for 30s: {\"method\":\""+getActionName(element.getLocationMethod())+"\",\"selector\":\""+element.getValue().replace("%78","\"")+"\"}");
                }
                if(isWin&&ne>3){
                    throw new StaleElementReferenceException("stale element reference and wait for 3s: {\"method\":\""+getActionName(element.getLocationMethod())+"\",\"selector\":\""+element.getValue().replace("%78","\"")+"\"}");

                }
                driver=driver.switchTo().window(driver.getWindowHandle());

                ne++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    //e1.printStackTrace();
                }
            }

        }



      return element1;

  }


  private Object action(WebElement webElement,String catid,WebDriver driver,String value){
      try {
          Thread.sleep(100);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

      switch (catid){
              case "1": click(driver,webElement);return 0;
              case "2": webElement.clear();webElement.sendKeys(value);return 0;

              case "3": webElement.clear();return 0;
              case "4":List<tmp> lt= jdbcTemplate.query("select path value from file where id="+value,new BeanPropertyRowMapper<>(tmp.class));

                  webElement.sendKeys(lt.get(0).getValue());return 0;

              case "5": driver.switchTo().alert().accept();return 0;
              case "6": driver.switchTo().alert().dismiss();return 0;
             // case "8": return driver.switchTo().alert().getText();
              //case "9": webElement.click();return true;
             // case "10": webElement.click();return true;


              case "7": return exist(webElement);
              case "8": return webElement.getText();
//                case "14": driver.navigate().to(value);return 0;
//                case "15": driver.navigate().back();return 0;
              case "9": return webElement.getAttribute("value");
              case "10": return driver.switchTo().alert().getText();

              case "11": return webElement.isEnabled();
              case "12": return webElement.isSelected();
              default:return 0;
          }

  }

  private String getOneAction(String catid){
      switch (catid){
//          case "2":return "是否可用";
//          case "4":return "是否选中";
//          case "5":return "获得文本";
//          case "13":return "是否存在";
//          case "17":return "获得文本";
//          case "18":return "获得输入值";
          case "1" :return "点击";
          case "2" :return "输入内容";
          case "3" :return "清除";
          case "4" :return "上传文件";
          case "5" :return "确认（alert）";
          case "6" :return "取消（alert）";


          case "7" :return "存在";
          case "8" :return "获取文本";
          case "9" :return "获取输入值";
          case "10" :return "获取文本（alert）";
          case "11" :return "可用";
          case "12" :return "被选中";

          default:return "操作";

      }
  }

private void click(WebDriver driver,WebElement elemnet){
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();",elemnet);
}
    private void setYellow(WebDriver driver,WebElement elemnet){

        try {
            ((JavascriptExecutor) driver).executeScript("   var rgb=arguments[0].style.backgroundColor;" +

                    "if(rgb=='yellow'){arguments[0].style.backgroundColor =''}else{arguments[0].style.backgroundColor = \"yellow\";}" +

                    "",elemnet);
        } catch (Exception e) {

        }
    }


private boolean exist(WebElement webElement){
    try {
        webElement.getTagName();
        return true;
    } catch (Exception e) {
        return false;
    }
}



    public WebElement toWindow(WebDriver driver,String topage,Element element,String vid) {
        String title=getTitle(topage);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
        final String[] aa = {driver.getWindowHandle()};
        if(title.substring(0,2).equals("..")||title.substring(0,2).equals("。。")){

            driver=  driver.switchTo().window(aa[0]);
            return element2Web(element, driver,false,vid);
        }
        Set<String> set= driver.getWindowHandles();

        if(set.size()==1){
            driver=  driver.switchTo().window(aa[0]);
            return element2Web(element, driver,false,vid);
        }


        for (String s1:set) {

            WebDriver driver1= driver.switchTo().window(s1);
            //System.out.println(driver1.getCurrentUrl());
            if(driver1.getTitle().equalsIgnoreCase(title)){

                if(exist(element2Web(element, driver1,true,vid))){
                    aa[0] =s1;
                    break;

                }

            }else {

                if(exist(element2Web(element, driver1,true,vid))){
                    aa[0] =s1;
                    break;

                }
            }



            
        }



             driver=  driver.switchTo().window(aa[0]);
        return element2Web(element, driver,false,vid);

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
