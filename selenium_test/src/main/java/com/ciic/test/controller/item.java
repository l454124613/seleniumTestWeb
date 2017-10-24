package com.ciic.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.ciic.test.bean.User;
import com.ciic.test.bean.tmp;
import com.ciic.test.bean.tmp2;
import com.ciic.test.exception.NotFoundException;
import com.ciic.test.rest.RestResultResponse;
import com.ciic.test.service.ItemService;
import com.ciic.test.service.UserService;
import com.ciic.test.tools.mycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by lixuecheng on 2017/10/23.
 * 登录登出相关
 */

@RestController
@RequestMapping("/item" )
public class item {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method= RequestMethod.POST,consumes ={ "application/json"},produces={ "application/json"})
    Object addItem( @RequestBody tmp2 t)  {
        return 0;


    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(method= RequestMethod.DELETE,consumes ={ "application/json"},produces={ "application/json"} )
    Object logout(HttpSession session){

        session.removeAttribute(session.getAttributeNames().nextElement());

        //   response.sendRedirect("");
        return new RestResultResponse(true,"","");

    }


    @RequestMapping(value = "/{dd}",method= RequestMethod.PUT,consumes ={ "application/json"},produces={ "application/json"} )
    Object test( @PathVariable String dd){

      if(dd.equals("5")){
          return new RestResultResponse(false,"1","2");
      }else {
          return new RestResultResponse(true,"","123");
      }

        //   response.sendRedirect("");
//        return new RestResultResponse(true,"","");

    }

    @RequestMapping(method= RequestMethod.GET,consumes ={ "application/json"},produces={ "application/json"} )
    Object getItems( ){

      return itemService.getItems();

    }
    @RequestMapping(value = "userid/{uid}",method= RequestMethod.GET,consumes ={ "application/json"},produces={ "application/json"} )
    Object getItems(@PathVariable String uid){
        return itemService.getItemByUid(uid);

    }
}
