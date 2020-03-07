package com.speedmail;

import com.mashape.unirest.http.exceptions.UnirestException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangshuai on 2018/1/27.
 */

public class EmailAppRunableForCloudEmail implements  Runnable{

    private EmailDbOperate emailDbOperateForSend;

    private EmailHandle handle;

    private static int LIMITE_STATIC = 10;

    private EmailBatchInfoService emailBatchInfoService;

    public void setHandle(EmailHandle handle) {
        this.handle = handle;
    }

    public void setEmailDbOperateForSend(EmailDbOperate emailDbOperateForSend){
        this.emailDbOperateForSend = emailDbOperateForSend;
    }

    public EmailAppRunableForCloudEmail(EmailHandle handle,EmailDbOperate emailDbOperate,EmailBatchInfoService emailBatchInfoService){
        this.handle = handle;
        this.emailDbOperateForSend = emailDbOperate;
        this.emailBatchInfoService = emailBatchInfoService;
    }

    public void run() {
        while(true) {
            this.sendEmails();
        }
    }

    private static int getRunnableSend(int mini,int maxi){
        int max=maxi;
        int min=mini;
        Random random = new Random();
        int index = random.nextInt(max)%(max-min+1) + min;
        return index;
    }

    public void sendEmails(){

        SimpleDateFormat dateFormater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datestr1 = dateFormater1.format(new Date());
        String sendsource = handle.getSource();
        String source_des = EmailUnit.SOURCE.get(sendsource).toString();
        datestr1 = datestr1 + "  发送渠道:" + source_des + " ";

        datestr1 = "邮件发送模块:" + datestr1+"  ";
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String datestr = dateFormater.format(new Date());
        String is_open = "0";
        String is_open_Test = "0";
        int plan_send_count = 0;
        int fact_send_count = 0;
        int min = 0;
        int max = 0;

        //获取SENDGRID配置信息
        Map map = emailDbOperateForSend.getSendSourceArg(sendsource,datestr);
        if(map != null){
            is_open_Test = map.get("IS_OPEN_TEST").toString();
            is_open = map.get("IS_OPEN").toString();
            plan_send_count = Integer.parseInt(map.get("PLAN_SEND_COUNT").toString());
            fact_send_count = Integer.parseInt(map.get("FACT_SEND_COUNT").toString());
            min = Integer.parseInt(map.get("SLEEP_MINUTE_MIN").toString());
            max = Integer.parseInt(map.get("SLEEP_MINUTE_MAX").toString());
        }else{
            System.out.println(datestr1 + "当前时间：" + datestr + ",当前没有任务！");
            try {
                Thread.sleep(1000 * 60 * 2);
            }catch(Exception e){

            }
            return;
        }
        EmailSourceBean sourceBean = new EmailSourceBean();
        sourceBean.setSend_source(sendsource);
        sourceBean.setPlan_date(datestr);
        if("0".equals(is_open)){
            System.out.println(datestr1 + "当前时间：" + datestr + ",任务开关未开启！");
            try {
                Thread.sleep(1000 * 60 * 2);
            }catch(Exception e){

            }
            return;
        }
        if("1".equals(is_open)) {
            //判断当前时间配置批量
            Map batchMap = emailDbOperateForSend.getSendBatchArg(datestr);
            boolean isBatchSet = true;
            if(batchMap == null){
                isBatchSet = false;
            }

            int fact_get_count = plan_send_count - fact_send_count;

            if(fact_get_count <= 0){
                System.out.println(datestr1 + "当前时间：" + datestr + ",计划发送条数:" + plan_send_count + ",实际发送条数:" + fact_send_count + ",当天已发送完毕！");
                try {
                    Thread.sleep(1000 * 60 * 2);
                }catch(Exception e){

                }
                return;
            }
            List<Map<String, Object>> toEmails = new ArrayList();
            if(fact_get_count > 0){
                if(fact_get_count >= LIMITE_STATIC){
                    fact_get_count = LIMITE_STATIC;
                }
                String fact_get_count_str = String.valueOf(fact_get_count);
                toEmails = emailDbOperateForSend.getEmailInfoList(fact_get_count_str,isBatchSet);
                String user = "";
                String userEmain = "";
                if(toEmails == null || toEmails.size() <= 0){
                    System.out.println(datestr1 + "当前时间：" + datestr + ",计划发送条数:" + plan_send_count + ",实际发送条数:" + fact_send_count + ",但没有可用发送地址！");
                    try {
                        Thread.sleep(1000 * 60 * 2);
                    }catch(Exception e){

                    }
                    return;
                }


                if (toEmails != null && toEmails.size() > 0) {
                    for (Map emaiinfoArrd : toEmails) {

                        String apikey = "";
                        int monthCount = 0;
                        List<Map<String, Object>> list = emailDbOperateForSend.getApiKey(sendsource);
                        if(list == null){
                            System.out.println(datestr1 + "APIKEY不可用!");
                            try {
                                Thread.sleep(1000 * 60 * 2);
                            }catch(Exception e){

                            }
                            return;
                        }
                        boolean flagApiKey = false;
                        for (Map result : list){
                            apikey = result.get("API_KEY").toString();
                            monthCount = Integer.parseInt(result.get("PLAN_MONTH_COUNT").toString());
                            SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");
                            String month = monthFormat.format(new Date());
                            int apiKeyMonthCount = emailDbOperateForSend.getApiKeyMonthCount(apikey,sendsource,month);

                            if(apiKeyMonthCount < monthCount){
                                flagApiKey = true;
                                break;
                            }else {
                                System.out.println(datestr1 + ",APIKEY:" + apikey + ",月份:" + month + ",月发送量:"+ monthCount + ",已发送:"+ apiKeyMonthCount +",已用完!");
                            }
                        }
                        if(!flagApiKey){
                            System.out.println(datestr1 + "所有可用APIKEY月使用量已用完!");
                            try {
                                Thread.sleep(1000 * 60 * 2);
                            }catch(Exception e){

                            }
                            return;
                        }


                        EmailUserInfo userInfo = new EmailUserInfo();
                        user = EmailBodyTemplateForCloudEmail.getUser();
                        userEmain = user + handle.getDomain();
                        userInfo.setEmail_user(userEmain);
                        userInfo.setAslias_name(userEmain);
                        EmailInfo emailinfo = new EmailInfo();
                        emailinfo.setEmail_from(userEmain);

                        if("1".equalsIgnoreCase(is_open_Test)){
                            SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
                            String day = dayFormat.format(new Date());
                            int apiKeyDayCount = emailDbOperateForSend.getApiKeyDayCount(apikey,sendsource,day);
                            if(apiKeyDayCount % 50 == 0){
                                System.out.println(datestr1 + "探测邮件!");
                                emailinfo.setEmail_to("wangshuaiws0716@163.com");
                            }else {
                                emailinfo.setEmail_to(emaiinfoArrd.get("EMAIL_ADDR").toString());
                            }
                        }else{
                            emailinfo.setEmail_to(emaiinfoArrd.get("EMAIL_ADDR").toString());
                        }
                        emailinfo.setEmail_subject(EmailBodyTemplateForCloudEmail.getSubject());
                        emailinfo.setEmail_content(EmailBodyTemplateForCloudEmail.getBoay(user));
                        emailinfo.setEmail_reply_to(userEmain);
                        String email_batch = emaiinfoArrd.get("EMAIL_BATCH").toString();
                        String send_status = "";
                        try {
                            String emailStrFilter = emailDbOperateForSend.getFiltrateEmails();
                            String data = emaiinfoArrd.get("EMAIL_ADDR").toString();
                            String serchStr = data.substring(data.indexOf("@")).trim();
                            String flag = "1";
                            if (emailStrFilter.indexOf(serchStr) < 0) {
                                flag = handle.sendEmailResult(userInfo, emailinfo,apikey);
                                int sendSleep = this.getRunnableSend(min, max);
                                Thread.sleep(1000 * sendSleep);
                            }
                            if ("0".equals(flag)) {
                                send_status = EmailUnit.SEND_STATUS_SUCCESS;
                            } else {
                                send_status = EmailUnit.SEND_STATUS_FAIL;
                            }

                        } catch (MessagingException e) {
                            send_status = EmailUnit.SEND_STATUS_FAIL;
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            send_status = EmailUnit.SEND_STATUS_FAIL;
                            e.printStackTrace();
                        } catch (UnirestException e) {
                            send_status = EmailUnit.SEND_STATUS_FAIL;
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            send_status = EmailUnit.SEND_STATUS_FAIL;
                        }finally {
                            String send_status_des = EmailUnit.SEND_STATUS.get(send_status).toString();
                            emailDbOperateForSend.upDateEmail(emailinfo,send_status,userInfo.getEmail_user(),send_status_des,sendsource,source_des);
                            emailDbOperateForSend.upDateSourceControlFactCount(sourceBean,send_status);
                            emailDbOperateForSend.upDateBatchSendCount(email_batch,send_status);
                            //emailDbOperateForSend.updateSendSourceApiKey(apikey,sendsource);

                            SimpleDateFormat monthFormatTemp = new SimpleDateFormat("yyyyMMdd");
                            String monthDayTemp = monthFormatTemp.format(new Date());
                            emailDbOperateForSend.updateSendSourceApiKeyCount(apikey,sendsource,monthDayTemp);

                            //更新批次表状态
                            if(emailBatchInfoService == null){
                                System.out.println("emailBatchInfoService is null");
                            }
                            EmailBatchInfoBean emailBatchInfoBean = emailBatchInfoService.getEmailBatchInfoSign(email_batch);
                            if(emailBatchInfoBean != null){
                                int batchcount = emailBatchInfoBean.getEmail_batch_count();
                                int sendfailcount = emailBatchInfoBean.getEmail_send_fail_count();
                                int sendsucccount = emailBatchInfoBean.getEmail_send_succ_count();
                                if(batchcount == (sendfailcount + sendsucccount)){
                                    emailBatchInfoBean.setEmail_batch_status("02");
                                    emailBatchInfoService.updateEmailBatchInfo(emailBatchInfoBean);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
