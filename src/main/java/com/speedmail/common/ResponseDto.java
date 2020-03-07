package com.speedmail.common;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ResponseDto {
    private String code;
    private String msg;
    private Object data;
}
