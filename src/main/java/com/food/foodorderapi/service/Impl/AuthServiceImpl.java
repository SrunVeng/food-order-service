package com.food.foodorderapi.service.Impl;

import com.food.foodorderapi.entity.PasswordResetOTP;
import com.food.foodorderapi.repository.PasswordResetOTPRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import com.food.foodorderapi.client.Gmail.GmailRegisterClient;
import com.food.foodorderapi.client.Gmail.GmailResetClient;
import com.food.foodorderapi.dto.request.RefreshTokenRequestDto;
import com.food.foodorderapi.dto.request.UserLoginRequestDto;
import com.food.foodorderapi.dto.request.UserRegisterRequestDto;
import com.food.foodorderapi.dto.request.UserResetPasswordRequestDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.entity.Role;
import com.food.foodorderapi.entity.User;
import com.food.foodorderapi.library.constant.Constant;
import com.food.foodorderapi.library.constant.ErrorCode;
import com.food.foodorderapi.library.exception.BusinessException;
import com.food.foodorderapi.library.utils.NumberGenerator.OTPGenerator;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.repository.RoleRepository;
import com.food.foodorderapi.repository.UserRepository;
import com.food.foodorderapi.service.AuthService;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtEncoder jwtEncoderAccessToken;
    private final JwtEncoder jwtEncoderRefreshToken;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final GmailRegisterClient gmailRegisterClient;
    private final GmailResetClient gmailResetClient;
    private final PasswordResetOTPRepository passwordResetOTPRepository;


    @Override
    public UserLoginResultDto userLogin(UserLoginRequestDto request) {
        //1. Authenticate
        Authentication auth = new UsernamePasswordAuthenticationToken(request.getUsername()
                , request.getPassword());
        auth = daoAuthenticationProvider.authenticate(auth);


        String scope = auth.
                getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));


        Instant now = Instant.now();
        JwtClaimsSet accessJwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject(auth.getName())
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .audience(List.of("REACT JS"))
                .claim("scope", scope)
                .build();

        JwtClaimsSet refreshJwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject(auth.getName())
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .audience(List.of("REACT JS"))
                .claim("scope", scope)
                .build();

        //2. Generate Token
        String accessToken = jwtEncoderAccessToken.encode(JwtEncoderParameters.from(accessJwtClaimsSet)).getTokenValue();
        String refreshToken = jwtEncoderRefreshToken.encode(JwtEncoderParameters.from(refreshJwtClaimsSet)).getTokenValue();


        return UserLoginResultDto.builder()
                .accessToken(accessToken)
                .tokenType(Constant.ACCESS_TOKEN)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserLoginResultDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequestDto.getRefreshToken());

        auth = jwtAuthenticationProvider.authenticate(auth);


        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();

        Jwt jwt = (Jwt) auth.getPrincipal();

        // Create access token claims set
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .issuedAt(now)
                .subject(auth.getName())
                .issuer("web")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Access Token")
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .claim("scope", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt encodedJwt = jwtEncoderAccessToken.encode(jwtEncoderParameters);

        String accessToken = encodedJwt.getTokenValue();
        String refreshToken = refreshTokenRequestDto.getRefreshToken();

        if (Duration.between(Instant.now(), jwt.getExpiresAt()).toDays() < 2) {
            // Create refresh token claims set
            JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .issuedAt(now)
                    .subject(auth.getName())
                    .issuer("web")
                    .audience(List.of("nextjs", "reactjs"))
                    .subject("Refresh Token")
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .build();
            JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(jwtClaimsSetRefreshToken);
            Jwt jwtRefreshToken = jwtEncoderRefreshToken.encode(jwtEncoderParametersRefreshToken);
            refreshToken = jwtRefreshToken.getTokenValue();

        }
        return UserLoginResultDto.builder()
                .accessToken(accessToken)
                .tokenType(Constant.REFRESH_TOKEN)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void userRegister(UserRegisterRequestDto userRegisterRequestDto)  {

        User Byusername = userRepository.findByusername(userRegisterRequestDto.getUsername());
        if(ObjectUtils.isNotEmpty(Byusername)){
            throw new BusinessException(ErrorCode.USER_NAME_ALREADY_EXIST.getCode(),ErrorCode.USER_NAME_ALREADY_EXIST.getMessage());
        }

        User user = new User();
        user.setFirstName(userRegisterRequestDto.getFirstName());
        user.setLastName(userRegisterRequestDto.getLastName());
        user.setUsername(userRegisterRequestDto.getUsername());
        user.setEmail(userRegisterRequestDto.getEmail());
        user.setPhoneNumber(userRegisterRequestDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        //ToDO
        user.setChatId(null);

        // roles
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findByName("USER");
        roles.add(role);
        user.setRoles(roles);


        //SYSTEM
        user.setCreatedAt(String.valueOf(LocalDate.now()));
        user.setIsVerified(false);
        user.setIsBlock(false);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsDeleted(false);
        userRepository.save(user);
        gmailRegisterClient.sendMsgForRegistered(user.getEmail());
    }

    @Override
    public void userResetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto) {
        String OTP = OTPGenerator.generate6DigitOtp();
        PasswordResetOTP passwordResetOTP = new PasswordResetOTP();
        passwordResetOTP.setOtp(OTP);
        passwordResetOTP.setEmail(userResetPasswordRequestDto.getEmail());
        passwordResetOTP.setCreatedAt(Instant.now());
        passwordResetOTP.setExpiresAt(Instant.now().plus(3, ChronoUnit.MINUTES));
        passwordResetOTPRepository.save(passwordResetOTP);
        gmailResetClient.sendMsgForOTP(userResetPasswordRequestDto.getEmail(),OTP);
    }
}
