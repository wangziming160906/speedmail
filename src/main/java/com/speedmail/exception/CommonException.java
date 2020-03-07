package com.speedmail.exception;

public class CommonException implements ErrorCodeException{

    private String code;

    private String msg;

    public CommonException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }


}
