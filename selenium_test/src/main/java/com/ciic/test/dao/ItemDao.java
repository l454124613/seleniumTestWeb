package com.ciic.test.dao;

import com.ciic.test.bean.*;
import com.ciic.test.service.ItemService;
import com.ciic.test.tools.mycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by lixuecheng on 2017/7/14.
 */
@Service
public class ItemDao implements ItemService{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<item> getItem(String uid) {



        return jdbcTemplate.query("SELECT id,name from item where id in (SELECT gid from u2g where uid=?) and isused=1", mycode.prase(new Object[]{uid}),new BeanPropertyRowMapper<item>(item.class));
    }

    @Override
    public List<item> getAllItem() {
        List<item> li=    jdbcTemplate.query("select * from item",new BeanPropertyRowMapper<item>(item.class));
        for (int i = 0; i <li.size() ; i++) {
            li.get(i).setUsers(getItemUser(li.get(i).getId()));





        }
        return li;

    }

    @Override
    public List<Page> getPage(String tid) {
                return jdbcTemplate.query("SELECT id,pagename,pagetitle from page where isused=1 and tid=?",mycode.prase(new Object[]{tid}),new BeanPropertyRowMapper<Page>(Page.class));

    }

    @Override
    public List<Page> getOnePage(String tid, String title) {
      return   jdbcTemplate.query("select * from page where tid=? and pagetitle=? and isused=1",mycode.prase(new Object[]{tid,title}),new BeanPropertyRowMapper<Page>(Page.class));
    }

    @Override
    public List<Element> getele4page(String pid) {

        return  jdbcTemplate.query("select * from element where pid= ? and isused=1",mycode.prase(new Object[]{pid}),new BeanPropertyRowMapper<Element>(Element.class));

    }

    @Override
    public int addItem(String name,String url) {

    return  jdbcTemplate.update("INSERT INTO \"item\" ( \"name\",   \"firstpageurl\",\"createtime\") VALUES ( ?,  ?, '"+ LocalDate.now()+" "+ LocalTime.now()+"')",mycode.prase(new Object[]{name,url}));



}





    @Override
    public int removeItem(String tid) {
      return   jdbcTemplate.update("UPDATE item set isused=0 where id=?",mycode.prase(new Object[]{tid}));


    }

    @Override
    public boolean isOwnItem(String uid, String tid) {
      List<tmp> lt=  jdbcTemplate.query("SELECT 1 from u2g where uid=? and gid=?",mycode.prase(new Object[]{uid,tid}),new BeanPropertyRowMapper<tmp>(tmp.class));
      if(lt.size()==1){
          return  true;
      }else
      return false;
    }



    @Override
    public int getmaxitemid() {
        List<tmp> lt= jdbcTemplate.query("select max(id) value from item ",new BeanPropertyRowMapper<tmp>(tmp.class));
        if(lt.size()==1){

            return Integer.parseInt(lt.get(0).getValue());
        }else{
            return  0;
        }
    }

    @Override
    public int addItemUser(String user,String id ) {
      return   jdbcTemplate.update("INSERT INTO \"u2g\" ( \"gid\", \"uid\") VALUES (?, ?)",mycode.prase(new Object[]{id,user}));


    }

    @Override
    public int removeItemUser(String id) {
      return   jdbcTemplate.update("DELETE from u2g where gid=?",mycode.prase(new Object[]{id}));

    }

    @Override
    public int updateItem(String name, String url, String id) {
     return    jdbcTemplate.update("UPDATE \"item\" SET  \"name\"=?, \"firstpageurl\"=?, \"isused\"=1 WHERE (\"id\"=?)",mycode.prase(new Object[]{name,url,id}));
    }

    @Override
    public List<user> getItemUser(String id) {
       return jdbcTemplate.query("SELECT * from user   where id in (SELECT uid from u2g where gid= ?)",mycode.prase(new Object[]{id}),new BeanPropertyRowMapper<user>(user.class));
    }

    @Override
    public int addtitle(String title) {
        return jdbcTemplate.update("UPDATE item set title=? where id=(select max(id) value from item)",mycode.prase(new Object[]{title}));


    }

    @Override
    public String gerTitle(String id) {
      List<tmp> ll=  jdbcTemplate.query("Select title value from item where isused=1 and id =?",mycode.prase(new Object[]{id}),new BeanPropertyRowMapper<tmp>(tmp.class));
       if(ll.size()==1){
           return ll.get(0).getValue();
       }else {
           return "err";
       }

    }

    @Override
    public String getItemUrl(String tid) {

       List<tmp> ll= jdbcTemplate.query("SELECT firstpageurl value from item WHERE id=? and isused=1",mycode.prase(new Object[]{tid}),new BeanPropertyRowMapper<tmp>(tmp.class));
    if (ll.size()>0){
         return ll.get(0).getValue();
    }else
        return "err";

    }

    @Override
    public List<Step> getStep(String cid) {
     return   jdbcTemplate.query("SELECT * from step where isused=1 and cid=? order by step",mycode.prase(new Object[]{cid}),new BeanPropertyRowMapper<Step>(Step.class));
    }

    @Override
    public int addStep(String step, String type, String catid, String cid, String value, String eid, String ename) {
     int a=    jdbcTemplate.update("INSERT INTO \"step\" ( \"type\", \"step\", \"catid\",  \"cid\", \"value\", \"eid\", \"ename\") VALUES (?,?,?,?,?,?,?);",mycode.prase(new Object[]{type,step,catid,cid,value,eid,ename}));
        if(a==1){
              return jdbcTemplate.update("INSERT INTO \"exp\" (\"type\", \"a\", \"b\", \"c\", \"d\", \"e\",  \"sid\") VALUES (4, -1, -1, -1, -1, -1, (SELECT max(id) from step where isused=1 and cid=?) )",mycode.prase(new Object[]{cid}));

        }else {
            return 0;
        }

    }

    @Override
    public int updateStep(String id, String type, String catid, String value, String eid, String ename) {
         return jdbcTemplate.update("UPDATE \"step\" SET  \"type\"=?, \"catid\"=?,   \"value\"=?, \"eid\"=?, \"ename\"=? WHERE (\"id\"=?)",mycode.prase(new Object[]{type,catid,value,eid,ename,id}));

    }
}
