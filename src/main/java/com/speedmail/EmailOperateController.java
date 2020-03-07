package com.speedmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuai on 2018/1/27.
 */
@Controller
public class EmailOperateController {
    @Autowired
    private EmailDbOperate emailDbOperate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmailSendGridHandle emailSendGridHandle;

    @Autowired
    EmailsLoadService emailsLoadService;

    @Autowired
    EmailSendSourceControlService emailSendSourceControlService;

    @Autowired
    EmailBatchInfoService emailBatchInfoService;

    @Autowired
    EmailSendApiKeyRepository emailSendApiKeyRepository;

    @Autowired
    EmailAutoSendConfigRepository emailAutoSendConfigRepository;

    @RequestMapping("/getUsers")
    public List<Map<String, Object>> getDbType() {
        String sql = "select * from EMAILS_SEND_TODAY";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//        for (Map<String, Object> map : list) {
//            Set<Entry<String, Object>> entries = map.entrySet();
//            if (entries != null) {
//                Iterator<Entry<String, Object>> iterator = entries.iterator();
//                while (iterator.hasNext()) {
//                    Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
//                    Object key = entry.getKey();
//                    Object value = entry.getValue();
//                    System.out.println(key + ":" + value);
//                }
//            }
//        }
        return list;
    }

    @RequestMapping("/sendGridforEmails")
    public String getsendGrid() {
        EmailAppRunableForCloudEmail sendGrid = new EmailAppRunableForCloudEmail(emailSendGridHandle,emailDbOperate,emailBatchInfoService);
        sendGrid.sendEmails();
        return "sendGrid emails is ok";
    }

    @RequestMapping("/getReplyResult")
    public List<EmailReceiveEmailInfo> getReplyResult() {
        //批量收集收件人信息及回复功能
        List<EmailUserInfo> listReceiveEmailUsers = emailDbOperate.getEmailUsersList();
        List<EmailReceiveEmailInfo> listEmailReceiveEmailInfo = new ArrayList<EmailReceiveEmailInfo>();
        int index = 1;
        for (EmailUserInfo userInfo : listReceiveEmailUsers) {
            EmailYeahHandle emailYeahHandle = new EmailYeahHandle();
            List<MimeMessage> listReceiveEmailMsg = new ArrayList<MimeMessage>();
            System.out.println("第" + index +"个:" + userInfo.getEmail_user());
            try {
                EmailReceivePop3.receive(userInfo, listEmailReceiveEmailInfo,listReceiveEmailMsg,emailDbOperate);
            }catch (MessagingException e){
                System.out.println("MessagingException e");
                e.printStackTrace();
            }catch (IOException e){
                System.out.println("IOException e");
            }catch (Exception e){
                e.printStackTrace();
            }
            index++;
        }
        try {
            PrintWriter bw = null;
            bw = new PrintWriter(new BufferedOutputStream(new FileOutputStream("/Users/wangshuai/工作/10-开发信/EMAIL_RECEIVE.TXT")));
            String bt = "发件人\t收件人\t日期\t时间\t邮件主题\t邮件用户\t邮件密码";
            bw.println(bt);
            for (EmailReceiveEmailInfo emailre : listEmailReceiveEmailInfo) {
                System.out.println("subject:" + emailre.getSubject() + "  receivedate:" + emailre.getSendDate() + "  user:" + emailre.getUserAddr() + "  pws:" + emailre.getUserPws());
                String data = emailre.getSendUser() + "\t" + emailre.getUserAddr() + "\t" + emailre.getSendDate().substring(0, 11) + "\t" + emailre.getSendDate() + "\t" + emailre.getSubject() + "\t" + emailre.getUserAddr() + "\t" + emailre.getUserPws();
                bw.println(data);
            }
            bw.close();
        }catch (IOException e){

        }
        System.out.println("receive emails count is:" + listEmailReceiveEmailInfo.size());
        return listEmailReceiveEmailInfo;
    }

    @RequestMapping(value = "/sendsource")
    public String sendsourceList(Model model) {
        emailDbOperate.updateSendSourceControlStatus();
        model.addAttribute("list", emailSendSourceControlService.getSendSourceControlList());
        return "emailsendsourcecontrol";
    }

    @RequestMapping(value = "/batch")
    public String batchinfoList(Model model) {
        model.addAttribute("list", emailBatchInfoService.getEmailBatchinfoListAndWorkStatus());
        return "emailbatchinfo";
    }

    @RequestMapping(value = "/emailsendkey")
    public String emailsendkey(Model model) {
        List<EmailSendApiKeyEntity> list = emailSendApiKeyRepository.findAll();
        List listNew = new ArrayList();
        for(EmailSendApiKeyEntity emailSendApiKeyEntity : list){
            String apikey = emailSendApiKeyEntity.getApikey();
            int factCountAipkey = emailDbOperate.getSingleApiKeyMonthFactCount(apikey);
            emailSendApiKeyEntity.setPlandaycount(factCountAipkey);
            listNew.add(emailSendApiKeyEntity);
        }

        model.addAttribute("list", listNew);
        return "emailsendkeyinfo";
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "index";
    }


    @RequestMapping(value = "/emailautocruise")
    public String emailautocruise(Model model) {
        return "emailautocruise";
    }

}
