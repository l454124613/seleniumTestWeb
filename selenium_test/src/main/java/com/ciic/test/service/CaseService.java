package com.ciic.test.service;

import com.ciic.test.bean.*;

import java.util.List;

/**
 * Created by lixuecheng on 2017/7/10.
 */
public interface CaseService {
    List<Thread> getCases();

List<CaseInfo> getcase(String tid,boolean isall);

int updateCanRunCase(String id);
int updateCantRunCase(String id);

int updatecase(String id ,String name ,String des,String important);

int removeCase(String id);
int finishCasehome(String id);
int addCase(String name ,String des,String important,String tid,String type);

List<CaseInfo> getOnecase(String id);

int removeStep(String id);

String getPid(String sid);
String getTopage(String sid);

int copyCase(String mid,String yid);

void zhengliStep(String cid);

String getNowPage(String sid);

String getPid4Case(String cid);

List<Expected> getExpected(String sid);
int removeExp(String sid);
int addExp(String type,String sid,String a,String b,String c,String d,String e);
int updateExp(String type,String sid,String a,String b,String c,String d,String e);

List<Precondition> getPrecondition(String cid);
int updatePrecondition(String type,String cid ,String a,String b,String c);

//run

//    int addCase2RunOneCase(String cid,String tid);
//    int reomveCase4RunOneCase(String cid);
//    boolean isRuningOneCase(String cid);
//    boolean isRuning(String tid);
//    int addRuncase2resOneCase(String cid,String sid,String pic,String word,String res,String restext);
//    int removeCaseRes(String cid);
//
//    int updateCaseStatus(String cid,String status);
//
//    List<Caseres> getCaseres(String cid,String type,String series);

    int addRunCase(String series,String cids,String tid,String type,String uid);
    List<Series> getOneSeries(String series, String tid);
    List<Series> getFinishSeries(String series);

    List<CaserenNum> getCaseresNum(String seriesid);
    List<Caseres>  getCaseres(String seriesid);

    int updateOneseriesStatus(String status,String ordertime,String id);

    List<Series> getSeries( String tid,String uid);
    int removeSeries(String id);



    boolean isRuning(String tid);

    Thread startrun(String tid);
    void stopRun(String seid);

    int addCaseHome(String name,String des,String tid);
    int updateCaseHome4cids(String cids,String id);
    int setCaseHome(String id,String tid);
    List<CaseHome> getCaseHome(String tid);
    int removeCaseHome(String id);
    int updateCaseHome(String id,String name,String des);

List<CaseresList> getCaseresList(String seriesid);

int updateLabel(String id ,String labels);

int addHttpCase(String tid);
int updateHttpCase(String type,String url,String con,String eq,String value,String cid);
    List<HttpCase> getHttpCase(String cid);

    void test(String cid);











}
