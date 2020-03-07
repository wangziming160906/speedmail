package com.speedmail.client.repository;

import com.speedmail.client.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity,String> {

    List<ClientEntity> findByAccountAndPassword(String account,String password);

}
