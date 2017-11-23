//package com.ciic.test.controller;
//
//import com.ciic.test.bean.*;
//import com.ciic.test.rest.RestResultResponse;
//import com.ciic.test.service.*;
//import com.ciic.test.tools.mycode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.*;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by lixuecheng on 2017/7/4.
// */
//@RestController
//@EnableAutoConfiguration
//@RequestMapping("/t2/" )
//public class Urls2 {
//    @Autowired
//    private UserService userService;
//@Autowired
//    private ItemService itemService;
//@Autowired
//private GetPageService getPageService;
//
//@Autowired
//private CaseService caseService;
//@Autowired
//private ConfigService configService;
//
//
//@Value("${test.file.path}")
//private String filePath;
//
//private Map<String,Thread> map4thread=new HashMap();
//
//
//    /**
//     *
//     * @param session
//     * @param user
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    @RequestMapping("/login" )
//    String login(HttpSession session,String user) throws UnsupportedEncodingException {
//
//        String us= mycode.decode(URLDecoder.decode(user,"utf8"));
//        //System.out.println(us);
//     String[] us2=us.split("!!!");
//
//
//        if(us2.length!=2||us2[0].isEmpty()||us2[1].isEmpty()){
//            return "{\"isok\":2,\"msg\":\"failed\",\"to\":\"\"}";
//        }else {
//            int isok= userService.isUsingUser(us2[0],us2[1]);
//            if(isok==1){
//             List<user> lu= userService.selectUser(us2[0],us2[1]);
//                session.setAttribute(lu.get(0).getId(),us2[0]);
//                return "{\"isok\":0,\"msg\":\"success\",\"to\":\"/html/context.html\",\"user\":\""+mycode.encode(lu.get(0).getName()+" ???"+lu.get(0).getEmail()+" ???"+lu.get(0).getIsmanager())+"\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
//            }
//        }
//
//    }
//
//    /**
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping("/getuser" )
//    String getuser(HttpSession session){
//      String u2=  session.getAttributeNames().nextElement();
//
//              if(userService.isManager(u2)){
//                  return "{\"isok\":0,\"msg\":\"success\",\"to\":\"/\",\"users\":"+userService.getUser()+"}";
//              }else {
//                  return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
//              }
//    }
//
//    /**
//     *
//     * @param session
//     * @param name
//     * @param url
//     * @param users
//     * @param type
//     * @return
//     */
//    @RequestMapping("/additem" )
//    String additem(HttpSession session,String name,String url,String users,String type){
//        if(userService.isManager(session.getAttributeNames().nextElement()) &&!name.isEmpty()&&!url.isEmpty()&&!type.isEmpty()){
//
//            if(type.equalsIgnoreCase("0")){
//                int a1= itemService.addItem(name,url);
//                int a2=itemService.getmaxitemid();
//                String[] u2=users.split(",");
//                if(a2>0&&u2.length>0){
//                    int num4a=0;
//                    for (int i = 0; i < u2.length; i++) {
//                       num4a+= itemService.addItemUser(u2[i],a2+"");
//                    }
//                    if(num4a!=u2.length){
//                           return "{\"isok\":1,\"msg\":\"添加用户操作失败\",\"to\":\"/\"}";
//                    }
//
//                }
//                if(a1==1&&a2>0){
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String aa= mycode.getTitle(url);
//                            itemService.addtitle(aa);
//
//                        }
//                    }).start();
//                    return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//                }else {
//                    return "{\"isok\":1,\"msg\":\"添加项目操作失败\",\"to\":\"/\"}";
//                }
//            }else {
//              int a=  itemService.updateItem(name,url,type);
//             int a2= itemService.removeItemUser(type);
//                String[] u2=users.split(",");
//                int num4a=0;
//                for (int i = 0; i < u2.length; i++) {
//                    num4a+=   itemService.addItemUser(u2[i],type);
//                }
//                if(num4a!=u2.length){
//                    return "{\"isok\":1,\"msg\":\"添加用户操作失败\",\"to\":\"/\"}";
//                }
//                if (a==1){
//
//                    return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//                }else {
//                    return "{\"isok\":1,\"msg\":\"修改项目操作失败\",\"to\":\"/\"}";
//                }
//
//
//            }
//
//
//
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//    }
//
//
//    /**
//     *
//     * @param session
//     * @param name
//     * @param email
//     * @param ismanager
//     * @param type
//     * @param ispass
//     * @return
//     */
//    @RequestMapping("/adduser" )
//    String adduser(HttpSession session,String name,String email,String ismanager,String type,String ispass){
//        String u2=  session.getAttributeNames().nextElement();
//        if(userService.isManager(u2)&&!name.isEmpty()&&!email.isEmpty()&&!ismanager.isEmpty()&&!type.isEmpty()){
//            int aa=0;
//            if(type.equalsIgnoreCase("0")){
//                aa=    userService.adduser(name,email,"123456",ismanager);
//            }else {
//                if(ispass.equalsIgnoreCase("1")){
//                    aa=userService.updateUser(name,email,"123456",ismanager,type);
//                }else {
//                    aa=userService.updateUserNopass(name,email,ismanager,type);
//                }
//
//            }
//
//        if(aa==1){
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else
//
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//
//
//        }else
//
//
//
//
//        return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
//    }
//
//    /**
//     *
//     * @param session
//     * @param id
//     * @return
//     */
//    @RequestMapping("/removeuser/{id}" )
//    String removeuser(HttpSession session,@PathVariable String id){
//        String u2=  session.getAttributeNames().nextElement();
//        if(userService.isManager(u2)){
//            int aa=    userService.jinyongUser(id);
//            if(aa==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else
//
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//
//
//        }else
//
//
//
//
//            return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
//    }
//
//    /**
//     *
//     * @param session
//     * @param id
//     * @return
//     */
//    @RequestMapping("/removeitem/{id}" )
//    String removeitem(HttpSession session,@PathVariable String id){
//        String u2=  session.getAttributeNames().nextElement();
//        if(userService.isManager(u2)){
//            int aa=    itemService.removeItem(id);
//            if(aa==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else
//
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//
//
//        }else
//
//
//
//
//            return "{\"isok\":1,\"msg\":\"failed\",\"to\":\"/\"}";
//    }
//
//    /**
//     *
//     * @param res
//     * @param id
//     * @param name
//     * @param ddf
//     */
//    @RequestMapping(value = "/Download/{id}/{name}/{ddf}", method = RequestMethod.GET)
//     void testDownload(HttpServletResponse res,@PathVariable String id,@PathVariable String name,@PathVariable String ddf) {
//        List<tmp> lt=configService.getOneFile(id,name);
//        if(lt.size()==1&&ddf.equals("filename")){
//            res.setHeader("content-type", "application/octet-stream");
//            res.setContentType("application/octet-stream");
//            try {
//                res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name,"UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            byte[] buff = new byte[1024];
//            BufferedInputStream bis = null;
//            OutputStream os = null;
//            try {
//                os = res.getOutputStream();
//                bis = new BufferedInputStream(new FileInputStream(new File(lt.get(0).getValue())));
//                int i = bis.read(buff);
//                while (i != -1) {
//                    os.write(buff, 0, buff.length);
//                    os.flush();
//                    i = bis.read(buff);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (bis != null) {
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//           // return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//
//
//        }else {
//           // return "{\"isok\":1,\"msg\":\"文件信息错误\",\"to\":\"/\"}";
//        }
//
//
//    }
//
//
//    /**
//     *
//     * @param cid
//     * @return
//     */
//    @RequestMapping("/test" )
//    public  RestResultResponse test(String cid){
////caseService.test(cid);
//        boolean a=true;
//        if (cid.equals("1")){
//            a=false;
//        }
////        if(a){
////
////        }else {
////            throw new com.ciic.test.exception.DelectFailedException("实验",cid);
////        }
//        return new RestResultResponse(true,"实验",cid);
//    }
//
//    /**
//     *
//     * @param session
//     * @return
//     * @throws IOException
//     */
//    @RequestMapping("/logout")
//    String logout(HttpSession session) throws IOException {
//       // System.out.println(session.getAttributeNames().nextElement());
//        session.removeAttribute(session.getAttributeNames().nextElement());
//
//     //   response.sendRedirect("");
//        return "{\"isok\":0,\"msg\":\"登出成功\",\"to\":\"/\"}";
//
//    }
//
//    /**
//     *
//     * @return
//     */
//    @RequestMapping("/com")
//    String comment(){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success2\"}";
//    }
//
//    /**
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping("/gitem")
//    String getItem(HttpSession session){
//
//        List<item> li= itemService.getItem(session.getAttributeNames().nextElement());
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"items\":"+li+"}";
//
//
//
//    }
//
//    /**
//     *
//     * @param file
//     * @param session
//     * @param uid
//     * @param tid
//     * @return
//     */
//    @RequestMapping(value = "/upload/{uid}/{tid}", method = RequestMethod.POST)
//    @ResponseBody
//    public String upload(@RequestParam("file") MultipartFile file,HttpSession session,@PathVariable String uid,@PathVariable String tid) {
//       String tit= itemService.getName(tid);
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH )+1;
//        File file2=new File(filePath+tit+"/"+year+"_"+month+"/");
//        if(!file2.exists()){
//            file2.mkdirs();
//        }
//        File file1=new File(filePath+tit+"/"+year+"_"+month+"/"+file.getOriginalFilename());
//        if (!file.isEmpty()) {
//            try {
//
//if(file1.exists()){
//    new File(filePath+tit+"/"+year+"_"+month+"/"+System.currentTimeMillis()+'/').mkdirs();
//    file1=new File(filePath+tit+"/"+year+"_"+month+"/"+System.currentTimeMillis()+'/'+file.getOriginalFilename());
//}
//                BufferedOutputStream out = new BufferedOutputStream(
//                        new FileOutputStream(file1));
//                out.write(file.getBytes());
//                out.flush();
//                out.close();
//            } catch (FileNotFoundException e) {
//
//
//                return "{\"isok\":1,\"msg\":\"上传失败，文件未找到\",\"to\":\"/\"}";
//            } catch (IOException e) {
//
//                return "{\"isok\":1,\"msg\":\"上传失败，因为文件传输不正确\",\"to\":\"/\"}";
//            }
//            String name =file.getOriginalFilename();
//            String size=file.getSize()+"";
//            String userid=session.getAttributeNames().nextElement();
//            String path=file1.getAbsolutePath();
//            int aa=0;
//            if(uid.equals("0")){
//              aa=  configService.addFile(name,size,userid,path,tid);
//            }else {
//             aa=   configService.updateFile(name,size,userid,path,uid);
//            }
//            if(aa==1){
//                return "{\"isok\":0,\"msg\":\"上传成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"上传失败\",\"to\":\"/\"}";
//            }
//
//        } else {
//            return "{\"isok\":1,\"msg\":\"上传失败，因为文件是空的.\",\"to\":\"/\"}";
//
//        }
//    }
//
//
//    /**
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping("/getlog")
//    String getLog(HttpSession session){
//        if(userService.isManager(session.getAttributeNames().nextElement())){
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\",\"logs\":"+configService.getActLog()+"}";
//        }else {
//
//            return "{\"isok\":1,\"msg\":\"信息不对称\",\"to\":\"/\"}";
//        }
//
//    }
//
//
//    /**
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping("/getslog")
//    String getsLog(HttpSession session){
//        if(userService.isManager(session.getAttributeNames().nextElement())){
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\",\"logs\":"+configService.getsLog()+"}";
//        }else {
//
//            return "{\"isok\":1,\"msg\":\"信息不对称\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping("/gitema")
//    String getAllItem(HttpSession session){
//        if(userService.isManager(session.getAttributeNames().nextElement())){
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\",\"items\":"+itemService.getAllItem()+"}";
//        }else {
//
//            return "{\"isok\":1,\"msg\":\"信息不对称\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param item
//     * @param session
//     * @return
//     */
//    @RequestMapping("/gpage/{item}")
//    String getPage(@PathVariable String item,HttpSession session){
//        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)){
//            List<Page> lp=itemService.getPage(item);
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"pages\":"+lp+"}";
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param item
//     * @return
//     */
//    @RequestMapping("/geurls/{item}")
//    String getExceptUrls(@PathVariable String item){
//
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"urls\":"+configService.getExceptUrls(item)+"}";
//
//
//
//
//
//    }
//
//    /**
//     *
//     * @param item
//     * @param session
//     * @return
//     */
//    @RequestMapping("/getfile/{item}")
//    String getfile(@PathVariable String item,HttpSession session){
//        String uid=session.getAttributeNames().nextElement();
//        if(itemService.isOwnItem(uid,item)){
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"files\":"+configService.getfile(item)+"}";
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param lid
//     * @return
//     */
//    @RequestMapping("/updatelid/{lid}")
//    String updateLogStatus(@PathVariable String lid){
//          int a=  configService.updateLogStatus(lid);
//
//
//
//        if(a==1){
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"修改成功\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"修改失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param url
//     * @param tid
//     * @param type
//     * @return
//     */
//    @RequestMapping("/updateeurl")
//    String updateEurl(String url,String tid,String type){
//
//        int a;
//        if (type.equals("0")) {
//            a=configService.addExceptUrl(url,tid);
//
//        } else {
//            a = configService.updateExceptUrl(url,type);
//        }
//
//
//        if(a==1){
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"修改成功\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"修改失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param page
//     * @param item
//     * @param session
//     * @return
//     */
//    @RequestMapping("/gele/{page}/{item}")
//    String getEle(@PathVariable String page,@PathVariable String item,HttpSession session){
//        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)&&getPageService.isOwnPage(page,item)){
//           // List<Page> lp=itemService.getPage(page);
//            List<Element> le=null;
//            if(page.equals("-1")){
//                le=getPageService.getall(item);
//
//            }else {
//                le=  getPageService.get(page);
//            }
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"elements\":"+le+"}";
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//
//        }}
//
//    /**
//     *
//     * @param item
//     * @param pagename
//     * @param pagetitle
//     * @param type
//     * @return
//     */
//    @RequestMapping(value = "/addpage")
//    String addPage(String item,String pagename,String pagetitle,String type){
//        if(item.isEmpty()||pagename.isEmpty()||pagetitle.isEmpty()||type.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"信息不能为空\",\"to\":\"/\"}";
//        }else {
//            int a=0;
//            if(type.equalsIgnoreCase("0")){
//                a= getPageService.addPage(item,pagename,pagetitle);
//            }else {
//a=getPageService.updatePageInfoById(item,type,pagename,pagetitle);
//            }
//
//
//               if(a==1){
//                    return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\"}";
//               }else {
//                   return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//               }
//        }
//
//    }
//
//    /**
//     *
//     * @param item
//     * @param elename
//     * @param num
//     * @param type
//     * @param elelo
//     * @param elevalue
//     * @param pid
//     * @param topage
//     * @param toframe
//     * @param waitv
//     * @param waitid
//     * @param isframe
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "/addele")
//    String addEle(String item,String elename,String num,String type,String elelo,String elevalue,String pid,String topage,String toframe,String waitv,String waitid,String isframe,HttpSession session){
//
//        if(isframe.isEmpty()||item.isEmpty()||elename.isEmpty()||num.isEmpty()||type.isEmpty()||elelo.isEmpty()||elevalue.isEmpty()||pid.isEmpty()||topage.isEmpty()||toframe.isEmpty()||waitid.isEmpty()||waitv.isEmpty()||!getPageService.isOwnPage(pid,item)){
//            return "{\"isok\":1,\"msg\":\"信息不能为空\",\"to\":\"/\"}";
//        }else {
//            Element element=new Element();
//            element.setLocationMethod(elelo);
//            element.setName(elename);
//            element.setTopage(topage);
//
//            element.setValue(elevalue.replace("\"","%78"));
//            element.setToframe(toframe);
//            element.setWaitid(waitid);
//            element.setWaitvalue(waitv);
//            element.setNum(num);
//            element.setIsframe(isframe);
//            int a=0;
//            if(type.equalsIgnoreCase("0")){
//                a= getPageService.addEle(element,session.getAttributeNames().nextElement(),pid);
//            }else {
//                a=getPageService.updateEle(element,session.getAttributeNames().nextElement(),type);
//            }
//
//
//            if(a==1){
//                return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"操作成功\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//        }
//
//    }
//
//
//    /**
//     *
//     * @param page
//     * @param session
//     * @param item
//     * @return
//     */
//    @RequestMapping("/removepage/{page}/{item}")
//    String removePage(@PathVariable String page,HttpSession session,@PathVariable String item){
//        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)&&getPageService.isOwnPage(page,item)){
//            int a=getPageService.removePage(page);
//            if(a==1){
//                return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"删除成功\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//            }
//
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//
//        }
//    }
//
//    /**
//     *
//     * @param page
//     * @param session
//     * @param item
//     * @param ele
//     * @return
//     */
//    @RequestMapping("/removeele/{ele}/{page}/{item}")
//    String removeEle(@PathVariable String page,HttpSession session,@PathVariable String item,@PathVariable String ele){
//        if(itemService.isOwnItem(session.getAttributeNames().nextElement(),item)&&getPageService.isOwnPage(page,item)&&getPageService.isOwnEle(page,ele)){
//            int a=getPageService.removeEle(ele);
//            if(a==1){
//                return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"删除成功\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//            }
//
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//
//        }
//    }
//
//    /**
//     *
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/getlabel/{tid}")
//    String getLabel(@PathVariable String tid){
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"labels\":"+configService.getLabel(tid)+"}";//",\"res\":" + caseService.getCaseresNum(seriesid)+
//    }
//
//    /**
//     *
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/getusedlabel/{tid}")
//    String getUsedLabel(@PathVariable String tid){
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"labels\":"+configService.getUsedLabel(tid)+"}";//",\"res\":" + caseService.getCaseresNum(seriesid)+
//    }
//
//
//    //case
//
//    /**
//     *
//     * @param seriesid
//     * @return
//     */
//    @RequestMapping("/getseriesandcase/{seriesid}")
//    String getSeriesAndCase(@PathVariable String seriesid){
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+caseService.getCaseresList(seriesid)+",\"series\":" + caseService.getFinishSeries(seriesid) + ",\"res\":" + caseService.getCaseres(seriesid) +"}";//",\"res\":" + caseService.getCaseresNum(seriesid)+
//    }
//
//    /**
//     *
//     * @param tid
//     * @param all
//     * @return
//     */
//    @RequestMapping("/getcase/{all}/{tid}")
//String getcase(@PathVariable String tid,@PathVariable String all){
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+caseService.getcase(tid,all.equals("1")?true:false)+"}";
//    }
//
//    /**
//     *
//     * @param seriesid
//     * @return
//     */
//    @RequestMapping("/getcasereslist/{seriesid}")
//    String getCasereslist(@PathVariable String seriesid){
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+caseService.getCaseresList(seriesid)+"}";
//    }
//
//    /**
//     *
//     * @param cid
//     * @return
//     */
//    @RequestMapping("/getstep/{cid}")
//    String getstep(@PathVariable String cid){
//        List<Step> ls =itemService.getStep(cid);
//
//          return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"steps\":"+ls+"}";
//
//    }
//
//    /**
//     *
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/getdatasource/{tid}")
//    String getDatasource(@PathVariable String tid){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"datasources\":"+configService.getDatasource(tid)+"}";
//
//    }
//
//    /**
//     *
//     * @param sid
//     * @return
//     */
//    @RequestMapping("/getexp/{sid}")
//    String getExp(@PathVariable String sid){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"exp\":"+caseService.getExpected(sid)+"}";
//
//    }
//
//    /**
//     *
//     * @param cid
//     * @return
//     */
//
//    @RequestMapping("/getprecondition/{cid}")
//    String getPrecondition(@PathVariable String cid){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"pre\":"+caseService.getPrecondition(cid)+"}";
//
//    }
//
//    /**
//     *
//     * @param type
//     * @param sid
//     * @param a
//     * @param b
//     * @param c
//     * @param d
//     * @param e
//     * @return
//     */
//
//    @RequestMapping("/updateexp")
//    String updateExp(String type,String sid,String a,String b,String c,String d,String e){
//        if(type.isEmpty()||sid.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
//        }else {
//
//
//            int  n   = caseService.updateExp(type,sid,a,b,c,d,e);
//
//
//
//            if(n==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param type
//     * @param cid
//     * @param a
//     * @param b
//     * @param c
//     * @return
//     */
//    @RequestMapping("/updatepre")
//    String updatePrecondition(String type,String cid,String a,String b,String c){
//        if(type.isEmpty()||cid.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
//        }else {
//
//
//            int  n   = caseService.updatePrecondition(type,cid,a,b,c);
//
//
//
//            if(n==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param did
//     * @return
//     */
//
//    @RequestMapping("/getdatasourceconnect/{did}")
//    String getDatasourceConnect(@PathVariable String did){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"con\":\""+configService.connectDatasource(did).replace("\"","\\\"")+"\"}";
//
//    }
//
//    /**
//     *
//     * @param tid
//     * @param session
//     * @return
//     */
//    @RequestMapping("/getseries/{tid}")
//    String getSeries(@PathVariable String tid, HttpSession session){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"series\":"+caseService.getSeries(tid,session.getAttributeNames().nextElement())+"}";
//
//    }
//
//    /**
//     *
//     * @param sid
//     * @return
//     */
//    @RequestMapping("/getpid/{sid}")
//    String getpid(@PathVariable String sid){
//        String value=caseService.getPid(sid);
//        if(value.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"查找pid失败\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"pid\":"+value+"}";
//        }
//
//    }
//
//
//    /**
//     *
//     * @param sid
//     * @return
//     */
//    @RequestMapping("/gettopage/{sid}")
//    String gettopage(@PathVariable String sid){
//        String value=caseService.getTopage(sid);
//        if(value.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"查找页面失败\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"topage\":"+value+"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/getcasehome/{tid}")
//    String getCaseHome(@PathVariable String tid){
//
//
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"casehomes\":"+ caseService.getCaseHome(tid)+"}";
//
//
//    }
//
//    /**
//     *
//     * @param tid
//     * @param id
//     * @param httpSession
//     * @return
//     */
//    @RequestMapping("/setcasehome/{id}/{tid}")
//    String setCaseHome(@PathVariable String tid,@PathVariable String id ,HttpSession httpSession){
//
//        if(userService.isManager(httpSession.getAttributeNames().nextElement())) {
//
//            int n = caseService.setCaseHome(id, tid);
//
//
//            if (n == 1) {
//
//
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            } else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//        }else {
//            return "{\"isok\":4,\"msg\":\"当前权限无法操作\",\"to\":\"/\"}";
//        }
//    }
//
//    /**
//     *
//     * @param cid
//     * @return
//     */
//    @RequestMapping("/gethttpcase/{cid}")
//    String getHttpCase(@PathVariable String cid){
//
//
//        return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"cases\":"+ caseService.getHttpCase(cid)+"}";
//
//
//    }
//
//    /**
//     *
//     * @param cid
//     * @param tid
//     * @param name
//     * @param session
//     * @return
//     */
//    @RequestMapping("/testcase/{cid}/{tid}/{name}")
//    String testCase(@PathVariable String cid,@PathVariable String tid,@PathVariable String name,HttpSession session){
//        int n=0;
//        List<Series> ls=    caseService.getOneSeries(name+"调试",tid);
//
//        if(ls.size()==0){
//         n=   caseService.addRunCase(name+"调试",cid,tid,"1",session.getAttributeNames().nextElement());
//
//        }else{
//            boolean hasCid=false;
//
//
//
//            for (int i = 0; i <ls.size() ; i++) {
//                if(ls.get(i).getCids().equals(cid)){
//                    hasCid=true;
//
//                }
//
//            }
//            if(hasCid){
//                return "{\"isok\":1,\"msg\":\"已有相同调试在运行或等待运行，请在进度中查看\",\"to\":\"/\"}";
//            }else {
//                n=   caseService.addRunCase(name+"调试",cid,tid,"1",session.getAttributeNames().nextElement());
//
//
//            }
//
//        }
//
//
//
//
//
//
//        if(n==1){
//                   if(map4thread.containsKey(tid)){
//                       if(map4thread.get(tid).isAlive()){
//
//                       }else {
//                           map4thread.put(tid, caseService.startrun(tid));
//                           map4thread.get(tid).start();
//                       }
//
//                   }else {
//                       map4thread.put(tid, caseService.startrun(tid));
//                       map4thread.get(tid).start();
//                   }
//
//
//
//
//
//
//            return "{\"isok\":0,\"msg\":\"操作成功,请稍后查看结果\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//
//    }
//
//    /**
//     *
//     * @param cids
//     * @param tid
//     * @param seriesName
//     * @return
//     */
//        @RequestMapping("/addseries")
//    String addSeries( String cids,String tid,String seriesName){
//      int n=  caseService.addRunCase(seriesName,cids,tid,"2","0");
//
//
//            if(n==1){
//
//
//                return "{\"isok\":0,\"msg\":\"操作成功,请在进度页面执行\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//
//
//    }
//
//    /**
//     *
//     * @param id
//     * @param ispass
//     * @return
//     */
//    @RequestMapping("/addcase2all")
//    String addCase2All( String id,String ispass){
//        int n;
//        if (ispass.equals("1")) {
//            n = caseService.updateCanRunCase(id);
//        } else {
//            n=caseService.updateCantRunCase(id);
//        }
//
//
//        if(n==1){
//
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param chid
//     * @return
//     */
//
//    @RequestMapping("/removecasehome/{chid}")
//    String removeCaseHome( @PathVariable String chid){
//        int n=  caseService.removeCaseHome(chid);
//
//
//        if(n==1){
//
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param euid
//     * @return
//     */
//
//    @RequestMapping("/removeeurl/{euid}")
//    String removeEurl( @PathVariable String euid){
//        int n=  configService.removeExceptUrl(euid);
//
//
//        if(n==1){
//
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param id
//     * @return
//     */
//    @RequestMapping("/removefile/{id}")
//    String removeFile( @PathVariable String id){
//        int n=  configService.reomveFile(id);
//
//
//        if(n==1){
//
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param id
//     * @param cids
//     * @return
//     */
//    @RequestMapping("/updatecids")
//    String updateCids(  String id,String cids){
//        int n=  caseService.updateCaseHome4cids(cids,id);
//
//
//        if(n==1){
//
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param name
//     * @param des
//     * @param tid
//     * @param type
//     * @return
//     */
//    @RequestMapping("/addcasehome")
//    String addCaseHome( String name,String des,String tid,String type){
//        int n=0;
//        if(name.isEmpty()||tid.isEmpty()||type.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"数据信息不全\",\"to\":\"/\"}";
//        }else {
//            if(type.equals("0")){
//                n=  caseService.addCaseHome(name,des,tid);
//            }else {
//                n=caseService.updateCaseHome(type,name,des);
//            }
//        }
//
//
//
//
//
//        if(n==1){
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param name
//     * @param des
//     * @param tid
//     * @param type
//     * @return
//     */
//    @RequestMapping("/addlabel")
//    String addLabel( String name,String des,String tid,String type){
//        int n=0;
//        if(name.isEmpty()||tid.isEmpty()||type.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"数据信息不全\",\"to\":\"/\"}";
//        }else {
//            if(type.equals("0")){
//                n=  configService.addLabel(name,des,tid);
//            }else {
//                n=configService.updateLabel(type,name,des);
//            }
//        }
//
//
//
//
//
//        if(n==1){
//
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//
//
////
////    @RequestMapping("/looktestcase/{cid}")
////    String lookTestCase(@PathVariable String cid){
////
////
////    //    caseService.
////        if( caseService.isRuningOneCase(cid)){
////            return "{\"isok\":4,\"msg\":\"当前用例正在运行，请稍后再试~\",\"to\":\"/\"}";
////        }else {
////            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"res\":"+caseService.getOnecase(cid).size()+"}";
////
////        }
////
////
////
////    }
//
//    /**
//     *
//     * @param mimi
//     * @param session
//     * @return
//     */
//    @RequestMapping("/clearisused")
//    String clearIsused(String mimi,HttpSession session){
//        if(userService.isManager(session.getAttributeNames().nextElement())){
//
//            long a=System.currentTimeMillis()/1000;
//            String b=mycode.decode(mimi);
//            long c= 0;
//            try {
//                c = Long.parseLong(b);
//            } catch (Exception e) {
//                return "{\"isok\":1,\"msg\":\"时间不对等，请不要修改url内容\",\"to\":\"/\"}";
//            }
//            if(Math.abs(a-c)>50){
//                return "{\"isok\":1,\"msg\":\"时间不对等，请不要修改url内容\",\"to\":\"/\"}";
//            }else {
//int num=configService.clearisused();
//
//                return "{\"isok\":0,\"msg\":\"完成操作,删除"+num+"条数据。\",\"to\":\"/\"}";
//            }
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"没有权限操作\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param mimi
//     * @param session
//     * @return
//     */
//    @RequestMapping("/stoprunningcase")
//    String stopRunningCase(String mimi,HttpSession session){
//        if(userService.isManager(session.getAttributeNames().nextElement())){
//
//            long a=System.currentTimeMillis()/1000;
//            String b=mycode.decode(mimi);
//            long c= 0;
//            try {
//                c = Long.parseLong(b);
//            } catch (Exception e) {
//                return "{\"isok\":1,\"msg\":\"时间不对等，请不要修改url内容\",\"to\":\"/\"}";
//            }
//            if(Math.abs(a-c)>50){
//                return "{\"isok\":1,\"msg\":\"时间不对等，请不要修改url内容\",\"to\":\"/\"}";
//            }else {
//                int num=configService.stopRunCase();
//
//                return "{\"isok\":0,\"msg\":\"完成操作,处理"+num+"条数据。\",\"to\":\"/\"}";
//            }
//
//        }else {
//            return "{\"isok\":1,\"msg\":\"没有权限操作\",\"to\":\"/\"}";
//        }
//
//
//
//    }
//
//    /**
//     *
//     * @param id
//     * @param tid
//     * @param cid
//     * @return
//     */
//    @RequestMapping("/ge4p/{id}/{tid}/{cid}")
//    String geteleforpage(@PathVariable String id,@PathVariable String tid,@PathVariable String cid){
//        if(id.equalsIgnoreCase("0")){
//           String a= caseService.getPid4Case(cid);
//
//           if(a.equals("0")){
//               String title=   itemService.gerTitle(tid);
//               if(title.equalsIgnoreCase("err")){
//                   return "{\"isok\":1,\"msg\":\"项目首页链接出错,请检查。\",\"to\":\"/\"}";
//               }else {
//                   List<Page> lp=  itemService.getOnePage(tid,title);
//                   if(lp.size()>0){
//                       return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"page\":"+lp.get(0)+",\"eles\":"+itemService.getele4page(lp.get(0).getId())+"}";
//
//                   }else {
//                       return "{\"isok\":1,\"msg\":\"获取页面失败。\",\"to\":\"/\"}";
//                   }
//
//
//
//
//               }
//           }else {
//               return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"page\":"+itemService.getOnePageById(a).get(0)+",\"eles\":"+itemService.getele4page(a)+"}";
//
//           }
//
//
//
//        }else {
//            return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"page\":"+itemService.getOnePageById(id).get(0)+",\"eles\":"+getPageService.get(id)+"}";
//
//        }
//
//
//       // return "{\"isok\":0,\"to\":\"/html/context.html\",\"msg\":\"success\",\"steps\":"+""+"}";
//
//    }
//
//    /**
//     *
//     * @param step
//     * @param pagename
//     * @param catid
//     * @param cid
//     * @param value
//     * @param eid
//     * @param ename
//     * @return
//     */
//    @RequestMapping("/addstep")
//    String addstep( String step,String pagename,String catid,String cid,String value,String eid,String ename){
//if(step.isEmpty()||pagename.isEmpty()||catid.isEmpty()||cid.isEmpty()||eid.isEmpty()||ename.isEmpty()){
//    return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
//}else {
//
//
//    int  a   =  itemService.addStep(step,pagename,catid,cid,value,eid,ename);
////    new Thread(new Runnable() {
////        @Override
////        public void run() {
////            caseService.zhengliStep(cid);
////        }
////    }).start();
//
//
//
//    if(a==1){
//        return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//    }else {
//        return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//    }
//
//}}
//
//
//    /**
//     *
//     * @param id
//     * @param pagename
//     * @param catid
//     * @param value
//     * @param eid
//     * @param ename
//     * @return
//     */
//    @RequestMapping("/fixstep")
//    String fixstep( String id,String pagename,String catid,String value,String eid,String ename){
//        if(id.isEmpty()||pagename.isEmpty()||catid.isEmpty()||eid.isEmpty()||ename.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
//        }else {
//
//
//            int  a   =  itemService.updateStep(id,pagename,catid,value,eid,ename);
//
//
//
//            if(a==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//        }}
//
//    /**
//     *
//     * @param id
//     * @param labels
//     * @return
//     */
//    @RequestMapping("/upatelabel")
//    String updateLabel( String id,String labels){
//        if(id.isEmpty()||labels.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
//        }else {
//
//
//            int  a   = caseService.updateLabel(id,labels);
//
//
//
//            if(a==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//        }}
//
//    /**
//     *
//     * @param type
//     * @param url
//     * @param con
//     * @param eq
//     * @param value
//     * @param cid
//     * @return
//     */
//    @RequestMapping("/upatehttp")
//    String updateHttp( String type,String url,String con,String eq,String value,String cid){
//        if(cid.isEmpty()||type.isEmpty()||url.isEmpty()||eq.isEmpty()||value.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
//        }else {
//
//
//            int  a   = caseService.updateHttpCase(type,url,con,eq,value,cid);
//
//
//
//            if(a==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//        }}
//
//    /**
//     *
//     * @param name
//     * @param des
//     * @param important
//     * @param type
//     * @param tid
//     * @param elety
//     * @return
//     */
//    @RequestMapping("/addcase")
//String addcase(String name,String des,String important,String type,String tid,String elety){
//       if(name.isEmpty()||important.isEmpty()||des.isEmpty()||type.isEmpty()||tid.isEmpty()||elety.isEmpty()){
//           return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//       }else {
//           int a=0;
//           if(type.equalsIgnoreCase("0")){
//             a=  caseService.addCase(name,des,important,tid,elety);
//
//           }else {
//               a=caseService.updatecase(type,name,des,important);
//
//           }
//
//           if(a==1){
//               return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//           }else {
//               return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//           }
//
//
//       }
//}
//
//    /**
//     *
//     * @param name
//     * @param des
//     * @param type
//     * @param link
//     * @param dataname
//     * @param user
//     * @param pass
//     * @param tid
//     * @param id
//     * @return
//     */
//    @RequestMapping("/adddatasource")
//    String addDatasource(String name,String des,String type,String link,String dataname,String user,String pass,String tid,String id){
//        if(name.isEmpty()||type.isEmpty()||tid.isEmpty()||link.isEmpty()||dataname.isEmpty()||user.isEmpty()||pass.isEmpty()||id.isEmpty()){
//            return "{\"isok\":1,\"msg\":\"信息不匹配\",\"to\":\"/\"}";
//        }else {
//int a=0;
//if(id.equalsIgnoreCase("0")){
//    a=configService.addDataspource(name,des,type,link,dataname,user,pass,tid);
//}else {
//    a=configService.updateDatasource(id,name,des,type,link,dataname,user,pass);
//}
//
//
//
//            if(a==1){
//                return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//            }
//
//
//        }
//    }
//
//
//    /**
//     *
//     * @param cid
//     * @return
//     */
//    @RequestMapping("/removecase/{cid}")
//    String removeCase(@PathVariable String cid){
//       int a= caseService.removeCase(cid);
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param lid
//     * @return
//     */
//    @RequestMapping("/removelabel/{lid}")
//    String removeLabel(@PathVariable String lid){
//        int a= configService.removeLabel(lid);
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//    @RequestMapping("/copycase/{mu}/{yuan}")
//    String copyCase(@PathVariable String mu,@PathVariable String yuan){
//        int a= caseService.copyCase(mu,yuan);
//
//        if(a>0){
//            return "{\"isok\":0,\"msg\":\"复制成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"复制失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param seid
//     * @return
//     */
//    @RequestMapping("/removeseries/{seid}")
//    String removeSeries(@PathVariable String seid){
//        int a= caseService.removeSeries(seid);
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param seid
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/runseries/{seid}/{tid}")
//    String runSeries(@PathVariable String seid,@PathVariable String tid){
//        int a= caseService.updateOneseriesStatus("1","",seid);
//
//        if(map4thread.containsKey(tid)){
//            if(map4thread.get(tid).isAlive()){
//
//            }else {
//                map4thread.put(tid, caseService.startrun(tid));
//                map4thread.get(tid).start();
//            }
//
//        }else {
//            map4thread.put(tid, caseService.startrun(tid));
//            map4thread.get(tid).start();
//        }
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param seid
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/stopseries/{seid}/{tid}")
//    String stopSeries(@PathVariable String seid,@PathVariable String tid){
//
//        try {
//            if(map4thread.get(tid).isAlive()){
//                caseService.stopRun(seid);
//                return "{\"isok\":0,\"msg\":\"操作成功，强制中止中\",\"to\":\"/\"}";
//            }else {
//                return "{\"isok\":1,\"msg\":\"操作失败，已经停止或暂时无法停止\",\"to\":\"/\"}";
//            }
//        } catch (NullPointerException e) {
//
//            return "{\"isok\":1,\"msg\":\"操作失败，运行出现错误，请修改数据库\",\"to\":\"/\"}";
//        }
//
//
//    }
//
//    /**
//     *
//     * @param seid
//     * @param tid
//     * @return
//     */
//    @RequestMapping("/pauseseries/{seid}/{tid}")
//    String pauseSeries(@PathVariable String seid,@PathVariable String tid){
//        int a= caseService.updateOneseriesStatus("0","",seid);
//
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"操作成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"操作失败,正好轮到运行\",\"to\":\"/\"}";
//        }
//
//    }
//
//
//    /**
//     *
//     * @param did
//     * @return
//     */
//    @RequestMapping("/removedatasource/{did}")
//    String removeDatasource(@PathVariable String did){
//        int a= configService.removeDatasource(did);
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//    /**
//     *
//     * @param sid
//     * @return
//     */
//    @RequestMapping("/removestep/{sid}")
//    String removestep(@PathVariable String sid){
//        int a= caseService.removeStep(sid);
//
//        if(a==1){
//            return "{\"isok\":0,\"msg\":\"删除成功\",\"to\":\"/\"}";
//        }else {
//            return "{\"isok\":1,\"msg\":\"删除失败\",\"to\":\"/\"}";
//        }
//
//    }
//
//
//
//}
