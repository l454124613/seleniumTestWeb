package com.ciic.test.service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/9/30.
 */
public interface Proxy  {

    void run() ;
    void stop();
    Map<String,String> getMap();
}
