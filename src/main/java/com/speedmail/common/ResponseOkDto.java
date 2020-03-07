package com.speedmail.common;

public class ResponseOkDto extends ResponseDto {
    public ResponseOkDto(Object data) {
        super("200","",data);
    }

}
