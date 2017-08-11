package com.ciic.test.service;

import com.ciic.test.bean.Datasource;

import java.util.List;

/**
 * Created by lixuecheng on 2017/8/1.
 */
public interface ConfigService {
    int addDataspource(String name,String des,String type,String link,String dataname,String user,String pass,String tid);
    int updateDatasource(String id,String name,String des,String type,String link,String dataname,String user,String pass);
    int removeDatasource(String did);
    List<Datasource> getDatasource(String tid);
    String connectDatasource(String did);

    int clearisused();

}
