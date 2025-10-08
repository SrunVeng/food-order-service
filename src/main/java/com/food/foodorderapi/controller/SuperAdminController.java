package com.food.foodorderapi.controller;



import com.food.foodorderapi.dto.request.AdminCreateRequestDto;
import com.food.foodorderapi.dto.request.AdminDeleteRequestDto;
import com.food.foodorderapi.dto.response.AdminCreateResultDto;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;

import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.service.AuthService;
import com.food.foodorderapi.vo.request.AdminCreateRequestVo;
import com.food.foodorderapi.vo.request.AdminDeleteRequestVo;
import com.food.foodorderapi.vo.response.AdminCreateResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/super")
public class SuperAdminController {

    private final UserMapper userMapper;
    private final AuthService authService;
   

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_SUPERADMIN')")
    @PostMapping("/create/user/admin")
    public ResponseMessageBuilder.ResponseMessage<AdminCreateResponseVo> createAdmins(@Valid @RequestBody AdminCreateRequestVo request) {
        AdminCreateRequestDto requestDto = userMapper.toAdminCreateRequestDto(request);
        AdminCreateResultDto AdminCreateResultDto = authService.createAdmin(requestDto);
        AdminCreateResponseVo response = userMapper.toAdminCreateResponseVo(AdminCreateResultDto);
        return new ResponseMessageBuilder<AdminCreateResponseVo>().addData(response).success().build();
    }

    
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_SUPERADMIN')")
    @DeleteMapping("/delete/user/admin")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteAdmins(@Valid @RequestBody AdminDeleteRequestVo request) {
        AdminDeleteRequestDto requestDto = userMapper.toAdminDeleteRequestDto(request);
        authService.deleteAdmin(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }

    

}
