package com.speedmail.common;

public class ResponseFailDto extends ResponseDto {
    public ResponseFailDto(String msg) {
        super("201",msg,null);
    }
}
