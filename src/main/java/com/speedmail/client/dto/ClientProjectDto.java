package com.speedmail.client.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientProjectDto {

    private String id;

    private String name;

    private String avatar;

    private String owner_code;

    private String create_time;

    private String personal;

    private String code;

    private String address;

    private String province;

    private String city;

    private String area;


}
