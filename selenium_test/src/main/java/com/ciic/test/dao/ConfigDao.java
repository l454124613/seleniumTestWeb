package com.ciic.test.dao;

import com.ciic.test.bean.Datasource;
import com.ciic.test.bean.Label;
import com.ciic.test.bean.tmp;
import com.ciic.test.service.ConfigService;
import com.ciic.test.tools.mycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lixuecheng on 2017/8/1.
 */
@Service
public class ConfigDao implements ConfigService {

    @Autowired
   private JdbcTemplate jdbcTemplate;

    private Connection con;

    @Override
    public int addDataspource(String name, String des, String type, String link,String dataname, String user,String pass, String tid) {
        return jdbcTemplate.update("INSERT INTO \"datasource\" (\"name\", \"des\", \"type\", \"link\", \"pass\", \"tid\", \"user\", \"dataname\") VALUES (?,?,?,?,?,?,?,?)", mycode.prase(new Object[]{name,des,type,link,pass,tid,user,dataname}));
    }

    @Override
    public int updateDatasource(String id, String name, String des, String type, String link,String dataname,String user, String pass) {
        return jdbcTemplate.update("UPDATE \"datasource\" SET  \"name\"=?, \"des\"=?, \"type\"=?, \"link\"=?, \"pass\"=?, \"user\"=?, \"dataname\"=?  WHERE (\"id\" =?)",mycode.prase(new Object[]{name,des,type,link,pass,user,dataname,id}));
    }

    @Override
    public int removeDatasource(String did) {
        return jdbcTemplate.update("update datasource set isused=0 where id=?",mycode.prase(new Object[]{did}));
    }

    @Override
    public List<Datasource> getDatasource(String tid) {
        return jdbcTemplate.query("select * from datasource where tid=? and isused=1",mycode.prase(new Object[]{tid}),new BeanPropertyRowMapper<Datasource>(Datasource.class));
    }

    @Override
    public String connectDatasource(String did) {
        List<Datasource> ld=  jdbcTemplate.query("select * from datasource where id=?",mycode.prase(new Object[]{did}),new BeanPropertyRowMapper<Datasource>(Datasource.class));
        if(ld.size()==1){
            ConnectDatasource connectDatasource=   new ConnectDatasource(ld.get(0).getLink(),ld.get(0).getDataname(),ld.get(0).getUser(),ld.get(0).getPass(),ld.get(0).getType());
          con=  connectDatasource.Connection();
            return connectDatasource.getConnectRes();

        }else {
            return "无此数据库";
        }

    }

    @Override
    public String[] updateDate(String data) {
        int a=0;
        String e1="";
        try {
             a=con.createStatement().executeUpdate(data);

        } catch (DataAccessException e) {
            e1=e.getCause().getLocalizedMessage();
        }finally {
            return new String[]{a+"",e1};

        }
    }

    @Override
    public ResultSet selectData(String sql) throws SQLException {
         return   con.createStatement().executeQuery(sql);

    }

    @Override
    public int clearisused() {
       int a= jdbcTemplate.update("DELETE from caselist where isused=0");
       int b= jdbcTemplate.update("DELETE from datasource where isused=0");
       int c= jdbcTemplate.update("DELETE from element where isused=0");
       int d= jdbcTemplate.update("DELETE from page where isused=0");
       int e= jdbcTemplate.update("DELETE from step where isused=0");
       int f= jdbcTemplate.update("DELETE from casehome where isused=0");
       int g= jdbcTemplate.update("DELETE from exp where sid not in (select id from step where isused=1)");
       int h= jdbcTemplate.update("DELETE from httpcase where cid not in (SELECT id from caselist where isused=1)");
       int i= jdbcTemplate.update("DELETE from label where isused=0 ");
       int j= jdbcTemplate.update("DELETE FROM precondition where cid not in (SELECT id from caselist WHERE isused=1)");
       int k= jdbcTemplate.update("DELETE from series where isused =0");


        return a+b+c+d+e+f+g+h+i+j+k;
    }

    @Override
    public int addLabel(String name, String des, String tid) {
        return jdbcTemplate.update("INSERT INTO \"label\" ( \"name\", \"des\",  \"tid\") VALUES ( ?, ?,  ?)",mycode.prase(new Object[]{name,des,tid}));
    }

    @Override
    public int updateLabel(String id, String name, String des) {
        return jdbcTemplate.update("UPDATE \"label\" SET  \"name\"=?, \"des\"=? WHERE (\"id\" = ?)",mycode.prase(new Object[]{name,des,id}));
    }

    @Override
    public int removeLabel(String id) {
        return jdbcTemplate.update("UPDATE \"label\" SET  isused=0 where id=?",new Object[]{id});
    }

    @Override
    public List<Label> getLabel(String tid) {
        return jdbcTemplate.query("select * from label where tid=? and isused=1",new Object[]{tid},new BeanPropertyRowMapper<>(Label.class));
    }

    @Override
    public List<Label> getUsedLabel(String tid) {
        List<tmp> lt= jdbcTemplate.query("SELECT label value from caselist where tid=? and label is not null",new Object[]{tid},new BeanPropertyRowMapper<>(tmp.class));
        Set<String> set =new HashSet<String>();
        for (tmp t:lt){
          String a[]=  t.getValue().split(",");
          for (String b:a){
              set.add(b);
          }
        }
        if(set.size()>0){
         //  set2String(set)
         return   jdbcTemplate.query("select * from label where id in ("+set2String(set)+")",new BeanPropertyRowMapper<>(Label.class));

        }else {
            return new ArrayList<>();
        }



    }

    private String set2String(Set set){
        final String[] a = {""};
        set.forEach(k->{
            a[0] +=","+k;

        });
        return a[0].substring(1);
    }
}
