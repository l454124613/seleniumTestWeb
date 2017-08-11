package com.ciic.test.dao;

import com.ciic.test.bean.tmp;
import com.ciic.test.bean.user;
import com.ciic.test.service.UserService;
import com.ciic.test.tools.mycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by lixuecheng on 2017/7/4.
 */
@Service
public class userdao implements UserService {

@Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<user> selectUser(String email, String password) {
        //return (user) jdbcTemplate.query("SELECT * from \"main\".\"user\" LIMIT 1",new BeanPropertyRowMapper(user.class)).get(0);
        return jdbcTemplate.query("SELECT * from \"main\".\"user\"  where email=? and password=? and isused=1",mycode.prase(new Object[]{email,password}),new BeanPropertyRowMapper<user>(user.class));
    }

    @Override
    public int isUsingUser(String email, String password) {
       int a=  jdbcTemplate.update("UPDATE   \"main\".\"user\"  set lastlogintime='"+ LocalDate.now()+" "+LocalTime.now()+"'  where email=? and password=? and isused=1",mycode.prase(new Object[]{email,password}));

        return a;
    }

    @Override
    public List<user> getUser() {
      return   jdbcTemplate.query("select * from user",new BeanPropertyRowMapper<user>(user.class));
    }

    @Override
    public int adduser(String name,String email,String password,String manage) {
      return   jdbcTemplate.update("INSERT INTO \"main\".\"user\" ( \"name\", \"email\", \"password\",  \"ismanager\" ) VALUES ( ?, ?,?,  ?)",mycode.prase(new Object[]{name,email,password,manage}));

    }

    @Override
    public int jinyongUser(String id) {
      return   jdbcTemplate.update("UPDATE user set isused=0 where id=?",mycode.prase(new Object[]{id}));

    }

    @Override
    public boolean isManager(String id) {

       List<tmp>  lt=jdbcTemplate.query("select 1 from user where id=? and ismanager=1",mycode.prase(new Object[]{id}),new BeanPropertyRowMapper<tmp>(tmp.class));
       if(lt.size()==1){
           return true;
       }else
        return false;
    }

    @Override
    public int updateUser(String name, String email, String password, String manage, String id) {

        return jdbcTemplate.update("UPDATE \"main\".\"user\" SET  \"name\"=?, \"email\"=?, \"password\"=?, \"ismanager\"=?, \"isused\"=1  WHERE (\"id\"=?)",mycode.prase(new Object[]{name,email,password,manage,id}));

    }

    @Override
    public int updateUserNopass(String name, String email, String manage, String id) {
        return jdbcTemplate.update("UPDATE \"main\".\"user\" SET  \"name\"=?, \"email\"=?,  \"ismanager\"=?, \"isused\"=1 WHERE (\"id\"=?)",mycode.prase(new Object[]{name,email,manage,id}));

    }

    @Override
    public List<user> getoneuser(String id) {
        return jdbcTemplate.query("select * from user where isused=1 and id=?", mycode.prase(new Object[]{id}),new BeanPropertyRowMapper<user>(user.class));
    }
}
