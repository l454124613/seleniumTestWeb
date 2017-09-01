package com.ciic.test.controller;

import com.ciic.test.bean.*;
import com.ciic.test.service.*;
import com.ciic.test.tools.mycode;
import com.sun.net.httpserver.HttpsConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/4.
 */
@RestController
@EnableAutoConfiguration
public class Urls {
    @Autowired
    private UserService userService;
@Autowired
    private ItemService itemService;
@Autowired
private GetPageService getPageService;

@Autowired
private CaseService caseService;
@Autowired
private ConfigService configService;

private Map<String,Thread> map4thread=new HashMap();

    @RequestMapping("/login" )
    String login(HttpSession session,String user) throws UnsupportedEncodingException {

        String us= mycode.decode(URLDecoder.decode(user,"utf8"));
        //System.out.println(us);
     String[] us2=us.split("!!!");


        if(us2.length!=2||us2[0].isEmpty()||us2[1].isEmpty()){
            return "{\"isok\":2,\"msg\":\"failed\",\"to\":\"\"}";
        }else {
            int isok= userService.isUsingUser(us2[0],us2[1]);
            if(isok==1){
             List<user> lu= userService.selectUser(us2[0],us2[1]);
                session.setAttribute(lu.get(0).getId(),us2[0]);
                return "{\"isok\":0,\"msg\":\"success\",\"to\":\"/html/context.html\",\"user\":\""+mycode.encode(lu.get(0).getName()+" ???"+lu.get(0).getEmail()+" ???"+lu.get(0).getIsmanager())+"\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
            }
        }

    }

    @RequestMapping("/getuser" )
    String getuser(HttpSession session){
      String u2=  session.getAttributeNames().nextElement();

              if(userService.isManager(u2)){
                  return "{\"isok\":0,\"msg\":\"success\",\"to\":\"/\",\"users\":"+userService.getUser()+"}";
              }else {
                  return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
              }



    }


    @RequestMapping("/additem" )
    String additem(HttpSession session,String name,String url,String users,String type){
        if(userService.isManager(session.getAttributeNames().nextElement()) &&!name.isEmpty()&&!url.isEmpty()&&!type.isEmpty()){

            if(type.equalsIgnoreCase("0")){
                int a1= itemService.addItem(name,url);
                int a2=itemService.getmaxitemid();
                String[] u2=users.split(",");
                if(a2>0&&u2.length>0){
                    int num4a=0;
                    for (int i = 0; i < u2.length; i++) {
                       num4a+= itemService.addItemUser(u2[i],a2+"");
                    }
                    if(num4a!=u2.length){
                           return "{\"isok\":1,\"msg\":\"添加用户操作失败\",\"to\":\"/\"}";
                    }

                }
                if(a1==1&&a2>0){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String aa= mycode.getTitle(url);
                            itemService.addtitle(aa);

                        }
                    }).start();
                    return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
                }else {
                    return "{\"isok\":1,\"msg\":\"添加项目操作失败\",\"to\":\"/\"}";
                }
            }else {
              int a=  itemService.updateItem(name,url,type);
             int a2= itemService.removeItemUser(type);
                String[] u2=users.split(",");
                int num4a=0;
                for (int i = 0; i < u2.length; i++) {
                    num4a+=   itemService.addItemUser(u2[i],type);
                }
                if(num4a!=u2.length){
                    return "{\"isok\":1,\"msg\":\"添加用户操作失败\",\"to\":\"/\"}";
                }
                if (a==1){

                    return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
                }else {
                    return "{\"isok\":1,\"msg\":\"修改项目操作失败\",\"to\":\"/\"}";
                }


            }




        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }
    }



    @RequestMapping("/adduser" )
    String adduser(HttpSession session,String name,String email,String ismanager,String type,String ispass){
        String u2=  session.getAttributeNames().nextElement();
        if(userService.isManager(u2)&&!name.isEmpty()&&!email.isEmpty()&&!ismanager.isEmpty()&&!type.isEmpty()){
            int aa=0;
            if(type.equalsIgnoreCase("0")){
                aa=    userService.adduser(name,email,"123456",ismanager);
            }else {
                if(ispass.equalsIgnoreCase("1")){
                    aa=userService.updateUser(name,email,"123456",ismanager,type);
                }else {
                    aa=userService.updateUserNopass(name,email,ismanager,type);
                }

            }

        if(aa==1){
            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else

            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";


        }else




        return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
    }

    @RequestMapping("/removeuser/{id}" )
    String removeuser(HttpSession session,@PathVariable String id){
        String u2=  session.getAttributeNames().nextElement();
        if(userService.isManager(u2)){
            int aa=    userService.jinyongUser(id);
            if(aa==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else

                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";


        }else




            return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
    }


    @RequestMapping("/removeitem/{id}" )
    String removeitem(HttpSession session,@PathVariable String id){
        String u2=  session.getAttributeNames().nextElement();
        if(userService.isManager(u2)){
            int aa=    itemService.removeItem(id);
            if(aa==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else

                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";


        }else




            return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
    }



    @RequestMapping("/test" )
    String test(String cid){
caseService.test(cid);

        return "ok";
    }

    @RequestMapping("/logout")
    String logout(HttpSession session) throws IOException {
       // System.out.println(session.getAttributeNames().nextElement());
        session.removeAttribute(session.getAttributeNames().nextElement());

     //   response.sendRedirect("");
        return "{\"isok\":0,\"msg\":\"登出成功\",\"to\":\"/\"}";

    }
    @RequestMapping("/com")
    String comment(){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\"}";
    }
    @RequestMapping("/gitem")
    String getItem(HttpSession session){

        List<item> li= itemService.getItem(session.getAttributeNames().nextElement());
        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"items\":"+li+"}";



    }

    @RequestMapping("/gitema")
    String getAllItem(HttpSession session){
        if(userService.isManager(session.getAttributeNames().nextElement())){

            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\",\"items\":"+itemService.getAllItem()+"}";
        }else {

            return "{\"isok\":1,\"msg\":\"信息不对称\",\"to\":\"/\"}";
        }







    }

    @RequestMapping("/gpage/{item}")
    String getPage(@PathVariable String item,HttpSession session){
        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)){
            List<Page> lp=itemService.getPage(item);
            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"pages\":"+lp+"}";

        }else {
            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";

        }



    }


    @RequestMapping("/gele/{page}/{item}")
    String getEle(@PathVariable String page,@PathVariable String item,HttpSession session){
        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)&&getPageService.isOwnPage(page,item)){
           // List<Page> lp=itemService.getPage(page);
            List<Element> le=null;
            if(page.equals("-1")){
                le=getPageService.getall(item);

            }else {
                le=  getPageService.get(page);
            }

            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"elements\":"+le+"}";

        }else {
            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";

        }}

    @RequestMapping(value = "/addpage")
    String addPage(String item,String pagename,String pagetitle,String type){
        if(item.isEmpty()||pagename.isEmpty()||pagetitle.isEmpty()||type.isEmpty()){
            return "{\"isok\":1,\"msg\":\"信息不能为空\",\"to\":\"/\"}";
        }else {
            int a=0;
            if(type.equalsIgnoreCase("0")){
                a= getPageService.addPage(item,pagename,pagetitle);
            }else {
a=getPageService.updatePageInfoById(item,type,pagename,pagetitle);
            }


               if(a==1){
                    return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\"}";
               }else {
                   return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
               }
        }

    }
    @RequestMapping(value = "/addele")
    String addEle(String item,String elename,String eletype,String type,String elelo,String elevalue,String pid,String topage,String toframe,String waitv,String waitid,HttpSession session){

        if(item.isEmpty()||elename.isEmpty()||eletype.isEmpty()||type.isEmpty()||elelo.isEmpty()||elevalue.isEmpty()||pid.isEmpty()||topage.isEmpty()||toframe.isEmpty()||waitid.isEmpty()||waitv.isEmpty()||!getPageService.isOwnPage(pid,item)){
            return "{\"isok\":1,\"msg\":\"信息不能为空\",\"to\":\"/\"}";
        }else {
            Element element=new Element();
            element.setLocationMethod(elelo);
            element.setName(elename);
            element.setTopage(topage);
            element.setType(eletype);
            element.setValue(elevalue);
            element.setToframe(toframe);
            element.setWaitid(waitid);
            element.setWaitvalue(waitv);
            int a=0;
            if(type.equalsIgnoreCase("0")){
                a= getPageService.addEle(element,session.getAttributeNames().nextElement(),pid);
            }else {
                a=getPageService.updateEle(element,session.getAttributeNames().nextElement(),type);
            }


            if(a==1){
                return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }
        }

    }



    @RequestMapping("/removepage/{page}/{item}")
    String removePage(@PathVariable String page,HttpSession session,@PathVariable String item){
        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)&&getPageService.isOwnPage(page,item)){
            int a=getPageService.removePage(page);
            if(a==1){
                return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"删除成功\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
            }


        }else {
            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";

        }
    }

    @RequestMapping("/removeele/{ele}/{page}/{item}")
    String removeEle(@PathVariable String page,HttpSession session,@PathVariable String item,@PathVariable String ele){
        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)&&getPageService.isOwnPage(page,item)&&getPageService.isOwnEle(page,ele)){
            int a=getPageService.removeEle(ele);
            if(a==1){
                return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"删除成功\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
            }


        }else {
            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";

        }
    }

    @RequestMapping("/getlabel/{tid}")
    String getLabel(@PathVariable String tid){
        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"labels\":"+configService.getLabel(tid)+"}";//",\"res\":" + caseService.getCaseresNum(seriesid)+
    }
    @RequestMapping("/getusedlabel/{tid}")
    String getUsedLabel(@PathVariable String tid){
        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"labels\":"+configService.getUsedLabel(tid)+"}";//",\"res\":" + caseService.getCaseresNum(seriesid)+
    }


    //case
    @RequestMapping("/getseriesandcase/{seriesid}")
    String getSeriesAndCase(@PathVariable String seriesid){
        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+caseService.getCaseresList(seriesid)+",\"series\":" + caseService.getFinishSeries(seriesid) + ",\"res\":" + caseService.getCaseres(seriesid) +"}";//",\"res\":" + caseService.getCaseresNum(seriesid)+
    }


    @RequestMapping("/getcase/{tid}")
String getcase(@PathVariable String tid){
        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+caseService.getcase(tid)+"}";
    }

    @RequestMapping("/getcasereslist/{seriesid}")
    String getCasereslist(@PathVariable String seriesid){
        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+caseService.getCaseresList(seriesid)+"}";
    }

    @RequestMapping("/getstep/{cid}")
    String getstep(@PathVariable String cid){
        List<Step> ls =itemService.getStep(cid);

          return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"steps\":"+ls+"}";

    }

    @RequestMapping("/getdatasource/{tid}")
    String getDatasource(@PathVariable String tid){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"datasources\":"+configService.getDatasource(tid)+"}";

    }


    @RequestMapping("/getexp/{sid}")
    String getExp(@PathVariable String sid){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"exp\":"+caseService.getExpected(sid)+"}";

    }


    @RequestMapping("/getprecondition/{cid}")
    String getPrecondition(@PathVariable String cid){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"pre\":"+caseService.getPrecondition(cid)+"}";

    }



    @RequestMapping("/updateexp")
    String updateExp(String type,String sid,String a,String b,String c,String d,String e){
        if(type.isEmpty()||sid.isEmpty()){
            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
        }else {


            int  n   = caseService.updateExp(type,sid,a,b,c,d,e);



            if(n==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }

        }



    }

    @RequestMapping("/updatepre")
    String updatePrecondition(String type,String cid,String a,String b,String c){
        if(type.isEmpty()||cid.isEmpty()){
            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
        }else {


            int  n   = caseService.updatePrecondition(type,cid,a,b,c);



            if(n==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }

        }



    }


    @RequestMapping("/getdatasourceconnect/{did}")
    String getDatasourceConnect(@PathVariable String did){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"con\":\""+configService.connectDatasource(did).replace("\"","\\\"")+"\"}";

    }
    @RequestMapping("/getseries/{tid}")
    String getSeries(@PathVariable String tid, HttpSession session){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"series\":"+caseService.getSeries(tid,session.getAttributeNames().nextElement())+"}";

    }


    @RequestMapping("/getpid/{sid}")
    String getpid(@PathVariable String sid){
        String value=caseService.getPid(sid);
        if(value.isEmpty()){
            return "{\"isok\":1,\"msg\":\"查找pid失败\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"pid\":"+value+"}";
        }

    }

    @RequestMapping("/gettopage/{sid}")
    String gettopage(@PathVariable String sid){
        String value=caseService.getTopage(sid);
        if(value.isEmpty()){
            return "{\"isok\":1,\"msg\":\"查找页面失败\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"topage\":"+value+"}";
        }

    }
    @RequestMapping("/getcasehome/{tid}")
    String getCaseHome(@PathVariable String tid){


            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"casehomes\":"+ caseService.getCaseHome(tid)+"}";


    }
    @RequestMapping("/gethttpcase/{cid}")
    String getHttpCase(@PathVariable String cid){


        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"case\":"+ caseService.getHttpCase(cid)+"}";


    }


    @RequestMapping("/testcase/{cid}/{tid}/{name}")
    String testCase(@PathVariable String cid,@PathVariable String tid,@PathVariable String name,HttpSession session){
        int n=0;
        List<Series> ls=    caseService.getOneSeries(name+"调试",tid);

        if(ls.size()==0){
         n=   caseService.addRunCase(name+"调试",cid,tid,"1",session.getAttributeNames().nextElement());

        }else{
            boolean hasCid=false;



            for (int i = 0; i <ls.size() ; i++) {
                if(ls.get(i).getCids().equals(cid)){
                    hasCid=true;

                }

            }
            if(hasCid){
                return "{\"isok\":1,\"msg\":\"已有相同调试在运行或等待运行，请在进度中查看\",\"to\":\"/\"}";
            }else {
                n=   caseService.addRunCase(name+"调试",cid,tid,"1",session.getAttributeNames().nextElement());


            }

        }






        if(n==1){
                   if(map4thread.containsKey(tid)){
                       if(map4thread.get(tid).isAlive()){

                       }else {
                           map4thread.put(tid, caseService.startrun(tid));
                           map4thread.get(tid).start();
                       }

                   }else {
                       map4thread.put(tid, caseService.startrun(tid));
                       map4thread.get(tid).start();
                   }






            return "{\"isok\":0,\"msg\":\"操作成功,请稍后查看结果\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }




    }


        @RequestMapping("/addseries")
    String addSeries( String cids,String tid,String seriesName){
      int n=  caseService.addRunCase(seriesName,cids,tid,"2","0");


            if(n==1){


                return "{\"isok\":0,\"msg\":\"操作成功,请在进度页面执行\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }



    }



    @RequestMapping("/removecasehome/{chid}")
    String removeCaseHome( @PathVariable String chid){
        int n=  caseService.removeCaseHome(chid);


        if(n==1){


            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }



    }

    @RequestMapping("/updatecids")
    String updateCids(  String id,String cids){
        int n=  caseService.updateCaseHome4cids(cids,id);


        if(n==1){


            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }



    }

    @RequestMapping("/addcasehome")
    String addCaseHome( String name,String des,String tid,String type){
        int n=0;
        if(name.isEmpty()||tid.isEmpty()||type.isEmpty()){
            return "{\"isok\":1,\"msg\":\"数据信息不全\",\"to\":\"/\"}";
        }else {
            if(type.equals("0")){
                n=  caseService.addCaseHome(name,des,tid);
            }else {
                n=caseService.updateCaseHome(type,name,des);
            }
        }





        if(n==1){

            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }



    }

    @RequestMapping("/addlabel")
    String addLabel( String name,String des,String tid,String type){
        int n=0;
        if(name.isEmpty()||tid.isEmpty()||type.isEmpty()){
            return "{\"isok\":1,\"msg\":\"数据信息不全\",\"to\":\"/\"}";
        }else {
            if(type.equals("0")){
                n=  configService.addLabel(name,des,tid);
            }else {
                n=configService.updateLabel(type,name,des);
            }
        }





        if(n==1){

            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }



    }



//
//    @RequestMapping("/looktestcase/{cid}")
//    String lookTestCase(@PathVariable String cid){
//
//
//    //    caseService.
//        if( caseService.isRuningOneCase(cid)){
//            return "{\"isok\":4,\"msg\":\"当前用例正在运行，请稍后再试~\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"res\":"+caseService.getOnecase(cid).size()+"}";
//
//        }
//
//
//
//    }


    @RequestMapping("/clearisused")
    String clearIsused(String mimi,HttpSession session){
        if(userService.isManager(session.getAttributeNames().nextElement())){

            long a=System.currentTimeMillis()/1000;
            String b=mycode.decode(mimi);
            long c= 0;
            try {
                c = Long.parseLong(b);
            } catch (Exception e) {
                return "{\"isok\":1,\"msg\":\"时间不对等，请不要修改url内容\",\"to\":\"/\"}";
            }
            if(Math.abs(a-c)>50){
                return "{\"isok\":1,\"msg\":\"时间不对等，请不要修改url内容\",\"to\":\"/\"}";
            }else {
int num=configService.clearisused();

                return "{\"isok\":0,\"msg\":\"完成操作,删除"+num+"条数据。\",\"to\":\"/\"}";
            }

        }else {
            return "{\"isok\":1,\"msg\":\"没有权限操作\",\"to\":\"/\"}";
        }



    }

    @RequestMapping("/ge4p/{id}/{tid}/{cid}")
    String geteleforpage(@PathVariable String id,@PathVariable String tid,@PathVariable String cid){
        if(id.equalsIgnoreCase("0")){
           String a= caseService.getPid4Case(cid);

           if(a.equals("0")){
               String title=   itemService.gerTitle(tid);
               if(title.equalsIgnoreCase("err")){
                   return "{\"isok\":1,\"msg\":\"项目首页链接出错,请检查。\",\"to\":\"/\"}";
               }else {
                   List<Page> lp=  itemService.getOnePage(tid,title);
                   if(lp.size()==1){
                       return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"page\":"+lp.get(0)+",\"eles\":"+itemService.getele4page(lp.get(0).getId())+"}";

                   }else {
                       return "{\"isok\":1,\"msg\":\"获取页面失败。\",\"to\":\"/\"}";
                   }




               }
           }else {
               return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"page\":{\"id\":\"" + a + "\"},\"eles\":"+itemService.getele4page(a)+"}";

           }



        }else {
            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"page\":{\"id\":\""+id+"\"},\"eles\":"+getPageService.get(id)+"}";

        }


       // return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"steps\":"+""+"}";

    }

    @RequestMapping("/addstep")
    String addstep( String step,String type,String catid,String cid,String value,String eid,String ename){
if(step.isEmpty()||type.isEmpty()||catid.isEmpty()||cid.isEmpty()||eid.isEmpty()||ename.isEmpty()){
    return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
}else {


    int  a   =  itemService.addStep(step,type,catid,cid,value,eid,ename);
    new Thread(new Runnable() {
        @Override
        public void run() {
            caseService.zhengliStep(cid);
        }
    }).start();



    if(a==1){
        return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
    }else {
        return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
    }

}}



    @RequestMapping("/fixstep")
    String fixstep( String id,String type,String catid,String value,String eid,String ename){
        if(id.isEmpty()||type.isEmpty()||catid.isEmpty()||eid.isEmpty()||ename.isEmpty()){
            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
        }else {


            int  a   =  itemService.updateStep(id,type,catid,value,eid,ename);



            if(a==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }

        }}


    @RequestMapping("/upatelabel")
    String updateLabel( String id,String labels){
        if(id.isEmpty()||labels.isEmpty()){
            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
        }else {


            int  a   = caseService.updateLabel(id,labels);



            if(a==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }

        }}


    @RequestMapping("/upatehttp")
    String updateHttp( String type,String url,String con,String eq,String value,String cid){
        if(cid.isEmpty()||type.isEmpty()||url.isEmpty()||eq.isEmpty()||value.isEmpty()){
            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
        }else {


            int  a   = caseService.updateHttpCase(type,url,con,eq,value,cid);



            if(a==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }

        }}


@RequestMapping("/addcase")
String addcase(String name,String des,String important,String type,String tid,String elety){
       if(name.isEmpty()||important.isEmpty()||des.isEmpty()||type.isEmpty()||tid.isEmpty()||elety.isEmpty()){
           return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
       }else {
           int a=0;
           if(type.equalsIgnoreCase("0")){
             a=  caseService.addCase(name,des,important,tid,elety);

           }else {
               a=caseService.updatecase(type,name,des,important);

           }

           if(a==1){
               return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
           }else {
               return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
           }


       }
}

    @RequestMapping("/adddatasource")
    String addDatasource(String name,String des,String type,String link,String dataname,String user,String pass,String tid,String id){
        if(name.isEmpty()||type.isEmpty()||tid.isEmpty()||link.isEmpty()||dataname.isEmpty()||user.isEmpty()||pass.isEmpty()||id.isEmpty()){
            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
        }else {
int a=0;
if(id.equalsIgnoreCase("0")){
    a=configService.addDataspource(name,des,type,link,dataname,user,pass,tid);
}else {
    a=configService.updateDatasource(id,name,des,type,link,dataname,user,pass);
}



            if(a==1){
                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
            }


        }
    }







    @RequestMapping("/removecase/{cid}")
    String removeCase(@PathVariable String cid){
       int a= caseService.removeCase(cid);

        if(a==1){
            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
        }

    }

    @RequestMapping("/removelabel/{lid}")
    String removeLabel(@PathVariable String lid){
        int a= configService.removeLabel(lid);

        if(a==1){
            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
        }

    }

    @RequestMapping("/removeseries/{seid}")
    String removeSeries(@PathVariable String seid){
        int a= caseService.removeSeries(seid);

        if(a==1){
            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
        }

    }
    @RequestMapping("/runseries/{seid}/{tid}")
    String runSeries(@PathVariable String seid,@PathVariable String tid){
        int a= caseService.updateOneseriesStatus("1","",seid);

        if(map4thread.containsKey(tid)){
            if(map4thread.get(tid).isAlive()){

            }else {
                map4thread.put(tid, caseService.startrun(tid));
                map4thread.get(tid).start();
            }

        }else {
            map4thread.put(tid, caseService.startrun(tid));
            map4thread.get(tid).start();
        }

        if(a==1){
            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
        }

    }
    @RequestMapping("/stopseries/{seid}/{tid}")
    String stopSeries(@PathVariable String seid,@PathVariable String tid){

        try {
            if(map4thread.get(tid).isAlive()){
                caseService.stopRun(seid);
                return "{\"isok\":0,\"msg\":\"操作成功，强制中止中\",\"to\":\"/\"}";
            }else {
                return "{\"isok\":1,\"msg\":\"操作失败，已经停止或暂时无法停止\",\"to\":\"/\"}";
            }
        } catch (NullPointerException e) {

            return "{\"isok\":1,\"msg\":\"操作失败，运行出现错误，请修改数据库\",\"to\":\"/\"}";
        }


    }
    @RequestMapping("/pauseseries/{seid}/{tid}")
    String pauseSeries(@PathVariable String seid,@PathVariable String tid){
        int a= caseService.updateOneseriesStatus("0","",seid);


        if(a==1){
            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"操作失败,正好轮到运行\",\"to\":\"/\"}";
        }

    }



    @RequestMapping("/removedatasource/{did}")
    String removeDatasource(@PathVariable String did){
        int a= configService.removeDatasource(did);

        if(a==1){
            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
        }

    }

    @RequestMapping("/removestep/{sid}")
    String removestep(@PathVariable String sid){
        int a= caseService.removeStep(sid);

        if(a==1){
            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
        }else {
            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
        }

    }



}
