package com.ciic.test.service;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by lixuecheng on 2017/7/10.
 */
public interface Thread2run {
    ExecutorService getPool();
    int run(List<Thread> list);
    int shopPool();
    int shutdownPool();
    boolean isClosed();
}
