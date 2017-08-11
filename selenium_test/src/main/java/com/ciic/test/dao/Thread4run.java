package com.ciic.test.dao;

import com.ciic.test.service.Thread2run;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixuecheng on 2017/7/10.
 */
@Service
public class Thread4run implements Thread2run{
    ExecutorService pool= Executors.newFixedThreadPool(3);

    /**
     * 获得线程池的线程
     * @return
     */
    @Override
    public ExecutorService getPool() {
        return pool;
    }

    @Override
    public int run(List<Thread> list) {
        try {
            for (int i = 0; i <list.size() ; i++) {
                pool.execute(list.get(i));
            }
            pool.shutdown();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 马上停止线程
     * @return 0，成功；1，失败
     */
    @Override
    public int shopPool() {
        try {
            pool.shutdownNow();
            return 0;
        } catch (Exception e) {
            return 1;
        }

    }
    /**
     * 马上停止加载线程
     * @return 0，成功；1，失败
     */
    @Override
    public int shutdownPool() {
        try {
            pool.shutdown();
            return 0;
        } catch (Exception e) {

            return 1;
        }

    }

    @Override
    public boolean isClosed() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(pool.isTerminated());
        return pool.isTerminated();
    }
}
