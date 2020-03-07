package com.speedmail.client.controller;

import com.speedmail.client.dto.*;
import com.speedmail.client.service.ClientService;
import com.speedmail.common.ResponseDto;
import com.speedmail.common.ResponseFailDto;
import com.speedmail.common.ResponseOkDto;
import com.speedmail.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/project/login")
    public ResponseDto userLogin(ClientLoginReqDto clientLoginReqDto) {
        ResponseDto resp = ResponseDto.builder().build();
        ClientLoginRespDto clientLoginRespDto = ClientLoginRespDto.builder().build();
        ClientDto clientDto;
        try {
            clientDto = clientService.getClientInfo(clientLoginReqDto.getAccount(), clientLoginReqDto.getPassword());
        }catch (BusinessException e){
            resp = new ResponseFailDto(e.getMsg());
            return resp;
        }
        clientLoginRespDto.setMember(clientDto);

        ClientTokenDto clientTokenDto = ClientTokenDto.builder().accessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJhdWQiOiIiLCJpYXQiOjE1ODE5OTU1OTMsIm5iZiI6MTU4MTk5NTU5MywiZGF0YSI6eyJjb2RlIjoiNnY3YmUxOXB3bWFuMmZpcmQwNGdxdTUzIn0sInNjb3BlcyI6ImFjY2VzcyIsImV4cCI6MTU4MjYwMDM5M30.XVDzQKXeQvJrtDSsNdisu2ClocUJtFA-m4R8xC-MZTI")
                .refreshToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJhdWQiOiIiLCJpYXQiOjE1ODE5OTU1OTMsIm5iZiI6MTU4MTk5NTU5MywiZGF0YSI6eyJjb2RlIjoiNnY3YmUxOXB3bWFuMmZpcmQwNGdxdTUzIn0sInNjb3BlcyI6InJlZnJlc2giLCJleHAiOjE1ODI2MDAzOTN9.UV4d5onwMDnrLAVyftBRNblSPx2OpAgjAelnZ44jP3I")
                .accessTokenExp("1582600393")
                .tokenType("bearer").build();

        ClientProjectDto clientProjectDto = ClientProjectDto.builder().id("1").name("vilson的个人项目1").owner_code("6v7be19pwman2fird04gqu53").create_time("2018-10-12").personal("1").code("6v7be19pwman2fird04gqu53")
                .address("vilson的个人项目").province("0").city("0").area("0").build();

        List<ClientProjectDto> listClientProjectDto = new ArrayList<ClientProjectDto>();
        listClientProjectDto.add(clientProjectDto);
        clientLoginRespDto.setTokenList(clientTokenDto);
        clientLoginRespDto.setOrganizationList(listClientProjectDto);
        resp = new ResponseOkDto(clientLoginRespDto);
        return resp;
    }

}
