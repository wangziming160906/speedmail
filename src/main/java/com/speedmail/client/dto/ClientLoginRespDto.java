package com.speedmail.client.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientLoginRespDto {

    private ClientDto member;

    private ClientTokenDto tokenList;

    private List<ClientProjectDto> organizationList;
}
