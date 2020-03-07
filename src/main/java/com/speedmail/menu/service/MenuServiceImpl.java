package com.speedmail.menu.service;

import com.speedmail.exception.BusinessException;
import com.speedmail.menu.dto.MenuTreeDto;
import com.speedmail.menu.entity.MenuEntity;
import com.speedmail.menu.repository.MenuRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<MenuTreeDto> getMenuTreeForClient(){
        List<MenuTreeDto> list = this.getMenuTreeList("0");
        return list;
    }

    private List<MenuTreeDto> getMenuTreeList(String pid) {
        List<MenuTreeDto> listMenuTreeDto = new ArrayList<>();
        List<MenuEntity> list = menuRepository.findByPid(pid);
        for(MenuEntity menuEntity:list){
            MenuTreeDto menuTreeDto = MenuTreeDto.builder().build();
            BeanUtils.copyProperties(menuEntity,menuTreeDto);
            menuTreeDto.setFullUrl(menuTreeDto.getUrl() + "/" + menuTreeDto.getValues());
            if(menuEntity.getInner() != null && "0".equalsIgnoreCase(menuEntity.getInner())){
                menuTreeDto.setIs_inner(false);
            }else {
                menuTreeDto.setIs_inner(true);
            }
            List<MenuTreeDto> childrenList = this.getMenuTreeList(menuEntity.getId().toString());
            if(childrenList.size()>0){
                menuTreeDto.setChildren(childrenList);
            }
            listMenuTreeDto.add(menuTreeDto);
        }
        return listMenuTreeDto;
    }
}
