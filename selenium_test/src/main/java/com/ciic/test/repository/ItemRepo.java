package com.ciic.test.repository;

import com.ciic.test.bean.item;
import com.ciic.test.exception.NotFoundException;
import com.ciic.test.service.ItemService;
import com.ciic.test.tools.mycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lixuecheng on 2017/10/24.
 */
@Service
public class ItemRepo implements ItemService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<item> getItemByUid(String uid) {
        List<item> li=    jdbcTemplate.query("select * from item where isused=1 and id in (select tid from u2t where uid=?)", mycode.getArrayObj(uid),new BeanPropertyRowMapper<>(item.class));
       if(li.size()>0){
           return li;
       }else {
           throw  new NotFoundException("用户对应的项目","");
       }

    }

    @Override
    public List<item> getItems() {
        return jdbcTemplate.query("select * from item ",new BeanPropertyRowMapper<>(item.class));

    }

    @Override
    public item getItemById(String id) {
        return null;
    }

    @Override
    public List<item> addItem(String name, String url) {
        return null;
    }

    @Override
    public List<item> deleteItemByid(String id) {
        return null;
    }

    @Override
    public List<item> updateItem(String name, String url, String id) {
        return null;
    }
}
