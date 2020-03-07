package com.speedmail;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_APIKEY_INFO")
public class EmailSendApiKeyEntity {

    @Id
    @Column(name = "API_KEY")
    private String apikey;

    @Column(name = "SEND_SOURCE")
    private String sendsource;

    @Column(name = "SEND_SOURCE_DES")
    private String sendsourcedesc;

    @Column(name = "PLAN_MONTH_COUNT")
    private int planmounthcount;

    @Column(name = "PLAN_DAY_COUNT")
    private int plandaycount;

    @Column(name = "APIKEY_STATUS")
    private String apikeystatus;

    @Column(name = "API_WEB_USER")
    private String apiwebuser;

    @Column(name = "AIP_WEB_PSW")
    private String apiwebpws;

    @Column(name = "POST_URL")
    private String posturl;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSendsource() {
        return sendsource;
    }

    public void setSendsource(String sendsource) {
        this.sendsource = sendsource;
    }

    public int getPlanmounthcount() {
        return planmounthcount;
    }

    public void setPlanmounthcount(int planmounthcount) {
        this.planmounthcount = planmounthcount;
    }

    public int getPlandaycount() {
        return plandaycount;
    }

    public void setPlandaycount(int plandaycount) {
        this.plandaycount = plandaycount;
    }

    public String getApikeystatus() {
        return apikeystatus;
    }

    public void setApikeystatus(String apikeystatus) {
        this.apikeystatus = apikeystatus;
    }

    public String getApiwebuser() {
        return apiwebuser;
    }

    public void setApiwebuser(String apiwebuser) {
        this.apiwebuser = apiwebuser;
    }

    public String getApiwebpws() {
        return apiwebpws;
    }

    public void setApiwebpws(String apiwebpws) {
        this.apiwebpws = apiwebpws;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }

    public String getSendsourcedesc() {
        return sendsourcedesc;
    }

    public void setSendsourcedesc(String sendsourcedesc) {
        this.sendsourcedesc = sendsourcedesc;
    }
}
