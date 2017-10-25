package com.ciic.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.ciic.test.bean.User;
import com.ciic.test.bean.tmp;
import com.ciic.test.exception.NotFoundException;
import com.ciic.test.rest.RestResultResponse;
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
@RequestMapping("/session" )
public class Session {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @return
     */
    @RequestMapping(method= RequestMethod.POST,consumes ={ "application/json"},produces={ "application/json"})
    Object login(HttpSession session, @RequestBody tmp t) throws UnsupportedEncodingException, IllegalArgumentException {
        String us= mycode.decode(URLDecoder.decode(t.getValue(),"utf8"));
        //格式是：账号！！！密码

        JSONObject jsonObject= JSONObject.parseObject(us);
       // User user1=JSONObject.toJavaObject(jsonObject, User.class);
       String em= jsonObject.getString("email");
       String pa= jsonObject.getString("password");


        if(mycode.hasEmpty(em,pa)){
            throw new IllegalArgumentException("获得的参数不全");
        }else {
          User user1=  userService.getUserByEmail(em);
            if (user1.getPassword().equals(pa)) {
                userService.updateLastTime(user1.getId());
                session.setAttribute(user1.getId(),em);
                return user1;
            }else {
                throw new NotFoundException("用户","email="+em);
            }
        }

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

}
