package com.ciic.test.dao;

import com.ciic.test.bean.Element;

import com.ciic.test.bean.tmp;
import com.ciic.test.service.GetElementService;
import com.ciic.test.service.GetPageService;


import com.ciic.test.tools.mycode;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/11.
 */
@Service
public class GetPageDao implements GetPageService{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GetElementService getElementService;

//    @Autowired
//    private ButtonService buttonService;
//    @Autowired
//    private CheckboxService checkboxService;
//    @Autowired
//    private DialogService dialogService;
//    @Autowired
//    private RadioService radioService;
//    @Autowired
//    private SelectService selectService;
//    @Autowired
//    private TextService textService;
//    @Autowired
//    private UploadService uploadService;
//    @Autowired
//    private SwitchToService switchToService;





private List<Element> list;
//private Map<String, ButtonService> mapb;
//private Map<String, CheckboxService> mapc;
//private Map<String, DialogService> mapd;
//private Map<String, RadioService> mapr;
//private Map<String, SelectService> maps;
//private Map<String, TextService> mapt;
//private Map<String, UploadService> mapu;
//private Map<String, SwitchToService> mapsw;


    @Override
    public List<Element> get(String page) {
        list=jdbcTemplate.query("SELECT id,num,isframe,locationMethod,value,name,lastupdatetime,updater,topage,toframe,waitid,waitvalue from element where isused=1 and pid=?", mycode.prase(new Object[]{page}),new BeanPropertyRowMapper<Element>(Element.class));


   return list;
    }

    @Override
    public List<Element> getall(String tid) {
        List<tmp> lt=jdbcTemplate.query("select isfinish value ,id value2 from casehome where tid=? and isused=1 and isnow=1",new Object[]{tid},new BeanPropertyRowMapper<>(tmp.class));
if(lt.size()>0){
    if(lt.get(0).getValue().equals("0")){
        return jdbcTemplate.query("SELECT element.id,pid,num,isframe,locationMethod,value,element.name,lastupdatetime,updater,topage,toframe,waitid,waitvalue,page.pagename,page.vid from element join page on element.pid=page.id  where element.isused=1 and page.isused=1 and page.tid=? and (vid=\"\" or vid is null)\n ",new Object[]{tid},new BeanPropertyRowMapper<>(Element.class));


    }else {
        return jdbcTemplate.query("SELECT element.id,pid,num,isframe,locationMethod,value,element.name,lastupdatetime,updater,topage,toframe,waitid,waitvalue,page.pagename,page.vid from element join page on element.pid=page.id  where element.isused=1 and page.isused=1 and page.tid=? and vid="+lt.get(0).getValue2(),new Object[]{tid},new BeanPropertyRowMapper<>(Element.class));

    }

}else {
    return  new ArrayList<>();
}
    }

//    @Override
//    public GetPageService control() {
//        Map<String, WebElement> map=     getElementService.getElements(list);
//        mapb=new HashMap<>();
//        mapc=new HashMap<>();
//        mapd=new HashMap<>();
//        mapr=new HashMap<>();
//        maps=new HashMap<>();
//        mapt=new HashMap<>();
//        mapu=new HashMap<>();
//        mapsw=new HashMap<>();
//        map.forEach((k,v)->{
//            String[] k2=  k.split("1a0a1");
//            switch (k2[1]){
//                case "0": mapb.put(k2[0],buttonService.setElemnet(v));break;
//                case "1": mapc.put(k2[0],checkboxService.setElemnet(v));break;
//                case "2": mapd.put(k2[0],dialogService);break;
//                case "3": mapr.put(k2[0],radioService.setElemnet(v));break;
//                case "4": maps.put(k2[0],selectService.setElemnet(v));break;
//                case "5": mapt.put(k2[0],textService.setElemnet(v));break;
//                case "6": mapu.put(k2[0],uploadService.setElemnet(v));break;
//                case "7": mapsw.put(k2[0],switchToService.setElement(v));break;
//
//
//            }
//
//        });
//        return this;
//    }


//    @Override
//    public Map<String, ButtonService> getbuttonmap() {
//        return mapb;
//    }
//
//    @Override
//    public Map<String, CheckboxService> getcheckboxmap() {
//        return mapc;
//    }
//
//    @Override
//    public Map<String, DialogService> getdialogmap() {
//        return mapd;
//    }
//
//    @Override
//    public Map<String, RadioService> getradiomap() {
//        return mapr;
//    }
//
//    @Override
//    public Map<String, SelectService> getselectmap() {
//        return maps;
//    }
//
//    @Override
//    public Map<String, TextService> gettextmap() {
//        return mapt;
//    }
//
//    @Override
//    public Map<String, UploadService> getuploadmap() {
//        return mapu;
//    }
//
//    @Override
//    public Map<String, SwitchToService> getFramemap() {
//        return mapsw;
//    }

    @Override
    public int updatePageInfoById(String tid,String pid,String pagename,String pagetitle) {

        return jdbcTemplate.update("UPDATE page set pagename=?,pagetitle=? WHERE id=? and tid=? and isused=1",mycode.prase(new  Object[]{pagename,pagetitle,pid,tid}));

    }

    @Override
    public int addPage(String item, String pagename, String pagetitle) {
       return jdbcTemplate.update("INSERT INTO \"main\".\"page\" ( \"pagename\", \"pagetitle\", \"parentid\",  \"tid\") VALUES (?, ?, 0,  ?)",mycode.prase(new Object[]{pagename,pagetitle,item}));
    }

    @Override
    public int removePage(String id) {
       return jdbcTemplate.update("update page set isused=0 where id=?",mycode.prase(new Object[]{id}));

    }

    @Override
    public boolean isOwnPage(String pid, String tid) {
        List<tmp> lt=  jdbcTemplate.query("SELECT 1 from page where tid=? and id=?",mycode.prase(new Object[]{tid,pid}),new BeanPropertyRowMapper<tmp>(tmp.class));
        if(lt.size()==1||pid.equals("-1")){
            return  true;
        }else
            return false;

    }

    @Override
    public boolean isOwnEle(String pid, String eid) {
        List<tmp> lt=   jdbcTemplate.query("SELECT 1 from element WHERE id=? and pid=?",mycode.prase(new Object[]{eid,pid}),new BeanPropertyRowMapper<tmp>(tmp.class));
        if(lt.size()==1){
            return  true;
        }else
            return false;
    }

    @Override
    public int removeEle(String id) {
        return jdbcTemplate.update("UPDATE element set isused=0 where id=?",mycode.prase(new Object[]{id}));
    }

    @Override
    public int addEle(Element element,String user,String pid) {
        return jdbcTemplate.update("INSERT INTO \"element\" ( \"num\", \"isframe\", \"pid\", \"locationMethod\", \"value\", \"name\", \"topage\", \"toframe\",\"createtime\", \"waitid\",\"waitvalue\", \"creater\") VALUES ( ?, ?,?, ?, ?, ?, ?,?, ?,?,?,  ?)",
                mycode.prase(new Object[]{element.getNum(),element.getIsframe(),pid,element.getLocationMethod(),element.getValue(),element.getName(),element.getTopage(), element.getToframe(),LocalDate.now()+" "+ LocalTime.now(),element.getWaitid(),element.getWaitvalue(),user}));

    }

    @Override
    public int updateEle(Element element,String user,String eid) {
      return   jdbcTemplate.update("UPDATE \"element\" SET  \"num\"=?,\"isframe\"=?,   \"locationMethod\"=?, \"value\"=?, \"name\"=?, \"topage\"=?, \"toframe\"=?,  \"lastupdatetime\"=?, \"updater\"=?, \"waitid\"=?, \"waitvalue\"=? WHERE (\"id\"=?)",
              mycode.prase(new Object[]{element.getNum(),element.getIsframe(),element.getLocationMethod(),element.getValue(),element.getName(),element.getTopage(), element.getToframe(),LocalDate.now()+" "+ LocalTime.now(),user,element.getWaitid(),element.getWaitvalue(),eid}));


    }

//    @Override
//    public List<Page> get(String item) {
//        return jdbcTemplate.query("SELECT id,pagename,pagetitle from page where isused=1 and tid=?",mycode.prase(new Object[]{item},new BeanPropertyRowMapper<Page>(Page.class));
//
//    }
}
