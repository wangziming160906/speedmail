package com.speedmail.client.dto;


import lombok.Data;

@Data
public class ClientLoginReqDto {

    private String remember_me;

    private String account;

    private String password;

    private String clientid;

}
