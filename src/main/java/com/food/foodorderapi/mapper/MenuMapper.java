
package com.food.foodorderapi.mapper;



import com.food.foodorderapi.dto.response.MenuCreateResultDto;
import com.food.foodorderapi.dto.response.MenuResultDto;
import com.food.foodorderapi.entity.Menu;
import com.food.foodorderapi.vo.response.MenuResponseVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.food.foodorderapi.dto.request.MenuCreateRequestDto;
import com.food.foodorderapi.dto.request.MenuDeleteRequestDto;
import com.food.foodorderapi.vo.request.MenuCreateRequestVo;
import com.food.foodorderapi.vo.request.MenuDeleteRequestVo;
import com.food.foodorderapi.vo.response.MenuCreateResponseVo;

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
}
