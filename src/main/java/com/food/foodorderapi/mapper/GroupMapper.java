package com.food.foodorderapi.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.GroupResultDto;
import com.food.foodorderapi.entity.Group;
import com.food.foodorderapi.vo.request.*;
import com.food.foodorderapi.vo.response.GroupResponseVo;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {

  GroupCreateRequestDto toGroupCreateRequestDto(GroupCreateRequestVo requestVo);

  GroupDeleteRequestDto toGroupDeleteRequestDto(GroupDeleteRequestVo requestVo);

  GroupResponseVo toGroupResponseVo(GroupResultDto resultDto);

  GroupResultDto toGroupResultDto(Group group);

  GroupUpdateRequestDto toGroupUpdateRequestDto(GroupUpdateRequestVo request);
}
