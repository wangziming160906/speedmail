package com.speedmail;

import com.speedmail.common.ResponseDto;
import com.speedmail.common.ResponseOkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuai on 2018/1/27.
 */
@RestController
public class EmailOperateControllerTestRest {
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


//    @RequestMapping(value = "/project/index")
//    public Map userLogin(HttpServletRequest request) {
//        Map map = new HashMap();
//        map.put("member","WEEW");
//        map.put("tokenList","WEWE");
//        map.put("organizationList","WEWEW");
//        return map;
//    }


//    @RequestMapping(value = "/project/login")
//    public Map userLogin1(HttpServletRequest request) {
//        Map map = new HashMap();
//        map.put("code","201");
//        map.put("msg","密码错误");
//        Map map1 = new HashMap();
//        map1.put("member","WEEqwqwW");
//        map1.put("tokenList","WqwEWE");
//        map1.put("organizationList","WEWEqwqwW");
//        map.put("data",map1);
//        return map;
//    }
//
//    @RequestMapping(value = "/project/notify/noReads")
//    public Map userLogin3(HttpServletRequest request) {
//        Map map = new HashMap();
//
//        return map;
//    }
//
//
//    @RequestMapping(value = "/project/project/selfList")
//    public Map userLogin4(HttpServletRequest request) {
//        Map map = new HashMap();
//        return map;
//    }
//
//    @RequestMapping(value = "/project/task/selfList")
//    public Map userLogin5(HttpServletRequest request) {
//        Map map = new HashMap();
//        return map;
//    }
//
//    @RequestMapping(value = "/project/project/getLogBySelfProject")
//    public Map userLogin7(HttpServletRequest request) {
//        Map map = new HashMap();
//        return map;
//    }
//
//    @RequestMapping(value = "/project/account")
//    public Map userLogin8(HttpServletRequest request) {
//        Map map = new HashMap();
//        return map;
//    }

    @RequestMapping(value = "/project/notify/noReads")
    public ResponseDto notifyNote() {
        ResponseDto resp = ResponseDto.builder().build();
        Map map = new HashMap();
        map.put("list",new ArrayList<String>());
        map.put("total","1");
        map.put("totalSum",new ArrayList<String>());
        resp.setCode("200");
        resp.setMsg("");
        resp.setData(map);
        return resp;
    }

    @RequestMapping(value = "/index/index/checkInstall")
    public ResponseDto checkInstall() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        resp.setData(new ArrayList<String>());
        return resp;
    }

    @RequestMapping(value = "/project/index/systemConfig")
    public ResponseDto systemConfig() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        Map map = new HashMap();
        map.put("app_name","Speedmail");
        map.put("app_version","1.0");
        map.put("miitbeian","粤ICP备16eeeee2号-2");
        resp.setData(map);
        return resp;
    }

    @RequestMapping(value = "/project/login/_out")
    public ResponseDto logOut() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        resp.setData(new ArrayList<String>());
        return resp;
    }

    @RequestMapping(value = "/project/project/selfList")
    public ResponseDto selfList() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        Map map = new HashMap();
        map.put("list",new ArrayList<String>());
        map.put("total",0);
        resp.setData(map);
        return resp;
    }


    @RequestMapping(value = "/project/task/selfList")
    public ResponseDto taskSelfList() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        Map map = new HashMap();
        map.put("list",new ArrayList<String>());
        map.put("total",0);
        resp.setData(map);
        return resp;
    }


    @RequestMapping(value = "/project/project/getLogBySelfProject")
    public ResponseDto getLogBySelfProject() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        resp.setData(new ArrayList<String>());
        return resp;
    }


    @RequestMapping(value = "/project/account")
    public ResponseDto account() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        Map map = new HashMap();
        map.put("list",new ArrayList<String>());
        map.put("authList",new ArrayList<String>());
        map.put("total",0);
        map.put("page",0);
        resp.setData(map);
        return resp;
    }

    @RequestMapping(value = "/project/organization")
    public ResponseDto organization() {
        ResponseDto resp = ResponseDto.builder().build();
        resp.setCode("200");
        resp.setMsg("");
        Map map = new HashMap();
        map.put("name","vilson的个人项目1");
        map.put("avatar",null);
        map.put("description",null);
        map.put("owner_code","6v7be19pwman2fird04gqu53");
        map.put("create_time","2018-10-12");
        map.put("personal","1");
        map.put("code","6v7be19pwman2fird04gqu53");
        map.put("address","vilson的个人项目");
        map.put("province","0");
        map.put("city","0");
        map.put("area","0");
        List<Map> list = new ArrayList<Map>();
        list.add(map);
        Map map1 = new HashMap();
        map1.put("list",list);
        map1.put("total",1);
        resp.setData(map1);
        return resp;
    }

    @RequestMapping(value = "/project/project")
    public ResponseDto project() {
        Map map = new HashMap();
        map.put("total",1);

        Map map1 = new HashMap();
        map1.put("id","16");
        map1.put("cover","https://beta.vilson.xyz/static/upload//20191112/bbc62a54f99320920e0cbf72b2406c66.png");
        map1.put("name","Ant Design");
        map1.put("code","mo4uqwfb06dxv8ez2spkl3rg");
        map1.put("description","那时候我只会想自己想要什么，从不想自己拥有什么");
        map1.put("access_control_type","open");
        map1.put("white_list",null);
        map1.put("order","0");
        map1.put("deleted","0");
        map1.put("template_code","0");
        map1.put("schedule","74.29");
        map1.put("create_time","2018-12-25 07:20:36");
        map1.put("organization_code","6v7be19pwman2fird04gqu53");
        map1.put("deleted_time","2020-02-09 12:26:31");
        map1.put("private","1");
        map1.put("prefix","EP434");
        map1.put("open_prefix","1");
        map1.put("archive","0");
        map1.put("archive_time","2020-02-03 14:53:18");
        map1.put("open_begin_time","1");
        map1.put("open_task_private","1");
        map1.put("task_board_theme","simple");
        map1.put("begin_time","");
        map1.put("end_time","");
        map1.put("auto_update_schedule","1");
        map1.put("project_code","mo4uqwfb06dxv8ez2spkl3rg");
        map1.put("member_code","6v7be19pwman2fird04gqu53");
        map1.put("join_time","2018-12-25 07:20:36");
        map1.put("is_owner","1");
        map1.put("authorize",null);
        map1.put("collected","0");
        map1.put("owner_name","vilson");

        List list = new ArrayList();
        list.add(map1);

        map.put("list",list);
        ResponseDto resp =  new ResponseOkDto(map);
        return resp;
    }

    @RequestMapping(value = "/project/project_template")
    public ResponseDto projectTemplate() {
        ResponseDto resp =  new ResponseOkDto("");
        return resp;
    }



}
