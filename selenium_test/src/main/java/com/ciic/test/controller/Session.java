package com.ciic.test.controller;

import com.ciic.test.bean.User;
import com.ciic.test.exception.NotFoundException;
import com.ciic.test.rest.RestResultResponse;
import com.ciic.test.service.UserService;
import com.ciic.test.tools.mycode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(method= RequestMethod.POST,consumes ={ "application/json"}, produces={ "application/json"} )
    Object login(HttpSession session, String user) throws UnsupportedEncodingException, IllegalArgumentException {
        String us= mycode.decode(URLDecoder.decode(user,"utf8"));
        //格式是：账号！！！密码
        String[] us2=us.split("!!!");

        if(mycode.hasEmpty(us2)){
            throw new IllegalArgumentException("获得的参数不全");
        }else {
          User user1=  userService.getUserByEmail(us2[0]);
            if (user1.getPassword().equals(us2[1])) {
                session.setAttribute(user1.getId(),us2[0]);
                return user1;
            }else {
                throw new NotFoundException("用户","email="+us2[0]);
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
          return new RestResultResponse(true,"","");
      }

        //   response.sendRedirect("");
//        return new RestResultResponse(true,"","");

    }

}
