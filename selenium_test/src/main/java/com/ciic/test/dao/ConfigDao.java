package com.ciic.test.dao;

import com.ciic.test.bean.*;
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
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Override
    public List<Log> getActLog() {
        return jdbcTemplate.query("SELECT log.id id ,log.user user, log,time,name  from log join user on uid=user.id where type=2 order by id desc ",new BeanPropertyRowMapper<>(Log.class));
    }

    @Override
    public List<Log> getsLog() {
        return jdbcTemplate.query("SELECT log.id id ,log.user user, log,time,name,log.status status  from log join user on uid=user.id where type=1 order by id desc",new BeanPropertyRowMapper<>(Log.class));

    }

    @Override
    public int updateLogStatus(String lid) {
        return jdbcTemplate.update("UPDATE log set status=2 where id=?",new Object[]{lid});
    }

    @Override
    public List<FileInfo> getfile(String tid) {
        return jdbcTemplate.query("select file.id,file.name name,size,user.name user ,file.time time   from file join user on user.id=file.uid where  file.isused=1 and user.isused =1 and tid =?",new Object[]{tid},new BeanPropertyRowMapper<>(FileInfo.class));
    }

    @Override
    public int addFile(String name, String size, String uid, String path, String tid) {
        return jdbcTemplate.update("INSERT INTO \"file\" ( \"name\", \"size\", \"uid\", \"path\", \"isused\", \"tid\", \"time\") VALUES (?, ?, ?, ?, 1, ?,'"+ LocalDate.now()+ " "+ LocalTime.now()+"')",
                mycode.prase(new Object[]{name,size,uid,path,tid}));
    }

    @Override
    public int updateFile(String name, String size, String uid, String path, String id) {
        return jdbcTemplate.update("UPDATE \"file\" SET  \"name\"=?, \"size\"=?, \"uid\"=?, \"path\"=? ,time='"+LocalDate.now()+ " "+ LocalTime.now()+"' WHERE (\"id\" = ?)",mycode.prase(new Object[]{name,size,uid,path,id}));
    }

    @Override
    public int reomveFile(String id) {
        return jdbcTemplate.update("update file set isused=0 where id=?",new Object[]{id});
    }

    @Override
    public List<tmp> getOneFile(String id, String name) {
        return jdbcTemplate.query("select path value from file where isused=1 and name = ? and id=?",new Object[]{name,id},new BeanPropertyRowMapper<>(tmp.class));
    }

    @Override
    public List<tmp> getUrls() {
        return  jdbcTemplate.query("select url value from excepturl where tid=11 and isused=1",new BeanPropertyRowMapper<>(tmp.class));

    }

    @Override
    public List<tmp> getExceptUrls(String tid) {
        return jdbcTemplate.query("select id value,url value2 from excepturl where tid=? and isused=1",new Object[]{tid},new BeanPropertyRowMapper<>(tmp.class));
    }

    @Override
    public int addExceptUrl(String url, String tid) {
        return jdbcTemplate.update("INSERT INTO excepturl ( \"url\", \"tid\", \"isused\") VALUES ( ?, ?, 1)",mycode.prase2(new Object[]{url,tid}));

    }

    @Override
    public int updateExceptUrl(String url, String id) {
        return jdbcTemplate.update("UPDATE excepturl SET \"url\"=?   WHERE id=?",mycode.prase2(new Object[]{url,id}));
    }

    @Override
    public int removeExceptUrl(String id) {
        return jdbcTemplate.update("UPDATE excepturl SET  \"isused\"=0   WHERE id=?",new Object[]{id});
    }

    private String set2String(Set set){
        final String[] a = {""};
        set.forEach(k->{
            a[0] +=","+k;

        });
        return a[0].substring(1);
    }
}
