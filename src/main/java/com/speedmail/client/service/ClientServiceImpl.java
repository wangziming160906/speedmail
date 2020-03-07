package com.speedmail.client.service;

import com.speedmail.client.dto.ClientDto;
import com.speedmail.client.entity.ClientEntity;
import com.speedmail.client.repository.ClientRepository;
import com.speedmail.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDto getClientInfo(String account, String password) throws BusinessException{
        List<ClientEntity> list = clientRepository.findByAccountAndPassword(account,password);
        if(list.size() <= 0){
            throw BusinessException.builder().code("201").msg("用户名或密码不正确").build();
        }
        ClientEntity clientEntity = list.get(0);
        ClientDto clientDto = ClientDto.builder().build();
        BeanUtils.copyProperties(clientEntity,clientDto);
        return clientDto;
    }
}
