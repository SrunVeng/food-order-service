package com.food.foodorderapi.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.vo.request.*;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {

  GroupCreateRequestDto toGroupCreateRequestDto(GroupCreateRequestVo requestVo);

  GroupDeleteRequestDto toGroupDeleteRequestDto(GroupDeleteRequestVo requestVo);

}
