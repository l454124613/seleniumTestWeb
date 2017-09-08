package com.ciic.test.controller;

import org.apache.xmlbeans.InterfaceExtension;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created by lixuecheng on 2017/7/5.
 */
@Aspect
@Configuration
public class aopLogin {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Pointcut("execution(* com.ciic.test.controller..*(..)) && !execution(* com.ciic.test.controller.Urls.login(..))")
    public void  execaop(){};

    @Around("execaop()")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint) throws IOException {



        HttpServletResponse response = null;
        long time1=System.currentTimeMillis();

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

   //     boolean a=session.getAttributeNames().hasMoreElements();
        String user="system";
        String aa="";
        String uid="0";
        boolean islog=false;
        if(session.getAttributeNames().hasMoreElements()
                &&session.getAttribute(session.getAttributeNames().nextElement())!=null){
            try {
                uid=session.getAttributeNames().nextElement();
                user=session.getAttribute(uid).toString();
                MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
                Method method = signature.getMethod(); //获取被拦截的方法
                String methodName = method.getName(); //获取被拦截的方法名
               // System.out.println(methodName);

                switch(methodName){
                    case "getuser":aa="获得用户信息";break;
                    case "additem":aa="添加项目";break;
                    case "adduser":aa="添加用户";break;
                    case "removeuser":aa="禁用用户";break;
                    case "removeitem":aa="禁用项目";break;
                    case "test":aa="测试";break;
                    case "logout":aa="登出";break;
                    case "comment":aa="刷新页面";break;
                    case "getItem":aa="获得用户所属项目信息";break;
                    case "getAllItem":aa="获得所有项目信息";break;
                    case "getPage":aa="获得页面信息";break;
                    case "getEle":aa="获得元素信息";break;
                    case "addPage":aa="添加页面";break;
                    case "addEle":aa="添加元素";break;
                    case "removePage":aa="删除页面";break;
                    case "removeEle":aa="删除元素";break;
                    case "getLabel":aa="获得标签";break;
                    case "getUsedLabel":aa="获得已使用的标签";break;
                    case "getSeriesAndCase":aa="查看运行结果";break;
                    case "getcase":aa="获得用例信息";break;
                  //  case "getCasereslist":aa="查看运行情况";break;
                    case "getstep":aa="获得操作步骤信息";break;
                    case "getDatasource":aa="获得数据库信息";break;
                    case "getExp":aa="获得期望结果信息";break;
                    case "getPrecondition":aa="获得预置条件信息";break;
                    case "updateExp":aa="修改期望结果";break;
                    case "updatePrecondition":aa="修改预置条件";break;
                    case "getDatasourceConnect":aa="测试数据库连通性";break;
                 //   case "getSeries":aa="查看系列信息";break;
                  //  case "getpid":aa="获得用户信息";break;
                 //   case "gettopage":aa="获得用户信息";break;
                    case "getCaseHome":aa="获得用例小库信息";break;
                    case "getHttpCase":aa="获得接口请求用例信息";break;
                    case "testCase":aa="调试用例";break;
                    case "addSeries":aa="添加系列";break;
                    case "removeCaseHome":aa="删除用例小库";break;
                    case "updateCids":aa="修改小库用例";break;
                    case "addCaseHome":aa="添加用例小库";break;
                    case "addLabel":aa="添加标签";break;
                    case "clearIsused":aa="清除无用数据";break;
                    //case "geteleforpage":aa="获得用户信息";break;
                    case "addstep":aa="添加操作步骤";break;
                    case "fixstep":aa="修改操作步骤";break;
                    case "updateLabel":aa="修改标签信息";break;
                    case "updateHttp":aa="修改接口请求";break;
                    case "addcase":aa="添加用例信息";break;
                    case "addDatasource":aa="添加数据库";break;
                    case "removeCase":aa="删除用例";break;
                    case "removeLabel":aa="删除标签";break;
                    case "removeSeries":aa="删除运行用例系列";break;
                    case "runSeries":aa="运行用例系列";break;
                    case "stopSeries":aa="停止用例系列";break;
                    case "pauseSeries":aa="暂停用例系列";break;
                    case "removeDatasource":aa="删除数据库";break;
                    case "removestep":aa="删除操作步骤";break;

                }

             //   System.out.println(1);
                if (isparam){
                    result = thisJoinPoint.proceed();


                }else {
                    result="{\"isok\":1,\"msg\":\"参数获取不全\",\"to\":\"/\"}";
                }

            } catch (Throwable e) {
               // e.printStackTrace();
                String rr="请求参数："+Arrays.asList(thisJoinPoint.getArgs());
                if(e.getCause()==null){
                    rr+="报错信息："+e.getLocalizedMessage();
                }else {
                    rr+="报错信息："+e.getCause().getLocalizedMessage();
                }


                long t1=System.currentTimeMillis()-time1;
                jdbcTemplate.update("INSERT INTO \"log\" ( \"user\",\"uid\", \"type\", \"log\", \"time\", \"data\", \"usingtime\") VALUES ('"+user+"',"+uid+", 1, '"+aa+" 出错信息"+"', '"+ LocalDate.now()+" "+ LocalTime.now()+"',?,'"+t1+"毫秒' )",new  Object[]{rr});
islog=true;
            }
            if(aa.length()>0&&!islog){
                String rr="请求参数："+Arrays.asList(thisJoinPoint.getArgs());
                rr+="响应结果："+result;

                long t1=System.currentTimeMillis()-time1;
                jdbcTemplate.update("INSERT INTO \"log\" ( \"user\",\"uid\", \"type\", \"log\", \"time\", \"data\", \"usingtime\") VALUES ('"+user+"',"+uid+", 2, '"+aa+"', '"+ LocalDate.now()+" "+ LocalTime.now()+"', ?,'"+t1+"毫秒' )",new  Object[]{rr});

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
        if(aa.length()>0){
            String rr="请求参数："+Arrays.asList(thisJoinPoint.getArgs());
            rr+="响应结果："+result;

            long t1=System.currentTimeMillis()-time1;
            jdbcTemplate.update("INSERT INTO \"log\" ( \"user\",\"uid\", \"type\", \"log\", \"time\", \"data\", \"usingtime\") VALUES ('"+user+"',"+uid+", 2, '"+aa+"', '"+ LocalDate.now()+" "+ LocalTime.now()+"',?,'"+t1+"毫秒' )",new  Object[]{rr});

        }


        return result;

    }
}
