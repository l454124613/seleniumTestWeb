package com.ciic.test.service;

import com.ciic.test.bean.*;

import java.util.List;
import java.util.Map;


/**
 * Created by lixuecheng on 2017/7/13.
 */
public interface ItemService {
    int addCaseVer(String ti,String des,String id);
    List<item> getItem(String uid);
    Map<String,List<CaseVer>> getVer();
    List<item> getAllItem();
    List<Page> getPage(String tid);
    List<Page> getOnePage(String tid,String title);
    List<Page> getOnePageById(String id);
    List<Element> getele4page(String pid);
    int addItem(String name,String url);
    int removeItem(String tid);
    boolean isOwnItem(String uid,String tid);

    int getmaxitemid();
    int addItemUser(String user,String id);
    int removeItemUser(String id);
    int updateItem(String name,String url,String id);
    List<user> getItemUser(String id);
    String getName(String id);
    int addtitle(String title);
    String gerTitle(String id);



    String getItemUrl(String tid);
    List<Step> getStep(String cid);
    int addStep(String step,String type,String catid,String cid,String value,String eid,String ename);
    int updateStep(String id ,String type,String catid,String value,String eid,String ename);


}
