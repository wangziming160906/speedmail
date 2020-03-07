package com.speedmail.menu.repository;

import com.speedmail.menu.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuEntity,String> {
    List<MenuEntity> findByPid(String pid);
}
