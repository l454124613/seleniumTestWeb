package com.ciic.test.bean;

import com.ciic.test.service.GetElementService;
import com.ciic.test.service.pageInfo.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public class Page {
@Autowired
    private GetElementService getElementService;

    private String id;
   private String  pagename;
   private String  pagetitle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getPagetitle() {
        return pagetitle;
    }

    public void setPagetitle(String pagetitle) {
        this.pagetitle = pagetitle;
    }

//    private Map<String,ButtonService>   buttonmap;
//    private Map<String,CheckboxService> checkboxmap;
//    private Map<String,DialogService>   dialogmap;
//    private Map<String,RadioService>    radiomap;
//    private Map<String,SelectService>   selectmap;
//    private Map<String,TextService>     textmap;
//    private Map<String,UploadService>   uploadmap;

    @Override
    public String toString() {
        return "{" +
                "\"pagename\":\"" + pagename + '\"' +
                ", \"pagetitle\":\"" + pagetitle + '\"' +
                ", \"id\":\"" + id + '\"' +
                '}';
    }
//
//    public  void calc(){
//        List<Element> le= getElementService.get(id);
//        buttonmap=new HashMap<>();
//        checkboxmap=new HashMap<>();
//        dialogmap=new HashMap<>();
//        radiomap=new HashMap<>();
//        selectmap=new HashMap<>();
//        textmap=new HashMap<>();
//        uploadmap=new HashMap<>();
//
//
//        for (Element l1:le){
//
//            switch (l1.getType()){
//                case "0": buttonmap.put(l1.getName(),);break;
//                case "1": break;
//                case "2": break;
//                case "3": break;
//                case "4": break;
//                case "5": break;
//                case "6": break;
//                case "7": break;
//                case "8": break;
//            }
//
//        }
//
//    }
}
