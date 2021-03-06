package com.ciic.test.service;

import com.ciic.test.bean.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by lixuecheng on 2017/8/1.
 */
public interface ConfigService {

    //data
    int addDataspource(String name,String des,String type,String link,String dataname,String user,String pass,String tid);
    int updateDatasource(String id,String name,String des,String type,String link,String dataname,String user,String pass);
    int removeDatasource(String did);
    List<Datasource> getDatasource(String tid);
    String connectDatasource(String did);
    String[] updateDate(String data);
    ResultSet selectData(String sql) throws SQLException;
//con
    int clearisused();
    int stopRunCase();

//label

    int addLabel(String name,String des,String tid);
    int updateLabel(String id ,String name,String des);
    int removeLabel(String id);
    List<Label> getLabel(String tid);
    List<Label> getUsedLabel(String tid);

    //log

    List<Log> getActLog();
    List<Log> getsLog();
    int updateLogStatus(String lid);

    List<FileInfo> getfile(String tid);
    int addFile(String name,String size,String uid,String path,String tid);
    int updateFile(String name,String size,String uid,String path,String id);
    int reomveFile(String id);

    List<tmp> getOneFile(String id,String name);

    //proxy
    List<tmp> getUrls();

    //URLS
    List<tmp> getExceptUrls(String tid);
    int addExceptUrl(String url,String tid);
    int updateExceptUrl(String url,String id);
    int removeExceptUrl(String id);

}
