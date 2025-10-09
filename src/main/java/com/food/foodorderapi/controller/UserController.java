package com.food.foodorderapi.controller;

import com.food.foodorderapi.dto.request.UserUpdateProfileRequestDto;
import com.food.foodorderapi.dto.request.UserUpdateProfileVerifyRequestDto;
import com.food.foodorderapi.vo.request.UserUpdateProfileRequestVo;
import com.food.foodorderapi.vo.request.UserUpdateProfileVerifyRequestVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.food.foodorderapi.dto.response.UserProfileResultDto;
import com.food.foodorderapi.library.messagebuilder.ResponseMessageBuilder;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.service.AuthService;
import com.food.foodorderapi.vo.response.UserProfileResponseVo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class UserController {

    private final AuthService authService;
    private final UserMapper userMapper;


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER','SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @GetMapping("/user/get-by-username")
    public ResponseMessageBuilder.ResponseMessage<UserProfileResponseVo> getByUsername(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("sub");
        UserProfileResultDto result = authService.getbyUsername(username);
        UserProfileResponseVo response = userMapper.toUserProfileResponseVo(result);
        return new ResponseMessageBuilder<UserProfileResponseVo>().success().addData(response).build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER','SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @PutMapping("/user/update-profile")
    public ResponseMessageBuilder.ResponseMessage<Void> updateUserProfile(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody UserUpdateProfileRequestVo request) {
        String username = jwt.getClaimAsString("sub");
        UserUpdateProfileRequestDto req = userMapper.toUserUpdateProfileRequestDto(request);
        authService.updateByUsername(username ,req);
        return new ResponseMessageBuilder<Void>().success().build();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER','SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @PostMapping("/user/update-profile/verify")
    public ResponseMessageBuilder.ResponseMessage<Void> verifyUpdateEmail(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody UserUpdateProfileVerifyRequestVo body
    ) {
        String username = jwt.getClaimAsString("sub");
        UserUpdateProfileVerifyRequestDto userUpdateProfileVerifyRequestDto = userMapper.toUserUpdateProfileVerifyRequestDto(body);
        authService.verifyUpdateEmail(username, userUpdateProfileVerifyRequestDto);
        return new ResponseMessageBuilder<Void>().success().build();
    }



    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER','SCOPE_ROLE_ADMIN','SCOPE_ROLE_SUPERADMIN')")
    @DeleteMapping("/user/delete-account")
    public ResponseMessageBuilder.ResponseMessage<Void> deleteUser(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getClaimAsString("sub");
        authService.deletebyUsername(username);
        return new ResponseMessageBuilder<Void>().success().build();
    }
}
