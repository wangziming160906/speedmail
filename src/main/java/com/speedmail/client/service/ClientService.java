package com.speedmail.client.service;

import com.speedmail.client.dto.ClientDto;
import com.speedmail.exception.BusinessException;

public interface ClientService {
    ClientDto getClientInfo(String account,String password) throws BusinessException;
}
