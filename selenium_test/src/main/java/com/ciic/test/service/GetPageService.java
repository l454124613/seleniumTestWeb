package com.ciic.test.service;

import com.ciic.test.bean.Element;
import com.ciic.test.service.pageInfo.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface GetPageService {

//    List<Page> get(String item);


    List<Element> get(String page);
    List<Element> getall(String tid);

 GetPageService control();
     Map<String,ButtonService>   getbuttonmap();
     Map<String,CheckboxService> getcheckboxmap();
     Map<String,DialogService>   getdialogmap();
     Map<String,RadioService>    getradiomap();
     Map<String,SelectService>   getselectmap();
     Map<String,TextService>     gettextmap();
     Map<String,UploadService>   getuploadmap();
     Map<String, SwitchToService> getFramemap();
     int updatePageInfoById(String tid,String pid,String pagename,String pagetitle);
     int addPage(String item,String pagename,String pagetitle);
     int removePage(String id);
     boolean isOwnPage(String pid,String tid);
     boolean isOwnEle(String pid,String eid);
     int removeEle(String id);
     int addEle(Element element,String user,String pid);
     int updateEle(Element element,String user,String eid);




}
