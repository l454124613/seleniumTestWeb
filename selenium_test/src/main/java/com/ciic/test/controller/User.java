package com.ciic.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.ciic.test.bean.tmp2;
import com.ciic.test.rest.RestResultResponse;
import com.ciic.test.service.ItemService;
import com.ciic.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by lixuecheng on 2017/10/23.
 * 登录登出相关
 */

@RestController
@RequestMapping("/user" )
public class User {
//
    @Autowired
    private UserService userService;

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
    Object getUsers( ){

      return userService.getAllUsers();

    }

    @RequestMapping(value = "userid/{uid}",method= RequestMethod.GET,consumes ={ "application/json"},produces={ "application/json"} )
    Object getItems(@PathVariable String uid){
       // return itemService.getItemByUid(uid);
        return 0;

    }
    @RequestMapping(value = "initem",method= RequestMethod.GET,consumes ={ "application/json"},produces={ "application/json"} )
    Object getUsers4Item(){
        // return itemService.getItemByUid(uid);

        return JSONObject.toJSON(userService.getUser4Item());

    }
}
