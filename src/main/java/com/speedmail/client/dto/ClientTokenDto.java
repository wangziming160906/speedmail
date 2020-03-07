package com.speedmail.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientTokenDto {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String accessTokenExp;

}
