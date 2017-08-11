package com.ciic.test;


import com.ciic.test.service.CaseService;
import com.ciic.test.service.Thread2run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by lixuecheng on 2017/7/10.
 */
//@SpringBootApplication
public class seleniumNoWebApplication implements CommandLineRunner {
    public static void main(String[] args) throws Exception {

        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(seleniumNoWebApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    @Autowired
    private Thread2run thread2run;
    @Autowired
    private CaseService caseService;
    @Override
    public void run(String... strings) throws Exception {

        List<Thread> list=caseService.getCases();
        thread2run.run(list);



       // System.exit(0);
        while (true){
            if(thread2run.isClosed()){
                System.exit(0);
            }



        }

    }
}
