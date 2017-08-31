package com.ciic.test.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by lixuecheng on 2017/7/5.
 */
@Aspect
@Configuration
public class aopLogin {

    @Pointcut("execution(* com.ciic.test.controller..*(..)) && !execution(* com.ciic.test.controller.Urls.login(..))")
    public void  execaop(){};

    @Around("execaop()")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint) throws IOException {



        HttpServletResponse response = null;
        Object result = null;
        boolean isparam=true;
        for(Object param : thisJoinPoint.getArgs()){
            if(param==null){
                isparam=false;
                break;
            }else {

            }


        }
       // System.out.println(Arrays.asList(thisJoinPoint.getArgs()));
//        for (Object param : thisJoinPoint.getArgs()) {
//            if (param instanceof HttpServletResponse) {
//                response = (HttpServletResponse) param;
//                break;
//            }
//        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
       // HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
       // HttpSession session = request.getSession();
//        Enumeration e1=session.getAttributeNames();
//        while (e1.hasMoreElements()) {
//            Object o = e1.nextElement();
//            System.out.println(o);
//        }

        boolean a=session.getAttributeNames().hasMoreElements();
        if(session.getAttributeNames().hasMoreElements()
                &&session.getAttribute(session.getAttributeNames().nextElement())!=null){
            try {
             //   System.out.println(1);
                if (isparam){
                    result = thisJoinPoint.proceed();
                }else {
                    result="{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }
            return result;
        } else{

         //   System.out.println(2);
           // logger.debug("Session已超时，正在跳转回登录页面");
           // System.out.println("Session已超时，正在跳转回登录页面");
            result="{\"isok\":3,\"msg\":\"登录超时\",\"to\":\"/\"}";
         //   System.out.println(request.getContextPath());
           // System.out.println(response);
         //   response.sendRedirect("");
        }

//        System.out.println(1);
//        try {
//            thisJoinPoint.proceed();
//            System.out.println(2);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
        return result;

    }
}
