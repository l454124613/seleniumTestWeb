package com.ciic.test.dao;

import com.ciic.test.bean.*;
import com.ciic.test.service.CaseService;
import com.ciic.test.service.GetCase;

import com.ciic.test.service.SeleniumService;
import com.ciic.test.tools.mycode;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by lixuecheng on 2017/7/10.
 */
@Service
public class Casesdao implements CaseService {

    private String seidStop="0";

@Autowired
    private GetCase getCase;
@Autowired
private JdbcTemplate jdbcTemplate;
@Autowired
private SeleniumService seleniumService;

private boolean iskeep=false;

    public Casesdao() {

    }

    @Override
    public List<Thread> getCases() {
            List list=new ArrayList();
        for (int i = 0; i <2 ; i++) {
            list.add(new Thread(getCase));
            
        }
return list;

    }

    @Override
    public List<CaseInfo> getcase(String tid) {
        if (tid.charAt(0)=='t'){
            String id=tid.replace("t","");

            String aac="select cids value  from casehome where id=?";
           List<tmp> lt= jdbcTemplate.query(aac,new Object[]{id},new BeanPropertyRowMapper<tmp>(tmp.class));

           if(lt.size()>0){

               return  jdbcTemplate.query("select * from caselist where id in ("+lt.get(0).getValue()+")" ,new BeanPropertyRowMapper<CaseInfo>(CaseInfo.class));

        }else {
            return new ArrayList<CaseInfo>();
        }

        }else

        return  jdbcTemplate.query("select * from caselist where tid=? and isused=1" ,mycode.prase(new Object[]{tid}),new BeanPropertyRowMapper<CaseInfo>(CaseInfo.class));

    }

    @Override
    public int updatecase(String id, String name, String des, String important) {
        return  jdbcTemplate.update("UPDATE \"main\".\"caselist\" SET  \"name\"=?, \"des\"=?, \"important\"=?  WHERE (\"id\"=?)",mycode.prase(new Object[]{name,des,important,id}));

    }

    @Override
    public int removeCase(String id) {
        return    jdbcTemplate.update("UPDATE caselist set isused=0 where id=?",mycode.prase(new Object[]{id}));

    }

    @Override
    public int addCase(String name, String des, String important,String tid,String type) {
        int n=  jdbcTemplate.update("INSERT INTO \"main\".\"caselist\" ( \"name\", \"des\", \"important\", \"tid\", \"type\") VALUES ( ?, ?, ?, ?,?)",mycode.prase(new Object[]{name,des,important,tid,type}));
        if(n==1){
            int n2=jdbcTemplate.update("INSERT INTO \"precondition\" ( \"type\", \"a\", \"b\", \"c\",\"cid\") VALUES (4, -1, -1, -1, (SELECT max(id) from caselist where isused=1 and tid=?))",mycode.prase(new Object[]{tid}));
            int n3=0;
            if(type.equals("2")){
                 n3=addHttpCase(tid);
            }

        if(n2==1&&n3==1){
            return n2;
        }else {
            return 0;
        }
        }else {
            return  0;
        }
    }

    @Override
    public List<CaseInfo> getOnecase(String id) {

        return jdbcTemplate.query("select * from caselist where isused=1 and id=?" ,mycode.prase(new Object[]{id}),new BeanPropertyRowMapper<CaseInfo>(CaseInfo.class));
    }

    @Override
    public int removeStep(String id) {
      return   jdbcTemplate.update("update step set isused=0 where id=?",mycode.prase(new Object[]{id}));
    }

    @Override
    public String getPid(String sid) {
        List<tmp> lt=jdbcTemplate.query("SELECT pid value from element where id=(SELECT eid from step where id=?)",mycode.prase(new Object[]{sid}),new BeanPropertyRowMapper<tmp>(tmp.class));
        if(lt.size()==1){
                return lt.get(0).getValue();
        }else {
            return "";
        }
    }

    @Override
    public String getTopage(String sid) {


        List<tmp> lt=jdbcTemplate.query("SELECT topage value from element where id=(SELECT eid from step where id=?)",mycode.prase(new Object[]{sid}),new BeanPropertyRowMapper<tmp>(tmp.class));
        if(lt.size()==1){
            return lt.get(0).getValue();
        }else {
            return "";
        }
    }

    @Override
    public void zhengliStep(String cid) {
       List<Step> ls= jdbcTemplate.query("select * from step where cid=? order by step",mycode.prase(new Object[]{cid}),new BeanPropertyRowMapper<Step>(Step.class));
       for (int i=0;i<ls.size();i++){
           jdbcTemplate.update("update step set step="+((i+1)*2)+" where id="+ls.get(i).getId());

       }


    }

    @Override
    public String getNowPage(String sid) {

        return null;
    }

    @Override
    public String getPid4Case(String cid) {
        List<tmp> lt=jdbcTemplate.query("select yuid value from caselist where isused=1 and id=?  and value =1",mycode.prase(new Object[]{cid}),new BeanPropertyRowMapper<tmp>(tmp.class));

         if(lt.size()==1){
            List<Element> le=   jdbcTemplate.query("SELECT max(step),element.* from step JOIN element on element.id=step.eid  where cid=(SELECT a from precondition where cid=? and isused=1) and step.isused=1",mycode.prase(new Object[]{cid}),new BeanPropertyRowMapper<Element>(Element.class));
             System.out.println(le);
            if(le.size()==0){
                return "0";
            }else {
                String pa=le.get(0).getTopage();
                if(pa.equals("-1")){
                    return le.get(0).getPid();
                }else {
                    return le.get(0).getTopage();
                }
            }
        }else {
return "0";
        }

    }

    @Override
    public List<Expected> getExpected(String sid) {
        return jdbcTemplate.query("select * from exp where isused=1 and sid=?",mycode.prase(new Object[]{sid}),new BeanPropertyRowMapper<Expected>(Expected.class));
    }

    @Override
    public int removeExp(String sid) {
        int n= jdbcTemplate.update("update exp set isused=0 where sid=?",mycode.prase(new Object[]{sid}));
        if (n==1){
            int n2= jdbcTemplate.update("update step set expid=0 where sid=?",mycode.prase(new Object[]{sid}));
            if(n2==1){
                return  1;
            }else {
                return  0;
            }
        }else {
            return 0;
        }
    }

    @Override
    public int addExp(String type, String sid, String a, String b, String c, String d, String e) {
        int n=jdbcTemplate.update("INSERT INTO \"exp\" (\"type\", \"a\", \"b\", \"c\", \"d\", \"e\",  \"sid\") VALUES (?, ?, ?, ?, ?, ?, ?)",mycode.prase(new Object[]{type,a,b,c,d,e,sid}));
   if (n==1){
       int n2= jdbcTemplate.update("update step set expid=1 where sid=?",mycode.prase(new Object[]{sid}));
       if(n2==1){
           return  1;
       }else {
           return  0;
       }
   }else {
       return 0;
   }
    }

    @Override
    public int updateExp(String type, String sid, String a, String b, String c, String d, String e) {
       // System.out.println(type+a+b+c+d+e+sid);
        if(type.equals("4")||type.equals("0")){
            jdbcTemplate.update("update step set expid=0 where id=?",mycode.prase(new Object[]{sid}));
        }else {
            jdbcTemplate.update("update step set expid=1 where id=?",mycode.prase(new Object[]{sid}));
        }
        return jdbcTemplate.update("UPDATE \"exp\" SET  \"type\"=?, \"a\"=?, \"b\"=?, \"c\"=?, \"d\"=?, \"e\"=?  WHERE (\"sid\" =? and isused=1)",mycode.prase2(new Object[]{type,a,b,c,d,e,sid}));
    }

    @Override
    public List<Precondition> getPrecondition(String cid) {
        return jdbcTemplate.query("select * from precondition where isused=1 and cid=?",mycode.prase(new Object[]{cid}),new BeanPropertyRowMapper<Precondition>(Precondition.class));
    }

    @Override
    public int updatePrecondition(String type, String cid, String a, String b, String c) {
        if(type.equals("4")||type.equals("0")){
            jdbcTemplate.update("update caselist set yuid=? where id=?",mycode.prase(new Object[]{type,cid}));
        }else{
            jdbcTemplate.update("update caselist set yuid=? where id=?",mycode.prase(new Object[]{type,cid}));

        }


        return jdbcTemplate.update("UPDATE \"precondition\" SET  \"type\"=?, \"a\"=?, \"b\"=?, \"c\"=?  WHERE (\"cid\" = ?)",mycode.prase2(new Object[]{type,a,b,c,cid}));
    }

//    @Override
//    public int addCase2RunOneCase(String cid,String tid) {
//        return jdbcTemplate.update("INSERT INTO \"runtimecase\" ( \"cid\", \"mark\", \"tid\") VALUES (?,'',?);",mycode.prase(new Object[]{cid,tid}));
//    }

//    @Override
//    public int reomveCase4RunOneCase(String cid) {
//        return jdbcTemplate.update("DELETE from runtimecase where status=1 and cid=?",mycode.prase(new Object[]{cid}));
//    }
//
//    @Override
//    public boolean isRuningOneCase(String cid) {
//       List<tmp>lt= jdbcTemplate.query("select 1 value from runtimecase where status=1 and cid=?",new Object[]{cid},new BeanPropertyRowMapper<tmp>(tmp.class));
//       if(lt.size()>0){
//           return true;
//       }else
//        return false;
//    }

//    @Override
//    public boolean isRuning(String tid) {
//        List<tmp>lt= jdbcTemplate.query("select 1 value from runtimecase where status=1 and tid=?",new Object[]{tid},new BeanPropertyRowMapper<tmp>(tmp.class));
//        if(lt.size()>0){
//            return true;
//        }else
//            return false;
//    }
//
//    //多浏览器，互相影响，区分项目
//    @Override
//    public int addRuncase2resOneCase(String cid, String sid, String pic, String word, String res, String restext) {
//        return jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"type\", \"time\", \"listid\") VALUES (?, ?, ?, ?, ?, ?, 1, "+ LocalDate.now()+" "+ LocalTime.now()+",'')"
//                ,mycode.prase(new Object[]{cid,sid,pic,word,res,restext}));
//    }
//
//    @Override
//    public int removeCaseRes(String cid) {
//        return jdbcTemplate.update("DELETE from caseres where cid=? and type=1",mycode.prase(new Object[]{cid}));
//    }
//
//    @Override
//    public int updateCaseStatus(String cid,String status) {
//        return jdbcTemplate.update("update runtimecase set status=? where cid=?  and status=0",mycode.prase(new Object[]{status,cid}));
//    }
//
//    @Override
//    public List<Caseres> getCaseres(String cid, String type, String series) {
//        return jdbcTemplate.query("select * from caseres where cid=? and type=? and series=?",mycode.prase(new Object[]{cid,type,series}),new BeanPropertyRowMapper<Caseres>(Caseres.class));
//    }

    @Override
    public int addRunCase(String series,String cids,String tid,String type) {
        if(type.equals("1")){
            return jdbcTemplate.update("INSERT INTO \"series\" ( \"series\", \"cids\",  \"status\",   \"tid\", \"type\", \"ordertime\") VALUES (?, ?, '1',?,?, '"+ LocalDate.now()+" "+ LocalTime.now()+"')",mycode.prase(new Object[]{series,cids,tid,type}));
        }else {
            return jdbcTemplate.update("INSERT INTO \"series\" ( \"series\", \"cids\",   \"tid\", \"type\", \"ordertime\") VALUES (?, ?, ?,?, '"+ LocalDate.now()+" "+ LocalTime.now()+"')",mycode.prase(new Object[]{series,cids,tid,type}));
        }

    }

    @Override
    public List<Series> getOneSeries(String series, String tid) {
        return  jdbcTemplate.query("select *  from series where isused=1 and status in ('1','2') and series=? and tid=?",new Object[]{series,tid},new BeanPropertyRowMapper<Series>(Series.class));
    }

    @Override
    public List<Series> getFinishSeries(String series) {
        return jdbcTemplate.query("select * from series where isused=1 and status in (3,4,5) and id=?",new Object[]{series},new BeanPropertyRowMapper<>(Series.class));
    }

    @Override
    public List<CaserenNum> getCaseresNum(String seriesid) {
        return jdbcTemplate.query("SELECT cid ,res,count(1) num from casereslist where  res!='-1' and status=3 and cid in (select cid from casereslist join series on series.id= casereslist.seriesid where series.id=? and series.type=2 GROUP BY cid) group by cid ,res",new Object[]{seriesid},new BeanPropertyRowMapper<>(CaserenNum.class));
    }

    @Override
    public List<Caseres> getCaseres(String seriesid) {
        return jdbcTemplate.query("select * from caseres where listid in (SELECT id from casereslist where seriesid=?)",new Object[]{seriesid},new BeanPropertyRowMapper<>(Caseres.class));
    }

    @Override
    public int updateOneseriesStatus(String status, String ordertime, String id) {
        if(ordertime.equals("")){
            return jdbcTemplate.update("update series set status=? where id=?",new Object[]{status,id});
        }else {
            return jdbcTemplate.update("update series set status=?,ordertime=? where id=?",new Object[]{status,ordertime,id});

        }

    }

    @Override
    public List<Series> getSeries(String tid) {
        return jdbcTemplate.query("select * from series where isused=1 and tid=? order by ordertime desc",new Object[]{tid},new BeanPropertyRowMapper<>(Series.class));
    }

    @Override
    public int removeSeries(String id) {
        return jdbcTemplate.update("update series set isused=0 where id=?",new Object[]{id});
    }

    @Override
    public boolean isRuning(String tid) {

        List<tmp> lt= jdbcTemplate.query("select 1 value from series where isused=1 and tid=? and status=2",new Object[]{tid},new BeanPropertyRowMapper<tmp>(tmp.class));
    if(lt.size()>0){
        return true;
    }else {
        return false;
    }
    }


    @Override
    public Thread startrun(String tid) {
        isNowKeep(tid); //查看是否有等待运行
        Thread  thread=null;
        if(!isRuning(tid)&&iskeep){
              thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    addCase4Run(tid);

                }
            });



        }

return thread;
    }

    @Override
    public void stopRun(String seid) {
        seleniumService.stopRun(seid);
        this.seidStop=seid;


    }


    @Override
    public int addCaseHome(String name, String des, String tid) {
        return jdbcTemplate.update("INSERT INTO \"casehome\" ( \"name\", \"des\",  \"tid\")  VALUES (?, ?, ?)",mycode.prase(new Object[]{name,des,tid}));
    }

    @Override
    public int updateCaseHome4cids(String cids, String id) {
        return jdbcTemplate.update("update casehome set cids=? where isused=1 and id=?",mycode.prase(new Object[]{cids,id}));
    }

    @Override
    public List<CaseHome> getCaseHome(String tid) {
        return jdbcTemplate.query("select * from casehome where isused=1 and tid=?",new Object[]{tid},new BeanPropertyRowMapper<>(CaseHome.class));
    }

    @Override
    public int removeCaseHome(String id) {
        return jdbcTemplate.update("update casehome set isused=0 where id=?",mycode.prase(new Object[]{id}));
    }

    @Override
    public int updateCaseHome(String id, String name, String des) {
        return jdbcTemplate.update("UPDATE \"casehome\" SET  \"name\"=?, \"des\"=?   WHERE (\"id\" = ?)",mycode.prase(new Object[]{name,des,id}));
    }

    @Override
    public List<CaseresList> getCaseresList(String seriesid) {
        return jdbcTemplate.query("SELECT * from casereslist where seriesid=?",new Object[]{seriesid},new BeanPropertyRowMapper<>(CaseresList.class));
    }

    @Override
    public int updateLabel(String id, String labels) {
        return jdbcTemplate.update("UPDATE caselist set label=? where id=?",mycode.prase(new Object[]{labels,id}));
    }

    @Override
    public int addHttpCase(String tid) {
       return jdbcTemplate.update("INSERT INTO \"httpcase\" (\"type\", \"url\", \"con\", \"eq\", \"value\", \"cid\") VALUES (-1, -1, -1, -1, -1, (SELECT max(id) from caselist where type=2 and isused=1 and tid=?))",new Object[]{tid});
    }

    @Override
    public int updateHttpCase(String type, String url, String con, String eq, String value, String cid) {
        return jdbcTemplate.update("UPDATE \"httpcase\" SET \"type\"=?, \"url\"=?, \"con\"=?, \"eq\"=?, \"value\"=? WHERE (\"cid\"=?)",mycode.prase2(new Object[]{type,url,con,eq,value,cid}));
    }

    @Override
    public List<HttpCase> getHttpCase(String cid) {
        return jdbcTemplate.query("select * from httpcase where cid=?",new Object[]{cid},new BeanPropertyRowMapper(HttpCase.class));
    }

    @Override
    public void test(String cid) {
        seleniumService.test(cid);
    }

    private void updateSeriesStime(String seriesid){
        jdbcTemplate.update("update series set sttime='"+LocalDate.now()+" "+LocalTime.now()+"'  where id="+seriesid );
    }
    private void updateSeriesEtime(String seriesid){
        jdbcTemplate.update("update series set etime='"+LocalDate.now()+" "+LocalTime.now()+"'  where id="+seriesid );
    }
private void map2Sql(Map<String,Set<String>> map,String tid){
    jdbcTemplate.update("DELETE from runtimecase where tid="+tid);
        map.forEach((k,v)->{
            String[] y = v.toArray(new String[0]);
            for (int i = 0; i < y.length; i++) {
                jdbcTemplate.update("INSERT INTO \"runtimecase\" (\"cida\", \"cidb\",tid) VALUES ("+k+", "+y[i]+","+tid+")");
                if(map.containsKey(y[i])){
                    map.get(y[i]).forEach(k3->{

                        jdbcTemplate.update("INSERT INTO \"runtimecase\" (\"cida\", \"cidb\",tid) VALUES ("+k+", "+k3+","+tid+")");

                    });

                }

            };



        });



}

    private void orderCases(String cids,String tid,String seriesid){
        List<String> ls=new ArrayList<>();
        List<String> ls2=new ArrayList<>();
        Set sety=new HashSet();
        Map<String,Set<String>> map=new HashMap<>();
            List<tmp3> lc=jdbcTemplate.query("SELECT type value1,a value2,cid value3 from precondition where   cid in ("+cids+")",new BeanPropertyRowMapper<>(tmp3.class));
        for (int i = 0; i <lc.size() ; i++) {
            if(lc.get(i).getValue1().equals("1")) {
                if (map.containsKey(lc.get(i).getValue2())) {
                    map.get(lc.get(i).getValue2()).add(lc.get(i).getValue3());
                } else {
                    Set set = new HashSet();
                    set.add(lc.get(i).getValue3());
                    map.put(lc.get(i).getValue2(), set);
                }
                lc.add(jdbcTemplate.query("SELECT type value1,a value2,cid value3 from precondition where   cid  =" + lc.get(i).getValue2(), new BeanPropertyRowMapper<>(tmp3.class)).get(0));
                sety.add(lc.get(i).getValue2());
            }
              //  lsy.add(lc.get(i).getValue3());
//            }else {
//                ls.add(lc.get(i).getValue3());
//            }
            
        }
        map2Sql(map,tid);
        for (int i = 0; i < lc.size(); i++) {
            if(!sety.contains(lc.get(i).getValue3())){
                ls.add(lc.get(i).getValue3());

            }
        }
        for (int i = 0; i <ls.size() ; i++) {
            List<String> l3=new ArrayList<>();
            boolean iscon=true;
            String cid1=ls.get(i);
            while(iscon){

             List<tmp> l4=   jdbcTemplate.query("select type value ,a value2 from precondition where cid="+cid1, new BeanPropertyRowMapper<>(tmp.class));
            if(l4.size()==1){

                    if(l4.get(0).getValue().equals("1")){
                        l3.add(l4.get(0).getValue2());
                        cid1=l4.get(0).getValue2();


                    }else {
                        break;

                    }


            }else {
                break;
            }
            }
            Collections.reverse(l3);
            try {
            addCase2res(ls.get(i),seriesid,l3.toArray(new String[0]));

            } catch (Exception e) {
                updateOneseriesStatus("4","",seriesid);
                updateSeriesEtime(seriesid);
                continue;
            }


        }




    }

    private void getPreCid(){

    }

    void addCase4Run(String tid){
        //获得等待的系列
        List<Series> ls=   jdbcTemplate.query("SELECT * from series where isused=1 and status=1  and tid =? order by ordertime",new Object[]{tid},new BeanPropertyRowMapper<Series>(Series.class));
          //获得第一个
            Series series= ls.get(0);
            //获得用例
            String cids=series.getCids();
            orderCases(cids,tid,series.getId());



            //修改当前系列开始运行
              updateOneseriesStatus("2","",series.getId());
              updateSeriesStime(series.getId());

              //运行脚本

                seleniumService.run(tid,series.getId());
                //修改当前系列运行结束
        if(series.getId().equals(seidStop)){
            updateOneseriesStatus("5","",series.getId());
        }else {
            updateOneseriesStatus("3","",series.getId());
        }

              updateSeriesEtime(series.getId());







        //检查是否还存在要运行的系列
        isNowKeep(tid); //查看是否有等待运行


        if(!isRuning(tid)&&iskeep){
            addCase4Run(tid);
        }
    }

void addCase2res(String cid,String seriesid,String[] precids) throws Exception {
    List<tmp> lt= jdbcTemplate.query("select type value from caselist where id="+cid ,new BeanPropertyRowMapper<tmp>(tmp.class));

         if( lt.get(0).getValue().equals("1")){
             int a= jdbcTemplate.update("INSERT INTO \"casereslist\" ( \"cid\", \"res\", \"runnum\", \"allnum\", \"cname\", \"cdes\", \"seriesid\", \"status\") SELECT caselist.id cid,-1 res,0 runnum,count(step.id) allnum,name cname,des cdes, "+seriesid+" seriesid,0 from caselist join step on step.cid=caselist.id  where  step.isused=1 and  caselist.isused=1 and caselist.id="+cid);
             if(a>0){
                 lt= jdbcTemplate.query("select id value from casereslist where cid="+cid+" and seriesid = "+seriesid +" order by id desc",new BeanPropertyRowMapper<tmp>(tmp.class));
                 //  System.out.println(lt);
                 for (int i = 0; i <precids.length ; i++) {
                     List<tmp> lt2= jdbcTemplate.query("select type value from caselist where id="+precids[i] ,new BeanPropertyRowMapper<tmp>(tmp.class));
                     if(lt2.get(0).getValue().equals("1")){
                         jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\")  SELECT cid,step.id sid,'' pic,''''||ename||''''||cat.name||value||'_预置条件' word,'-1' res ,'-1' restext ,'' time ,"+lt.get(0).getValue()+" listid from step join cat on cat.id=step.catid where cid="+precids[i]+" ORDER BY step");

                     }else {
                         jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\") SELECT cid,'0' sid,'' pic,'接口请求_预置条件' word,'-1' res,'' restext,'' time,'"+lt.get(0).getValue()+"'  listid from httpcase where cid="+cid);

                     }

                 }
                 jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\")  SELECT cid,step.id sid,'' pic,''''||ename||''''||cat.name||value word,'-1' res ,'-1' restext ,'' time ,"+lt.get(0).getValue()+" listid from step join cat on cat.id=step.catid where cid="+cid+" ORDER BY step");

             }else {
                 throw new Exception("no");
             }
         }else {
            int a=    jdbcTemplate.update("INSERT INTO \"casereslist\" ( \"cid\", \"res\", \"runnum\", \"allnum\", \"cname\", \"cdes\", \"seriesid\", \"status\") SELECT cid,'-1' res,'0' runnum,'1' allnum, name cname,des cdes,'"+seriesid+"' seriesid,0 status from caselist join httpcase on httpcase.cid=caselist.id where httpcase.type !='-1' and  caselist.isused=1  and  caselist.id="+cid);

            if(a>0){
                lt= jdbcTemplate.query("select id value from casereslist where cid="+cid+" and seriesid = "+seriesid +" order by id desc",new BeanPropertyRowMapper<tmp>(tmp.class));

                for (int i = 0; i <precids.length ; i++) {
                    List<tmp> lt2= jdbcTemplate.query("select type value from caselist where id="+precids[i] ,new BeanPropertyRowMapper<tmp>(tmp.class));
                    if(lt2.get(0).getValue().equals("1")){
                        jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\")  SELECT cid,step.id sid,'' pic,''''||ename||''''||cat.name||value||'_预置条件' word,'-1' res ,'-1' restext ,'' time ,"+lt.get(0).getValue()+" listid from step join cat on cat.id=step.catid where cid="+precids[i]+" ORDER BY step");

                    }else {
                        jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\") SELECT cid,'0' sid,'' pic,'接口请求_预置条件' word,'-1' res,'' restext,'' time,'"+lt.get(0).getValue()+"'  listid from httpcase where cid="+cid);

                    }

                }
                jdbcTemplate.update("INSERT INTO \"caseres\" ( \"cid\", \"sid\", \"pic\", \"word\", \"res\", \"restext\", \"time\", \"listid\") SELECT cid,'0' sid,'' pic,'接口请求' word,'-1' res,'' restext,'' time,'"+lt.get(0).getValue()+"'  listid from httpcase where cid="+cid);
            }else {
                throw new Exception("no");
            }
         }

    //System.out.println(cid);



    }

    void isNowKeep(String tid){

            List<tmp> lt=   jdbcTemplate.query("SELECT 1 value from series where isused=1 and status=1  and tid =? order by ordertime",new Object[]{tid},new BeanPropertyRowMapper<tmp>(tmp.class));
            if(lt.size()>0){
                iskeep=true;
            }else {
                iskeep=false;
            }

    }



}
