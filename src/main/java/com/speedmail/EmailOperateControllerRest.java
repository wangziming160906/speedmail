package com.speedmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by wangshuai on 2018/1/27.
 */
@RestController
public class EmailOperateControllerRest {
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

    @Autowired
    EmailSendSourceControlRepository emailSendSourceControlRepository;


    @RequestMapping(value="/upload")
    public ModelAndView doUploadFile(@RequestParam("emailBatchFile") MultipartFile file,@RequestParam("emailBathDes") String emailBatchDes,ModelAndView mv){
        Map map = new HashMap();
        map.put("result","1");
        mv.getModel().put("result","1");
        if(!file.isEmpty()){
            try {
                emailsLoadService.loadEmailsFromWebFile(file.getInputStream(),emailBatchDes);
            } catch (Exception e) {
                e.printStackTrace();
                mv.getModel().put("result","0");
            }
        }
        mv.setViewName("emailexport");
        return mv;
    }

    @RequestMapping(value = "/emailfiltrate")
    public ModelAndView emailfiltrate(ModelAndView mv) {
        mv.setViewName("emailfiltrate");
        return mv;
    }

    @RequestMapping(value="/filtrateupload")
    public ModelAndView doFiltrateUploadFile(@RequestParam("emailBatchFile") MultipartFile file,ModelAndView mv){
        Map map = new HashMap();
        map.put("result","1");
        mv.getModel().put("result","1");
        if(!file.isEmpty()){
            try {
                emailsLoadService.loadEmailsFiltrate(file.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                mv.getModel().put("result","0");
            }
        }
        mv.setViewName("emailfiltrate");
        return mv;
    }

    @RequestMapping(value = "/emailexport")
    public ModelAndView emailexport(ModelAndView mv) {
        mv.setViewName("emailexport");
        return mv;
    }


    @RequestMapping(value = "/sendkey/save")
    public Map sendApiKeySave1(HttpServletRequest request) {
        Map map = new HashMap();
        List<EmailSendApiKeyEntity> list = emailSendApiKeyRepository.findAll();
        EmailSendApiKeyEntity entity = list.get(0);
        System.out.println(entity.getApikey());
        return map;
    }


    @RequestMapping(value = "/sendapikey/save")
    public Map sendApiKeySave(EmailSendApiKeyEntity emailSendApiKeyEntity) {
        Map map = new HashMap();
        map.put("flag","1");
        emailSendApiKeyRepository.save(emailSendApiKeyEntity);
        return map;
    }

    @RequestMapping(value = "/sendapikey/delete")
    public Map sendApiKeySave(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("flag","1");
        String apikey = request.getParameter("apikey");
        emailSendApiKeyRepository.delete(apikey);
        return map;
    }


    @RequestMapping(value = "/sendautocruise/save")
    public Map sendAutoCruiseSave(EmailAutoSendConfigEntity emailAutoSendConfigEntity) {
        Map map = new HashMap();
        map.put("flag","1");
        emailAutoSendConfigRepository.save(emailAutoSendConfigEntity);
        return map;
    }

    @RequestMapping(value = "/sendsource/save2")
    public Map sendAutoCruiseSave1(EmailSendSourceControlEntity emailSendSourceControlEntity) {
        System.out.println(emailSendSourceControlEntity.getPlandate());
        System.out.println("sendway==" + emailSendSourceControlEntity.getSendway());
        emailSendSourceControlEntity.setSendway("01");
        Map map = new HashMap();
        map.put("flag","1");
        emailSendSourceControlRepository.save(emailSendSourceControlEntity);
        return map;
    }

    @RequestMapping(value = "/sendsource/save")
    public Map sendsourceSave(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("flag","1");
        try {
            String sendsource = request.getParameter("sendsource");
            String sendsourceDes = EmailUnit.SOURCE.get(sendsource);
            String plandate = request.getParameter("plandate");
            String isopen = request.getParameter("isopen");
            String isopenTest = request.getParameter("isopenTest");
            String requestway = request.getParameter("requestway");
            String sendway = request.getParameter("sendway");
            int plansendcount = 0;
            int sleepminutemin = 0;
            int sleepminutemax = 0;
            try{
                plansendcount = Integer.parseInt(request.getParameter("plan_send_count"));
                sleepminutemin = Integer.parseInt(request.getParameter("sleep_minute_min"));
                sleepminutemax = Integer.parseInt(request.getParameter("sleep_minute_max"));
            }catch (Exception e){

            }

            int allApiKeyFactCount = emailDbOperate.getAllApiKeyMonthFactCount();
            int allApiKeyPlanCount = emailDbOperate.getAllApiKeyMonthPlanCount();
            int allApiKeyFactDayCount = emailDbOperate.getAllApiKeyDayFactCount();
            int planfactsendCount  = plansendcount - allApiKeyFactDayCount;
            int leftCount = allApiKeyPlanCount - allApiKeyFactCount;

            if(planfactsendCount > leftCount){
                map.put("flag","3");
                map.put("errormessage","计划发送数量" + plansendcount + ",超过本月剩余发送数量" + leftCount);
                return map;
            }

            EmailSendSourceControlBean emailSendSourceControlBean = new EmailSendSourceControlBean();
            emailSendSourceControlBean.setSend_source(sendsource);
            emailSendSourceControlBean.setSend_source_des(sendsourceDes);
            emailSendSourceControlBean.setPlan_date(plandate);
            emailSendSourceControlBean.setIs_open(isopen);
            emailSendSourceControlBean.setPlan_send_count(plansendcount);
            emailSendSourceControlBean.setSleep_minute_min(sleepminutemin);
            emailSendSourceControlBean.setSleep_minute_max(sleepminutemax);
            emailSendSourceControlBean.setIsopenTest(isopenTest);
            emailSendSourceControlBean.setSend_way(sendway);


            if("1".equalsIgnoreCase(requestway)) {
                emailSendSourceControlBean.setSend_way("01");
                emailSendSourceControlService.saveSendSourceControl(emailSendSourceControlBean);

                map.put("requestway","1");
            }else if("0".equalsIgnoreCase(requestway)){
                emailSendSourceControlService.updateSendSourceControl(emailSendSourceControlBean);
                map.put("requestway","0");
            }else {
                emailSendSourceControlService.deleteSendSourceControl(emailSendSourceControlBean);
                map.put("requestway","2");
            }
            map.put("emailSendSourceControlBean",emailSendSourceControlBean);
        }catch (Exception e){
            e.printStackTrace();
            map.put("flag","0");
        }
        return  map;
    }

    @RequestMapping(value = "/batch/save")
    public Map batchInfoSave(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("flag","1");
        String email_batch = request.getParameter("email_batch");
        String plandate = request.getParameter("plandate");
        String priority = request.getParameter("priority");
        String requestway = request.getParameter("requestway");
        String isopen = request.getParameter("isopen");

        try {
            boolean flag = emailBatchInfoService.isExistBatchInfoCount(email_batch);
            if("5".equalsIgnoreCase(requestway)){
                emailBatchInfoService.deleteFromBatchNum(email_batch);
                return map;
            }
            if (flag) {
                if ("0".equalsIgnoreCase(requestway)) {
                    SimpleDateFormat dateFormater1 = new SimpleDateFormat("yyyy-MM-dd");
                    String datestr1 = dateFormater1.format(new Date());
                    if(datestr1.equalsIgnoreCase(plandate)){
                        isopen = "1";
                    }else {
                        isopen = "0";
                    }
                    emailBatchInfoService.updateBatchControl(email_batch, plandate, isopen, priority);
                }
                if("2".equalsIgnoreCase(requestway)){
                    emailBatchInfoService.updateBatchControl(email_batch, plandate, isopen, priority);
                }
                if("3".equalsIgnoreCase(requestway)){
                    emailBatchInfoService.resetBatch(email_batch);
                    isopen = "1";
                    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
                    plandate = dateFormater.format(new Date());
                }
                if("4".equalsIgnoreCase(requestway)){
                    Map mapcount = emailBatchInfoService.getCountForSucFailNoSend(email_batch);
                    if(mapcount != null){
                        if(mapcount.get("00") != null){
                            map.put("nosendcount",mapcount.get("00").toString());
                        }
                        if(mapcount.get("01") != null){
                            map.put("sendsuccess",mapcount.get("01").toString());
                        }
                        if(mapcount.get("02") != null){
                            map.put("sendfail",mapcount.get("02").toString());
                        }
                    }
                }

            } else {
                emailBatchInfoService.insertBatchControl(email_batch, plandate, isopen, priority);
            }
            map.put("email_batch", email_batch);
            map.put("plandate", plandate);
            map.put("priority", priority);
            map.put("requestway", requestway);
            map.put("isopen", isopen);
        }catch (Exception e){
            map.put("flag","0");
        }
        return map;
    }


    @RequestMapping(value = "/getSendConfig")
    public Map getSendConfig() {
        Map map = new HashMap();
        EmailAutoSendConfigEntity emailAutoSendConfigEntity = emailAutoSendConfigRepository.findOne("01");
        map.put("emailAutoSendConfigEntity", emailAutoSendConfigEntity);
        return map;
    }

    @RequestMapping(value = "/getServerDate")
    public Map getServerDate(HttpServletRequest request) {
        Map map = new HashMap();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        String datestr = dateFormater.format(new Date());
        int allApiKeyFactCount = emailDbOperate.getAllApiKeyMonthFactCount();
        int allApiKeyPlanCount = emailDbOperate.getAllApiKeyMonthPlanCount();
        int leftCount = allApiKeyPlanCount - allApiKeyFactCount;
        map.put("leftcount","本月剩余发送数量:" + leftCount);
        map.put("serverdate",datestr);
        return map;
    }

    @GetMapping(value = "/test")
    public EmailInfo getEmail(){
        EmailInfo e = new EmailInfo();
        e.setEmail_reply_to("dfdfdf");
        e.setEmail_subject("dfdsf");
        return e;
    }



}
