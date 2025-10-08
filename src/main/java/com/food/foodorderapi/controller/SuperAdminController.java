package com.food.foodorderapi.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.request.AdminCreateRequestDto;
import com.food.foodorderapi.dto.request.AdminDeleteRequestDto;
import com.food.foodorderapi.dto.response.AdminResultDto;
import com.food.foodorderapi.library.messagebuilder.PageResponse;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.service.AuthService;
import com.food.foodorderapi.vo.request.AdminCreateRequestVo;
import com.food.foodorderapi.vo.request.AdminDeleteRequestVo;
import com.food.foodorderapi.vo.response.AdminResponseVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/super")
public class SuperAdminController {

    private final UserMapper userMapper;
    private final AuthService authService;



    @PostMapping("/create/user/admin")
    public ResponseMessageBuilder.ResponseMessage<Void> createAdmins(@Valid @RequestBody AdminCreateRequestVo request) {
        AdminCreateRequestDto requestDto = userMapper.toAdminCreateRequestDto(request);
        authService.createAdmin(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_SUPERADMIN')")
    @DeleteMapping("/delete/user/admin")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteAdmins(@Valid @RequestBody AdminDeleteRequestVo request) {
        AdminDeleteRequestDto requestDto = userMapper.toAdminDeleteRequestDto(request);
        authService.deleteAdmin(requestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_SUPERADMIN')")
    @GetMapping("/get-all-admin")
    public ResponseMessageBuilder.ResponseMessage<PageResponse<AdminResponseVo>> listAllAdmin(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<AdminResultDto> page = authService.findAllAdmin(pageable);
        PageResponse<AdminResponseVo> payload = PageResponse.from(page.map(userMapper::toAdminResponseVo));
        return new ResponseMessageBuilder<PageResponse<AdminResponseVo>>().success().addData(payload).build();
    }




}
