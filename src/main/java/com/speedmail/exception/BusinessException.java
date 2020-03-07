package com.speedmail.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class BusinessException extends Exception{
    private String msg;
    private String code;
}
