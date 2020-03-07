package com.speedmail.menu.service;

import com.speedmail.menu.dto.MenuTreeDto;

import java.util.List;

public interface MenuService {
    List<MenuTreeDto> getMenuTreeForClient();
}
