package com.hwzn.service;

import com.hwzn.pojo.dto.button.GetButtonListByPageDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hwzn.pojo.dto.button.UpdateButtonDto;
import com.hwzn.pojo.entity.ButtonEntity;
import com.hwzn.pojo.entity.RouterEntity;
import com.hwzn.pojo.Result;
import java.util.List;

/**
 * @Author: Du Rongjun
 * @Date: 2023/9/1 16:37
 * @Desc: 路由服务接口
 */
public interface ButtonService {

    Integer createButton(ButtonEntity buttonEntity);

    IPage<ButtonEntity> getButtonList(GetButtonListByPageDto getButtonListByPageDto);

    ButtonEntity getButtonById(Integer id);

    Result validateCreateParam(Integer id);

    Result validateUpdateParam(UpdateButtonDto updateButtonDto);

    Integer updateButton(ButtonEntity buttonEntity);

    Integer deleteButton(Integer id);

    List<RouterEntity> getRouterButtonTree();
}