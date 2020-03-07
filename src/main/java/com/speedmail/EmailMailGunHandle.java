package com.speedmail;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * Created by wangshuai on 2018/1/31.
 */
@Service


public class EmailMailGunHandle implements EmailHandle {

    private static String MAILGUNURL = "https://api.mailgun.net/v3/bosun-mould.top/messages";

    private static String APIKEY = "key-c12ed697321e623871fc4b3cbb0af161";

    private static String SOURCE = "02";

    private static String DOMAIN = "@bosun-mould.top";

    @Override
    public void sendEmail(EmailUserInfo emailUserInfo, EmailInfo emailInfo) throws MessagingException, UnsupportedEncodingException, UnirestException {

    }

    @Override
    public void receiveEmails(EmailUserInfo emailUserInfo, EmailInfo emailInfo) {

    }

    @Override
    public void sendReceiveEmail(EmailUserInfo emailUserInfo, MimeMessage mimeMessage) throws MessagingException {

    }

    @Override
    public String sendEmailResult(EmailUserInfo emailUserInfo, EmailInfo emailInfo,String apikey) throws MessagingException, UnsupportedEncodingException, UnirestException {
        String flag = "0";
        HttpResponse<JsonNode> request = Unirest.post(MAILGUNURL)
                .basicAuth("api", apikey)
                .queryString("from", emailInfo.getEmail_from())
                .queryString("to", emailInfo.getEmail_to())
                .queryString("subject", emailInfo.getEmail_subject())
                .queryString("html", emailInfo.getEmail_content())
                .asJson();
        String respone = request.getBody().toString();
        System.out.println(respone);
        if(respone.indexOf("error")>=0){
            flag = "1";
        }
        return flag;
    }

    @Override
    public String getSource() {
        return SOURCE;
    }

    @Override
    public String getDomain() {
        return DOMAIN;
    }
}
