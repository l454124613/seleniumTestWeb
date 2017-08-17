package com.ciic.test.service;

import com.ciic.test.bean.*;

import java.util.List;

/**
 * Created by lixuecheng on 2017/7/10.
 */
public interface CaseService {
    List<Thread> getCases();

List<CaseInfo> getcase(String tid);

int updatecase(String id ,String name ,String des,String important);

int removeCase(String id);
int addCase(String name ,String des,String important,String tid);

List<CaseInfo> getOnecase(String id);

int removeStep(String id);

String getPid(String sid);
String getTopage(String sid);

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

    int addRunCase(String series,String cids,String tid,String type);
    List<Series> getOneSeries(String series, String tid);
    List<Series> getFinishSeries(String series);

    List<CaserenNum> getCaseresNum(String seriesid);
    List<Caseres>  getCaseres(String seriesid);

    int updateOneseriesStatus(String status,String ordertime,String id);

    List<Series> getSeries( String tid);
    int removeSeries(String id);



    boolean isRuning(String tid);

    void startrun(String tid);

    int addCaseHome(String name,String des,String tid);
    int updateCaseHome4cids(String cids,String id);
    List<CaseHome> getCaseHome(String tid);
    int removeCaseHome(String id);
    int updateCaseHome(String id,String name,String des);

List<CaseresList> getCaseresList(String seriesid);









}
