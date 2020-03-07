package com.speedmail;

import com.mashape.unirest.http.exceptions.UnirestException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

/**
 * Created by wangshuai on 2017/9/7.
 */
public class EmailAppRunable implements  Runnable{

    private EmailDbOperate emailDbOperate;

    private List<Map<String, Object>> toEmails = new ArrayList();

    private EmailUserInfo userInfo;

    private int sleepTime = 0;

    private EmailHandle handle;

    public EmailAppRunable(int sleepTime, EmailHandle handle,EmailDbOperate emailDbOperate){
        this.emailDbOperate = emailDbOperate;
        this.sleepTime = sleepTime;
        this.handle = handle;
    }

    public void run() {
        boolean flag = true;
        while(flag) {
            toEmails = emailDbOperate.getEmailInfoList();
            userInfo = emailDbOperate.getEmailUser();
            if(toEmails == null){
                exit(0);
            }
            if(userInfo == null){
                exit(0);
            }
            for (Map emaiinfoArrd : toEmails) {
                EmailInfo emailinfo = new EmailInfo();
                emailinfo.setEmail_from(userInfo.getEmail_user());
                emailinfo.setEmail_to(emaiinfoArrd.get("EMAIL_ADDR").toString());
                emailinfo.setEmail_subject(EmailBoayTemplatestemp.getSubject());
                emailinfo.setEmail_content(EmailBoayTemplatestemp.getBoay());
                try {
                    try {
                        Thread.sleep(1000 * sleepTime);
                        handle.sendEmail(userInfo, emailinfo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    emailDbOperate.upDateUser(userInfo,"01");
                    System.out.println(userInfo.getEmail_user() + "----" + userInfo.getEmail_password() + "----" + userInfo.getAslias_name() + "---ok");
                } catch (MessagingException e) {
                    //emailDbOperate.upDateEmail(emailinfo,"02",userInfo.getEmail_user());
                    //e.printStackTrace();
                    emailDbOperate.upDateUser(userInfo,"02");
                    System.out.println(userInfo.getEmail_user() + "----" + userInfo.getEmail_password() + "----" + userInfo.getAslias_name() + "---no");
                } catch (UnsupportedEncodingException e) {
                    //emailDbOperate.upDateEmail(emailinfo,"02",userInfo.getEmail_user());
                    //e.printStackTrace();
                    emailDbOperate.upDateUser(userInfo,"02");
                    System.out.println(userInfo.getEmail_user() + "----" + userInfo.getEmail_password() + "----" + userInfo.getAslias_name() + "---no");
                } catch (UnirestException e){

                }
            }
        }
    }
}
