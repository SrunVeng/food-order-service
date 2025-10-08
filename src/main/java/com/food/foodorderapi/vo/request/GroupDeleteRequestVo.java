package com.food.foodorderapi.vo.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupDeleteRequestVo {

    @NotBlank(message = "Group Id Cannot be blank")
    private Long groupId;
}
