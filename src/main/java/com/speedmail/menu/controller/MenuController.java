package com.speedmail.menu.controller;

import com.speedmail.common.ResponseDto;
import com.speedmail.common.ResponseOkDto;
import com.speedmail.menu.dto.MenuTreeDto;
import com.speedmail.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;
    @RequestMapping(value = "/project/index")
    public ResponseDto getMenuInfo() {
        List<MenuTreeDto> list = new ArrayList<MenuTreeDto>();
        list = menuService.getMenuTreeForClient();
        ResponseDto resp = new ResponseOkDto(list);
        return resp;
    }
}
