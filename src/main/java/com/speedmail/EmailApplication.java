package com.speedmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EmailApplication implements CommandLineRunner {

    @Autowired
    private EmailDbOperate emailDbOperateForSend;

    @Autowired
    private EmailAppRunableForVeriEmail emailAppRunableForVeriEmail;

    @Autowired
    private EmailSendGridHandle emailSendGridHandle;

    @Autowired
    private EmailMailGunHandle emailMailGunHandle;

    @Autowired
    private EmailBatchInfoService emailBatchInfoService;

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {


    }
}

