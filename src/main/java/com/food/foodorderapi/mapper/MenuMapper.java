
package com.food.foodorderapi.mapper;




import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.MenuDeleteRequestDto;
import com.food.foodorderapi.dto.request.MenuUpdateRequestDto;
import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.dto.response.MenuResultDto;
import com.food.foodorderapi.entity.Menu;
import com.food.foodorderapi.vo.request.MenuCreateRequestVo;
import com.food.foodorderapi.vo.request.MenuDeleteRequestVo;
import com.food.foodorderapi.vo.response.MenuCreateResponseVo;
import com.food.foodorderapi.vo.response.MenuResponseVo;
import com.food.foodorderapi.vo.response.MenuUpdateRequestVo;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {


    MenuCreateRequestDto toMenuCreateRequestDto(MenuCreateRequestVo request);

    MenuCreateResponseVo toMenuCreateResponseVo(MenuCreateResultDto menuCreateResultDto);

    MenuDeleteRequestDto toMenuDeleteRequestDto(MenuDeleteRequestVo request);

    MenuResponseVo toMenuResponseVo(MenuResultDto dto);
    MenuResultDto toMenuResultDto(Menu menu);

    MenuUpdateRequestDto toMenuUpdateRequestDto(MenuUpdateRequestVo request);

    List<MenuResponseVo> toMenuResponseVoList(List<MenuResultDto> menus);
}
