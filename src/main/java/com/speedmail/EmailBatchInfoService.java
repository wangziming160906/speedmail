package com.speedmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangshuai on 2018/2/14.
 */
@Service
public class EmailBatchInfoService {

    @Autowired
    private EmailBatchInfoDao emailBatchInfoDao;

    @Autowired
    private EmailDbOperate emailDbOperate;

    public int updateEmailBatchInfo(EmailBatchInfoBean emailBatchInfoBean){
        return emailBatchInfoDao.updateEmailBatchInfo(emailBatchInfoBean);
    }

    public EmailBatchInfoBean getEmailBatchInfoSign(String emailbatch){
        EmailBatchInfoBean emailBatchInfoBean = new EmailBatchInfoBean();
        emailBatchInfoBean.setEmail_batch(emailbatch);
        emailBatchInfoBean = emailBatchInfoDao.getEmailBatchinfoList(emailBatchInfoBean).get(0);
        return emailBatchInfoBean;
    }

    public List<EmailBatchInfoBean> getEmailBatchInfoList(){
        EmailBatchInfoBean emailBatchInfoBean = new EmailBatchInfoBean();
        return  emailBatchInfoDao.getEmailBatchinfoList(emailBatchInfoBean);
    }

    public List<EmailBatchInfoBean> getEmailBatchinfoListAndWorkStatus(){
        return emailBatchInfoDao.getEmailBatchinfoListAndWorkStatus();
    }

    public boolean isExistBatchInfoCount(String emailBatch){
        boolean flag = false;
        int count =  emailDbOperate.getBatchInfo(emailBatch);
        if (count > 0){
            flag = true;
        }
        return flag;
    }


    public int insertBatchControl(String emailBatch,String plandate,String isopen,String priority){
        return emailDbOperate.insertBatchControl(emailBatch,plandate,isopen,priority);
    }


    public int updateBatchControl(String  emailBatch,String plandate,String isopen,String priority){
        return emailDbOperate.updateBatchControl(emailBatch,plandate,isopen,priority);

    }

    public int resetBatch(String emailBatch){
        return emailDbOperate.reSetBatch(emailBatch);
    }

    public Map getCountForSucFailNoSend(String emailBatch){
        return  emailDbOperate.getCountSendStuatus(emailBatch);
    }

    public boolean deleteFromBatchNum(String batchNum){
        emailDbOperate.deleteFromBatchNum(batchNum);
        return true;
    }

}
