package com.food.foodorderapi.service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.food.foodorderapi.client.Gmail.GmailClient;
import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.AdminResultDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.dto.response.UserResultDto;
import com.food.foodorderapi.entity.*;
import com.food.foodorderapi.library.constant.Constant;
import com.food.foodorderapi.library.constant.ErrorCode;
import com.food.foodorderapi.library.exception.BusinessException;
import com.food.foodorderapi.library.utils.NumberGenerator.OTPGenerator;
import com.food.foodorderapi.library.utils.NumberGenerator.TokenGenerator;
import com.food.foodorderapi.mapper.UserMapper;
import com.food.foodorderapi.repository.*;
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
    private final GmailClient gmailClient;
    private final EmailVerificationOTPRepository emailVerificationOTPRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final AdminInviteTokenRepository adminInviteTokenRepository;


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

        if (userRepository.existsByUsernameIgnoreCase(userRegisterRequestDto.getUsername())) {
            throw new BusinessException(
                    ErrorCode.USER_NAME_ALREADY_EXIST.getCode(),
                    ErrorCode.USER_NAME_ALREADY_EXIST.getMessage()
            );
        }

        if (userRepository.existsByEmailIgnoreCase(userRegisterRequestDto.getEmail())) {
            throw new BusinessException(
                    ErrorCode.EMAIL_ALREADY_EXIST.getCode(),
                    ErrorCode.EMAIL_ALREADY_EXIST.getMessage()
            );
        }
        if(!userRegisterRequestDto.getPassword().equals(userRegisterRequestDto.getConfirmPassword())){
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH.getCode(),ErrorCode.PASSWORD_MISMATCH.getMessage());
        }

        emailVerificationOTPRepository.findOpenByEmailAndPurpose(userRegisterRequestDto.getEmail(), EmailVerificationOTP.Purpose.REGISTER)
                .forEach(o -> {
                    if (o.getExpiresAt().isBefore(Instant.now())) o.setStatus(EmailVerificationOTP.Status.EXPIRED);
                });

        String code = OTPGenerator.generate6DigitOtp(); // e.g. 000000–999999
        EmailVerificationOTP rec = new EmailVerificationOTP();
        rec.setEmail(userRegisterRequestDto.getEmail());
        rec.setOtp(code);
        rec.setPurpose(EmailVerificationOTP.Purpose.REGISTER);
        rec.setStatus(EmailVerificationOTP.Status.OPEN);
        rec.setCreatedAt(Instant.now());
        rec.setExpiresAt(Instant.now().plus(Duration.ofMinutes(5)));
        emailVerificationOTPRepository.save(rec);
        gmailClient.sendOtpForRegister(userRegisterRequestDto.getEmail(),code);
    }

    @Override
    public void userRegisterVerify(UserRegisterVerifyRequestDto requestDto) {
        List<EmailVerificationOTP> list = emailVerificationOTPRepository.findOpenByEmailAndPurpose(requestDto.getEmail(), EmailVerificationOTP.Purpose.REGISTER);
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCode.OTP_INVALID.getCode(), ErrorCode.OTP_INVALID.getMessage());
        }
        EmailVerificationOTP last = list.getFirst();

        if (last.getExpiresAt().isBefore(Instant.now())) {
            last.setStatus(EmailVerificationOTP.Status.EXPIRED);
            throw new BusinessException(ErrorCode.OTP_EXPIRED.getCode(), ErrorCode.OTP_EXPIRED.getMessage());
        }
        if (!Objects.equals(last.getOtp(), requestDto.getOtp())) {
            last.setAttempts(last.getAttempts() + 1);
            throw new BusinessException(ErrorCode.OTP_INCORRECT.getCode(), ErrorCode.OTP_INCORRECT.getMessage());
        }
        last.setStatus(EmailVerificationOTP.Status.USED);

        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setChatId(null);

        Role role = roleRepository.findByName("USER");
        user.setRoles(Collections.singletonList(role));

        user.setCreatedAt(Instant.now());
        user.setIsVerified(true);
        user.setIsBlock(false);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsDeleted(false);
        userRepository.save(user);
        gmailClient.sendConfirmationRegistered(user.getEmail());
    }



    @Override
    public void userResetPassword(UserResetPasswordRequestDto userResetPasswordRequestDto) {

        Optional<User> userOpt = userRepository.findByEmailIgnoreCase(userResetPasswordRequestDto.getEmail());
        if (userOpt.isEmpty()) {
           throw new BusinessException(ErrorCode.EMAIL_DOES_NOT_EXIST.getCode(), ErrorCode.EMAIL_DOES_NOT_EXIST.getMessage());
        }
        User user = userOpt.get();
        String token = TokenGenerator.generateToken();
        Instant now = Instant.now();
        PasswordResetToken presetToken = new PasswordResetToken();
        presetToken.setToken(token);
        presetToken.setCreatedAt(now);
        presetToken.setExpiresAt(now.plus(Duration.ofMinutes(30)));
        presetToken.setStatus(PasswordResetToken.Status.OPEN);
        presetToken.setUser(user);
        passwordResetTokenRepository.save(presetToken);
        gmailClient.sendResetLink(user.getEmail(),presetToken.getToken());

    }

    @Override
    public void performPasswordReset(PerformPasswordResetRequestDto performPassDto) {

        PasswordResetToken prt = passwordResetTokenRepository.findByToken(performPassDto.getToken());
        if (prt.getStatus() != PasswordResetToken.Status.OPEN || prt.getExpiresAt().isBefore(Instant.now())) {
            prt.setStatus(PasswordResetToken.Status.EXPIRED);
            passwordResetTokenRepository.save(prt);
            throw new BusinessException(ErrorCode.INVALID_EXPIRED_RESET_LINK.getCode(), ErrorCode.INVALID_EXPIRED_RESET_LINK.getMessage());
        }
        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(performPassDto.getNewPassword()));
        userRepository.save(user);
        prt.setStatus(PasswordResetToken.Status.USED);
        prt.setUsedAt(Instant.now());
        passwordResetTokenRepository.save(prt);

    }

    @Override
    public void createAdmin(AdminCreateRequestDto requestDto) {
        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setPassword(null);
        user.setChatId(null);
        Role role = roleRepository.findByName("ADMIN");
        user.setRoles(Collections.singletonList(role));
        user.setCreatedAt(Instant.now());
        user.setIsVerified(false);
        user.setIsBlock(false);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsDeleted(false);
        userRepository.save(user);
        gmailClient.sendAdminInvite(user.getEmail(),user.getUsername());
    }

    @Override
    public void deleteAdmin(AdminDeleteRequestDto requestDto) {
        User byUserNo = userRepository.findByUserNo(requestDto.getUserNo());
        if(ObjectUtils.isEmpty(byUserNo)){
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage());
        }
        userRepository.deleteAdminByUserNo(byUserNo.getUserNo());
        gmailClient.sendRemoveConfirmationtoUser(byUserNo.getEmail());
    }

    @Override
    public Page<UserResultDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResultDto);
    }

    @Override
    public Page<AdminResultDto> findAllAdmin(Pageable pageable) {
        return userRepository.findAllAdmin(pageable).map(userMapper::toAdminResultDto);
    }

    @Override
    public void adminSetPassword(AdminSetPasswordRequestDto request) {
        AdminInviteToken byToken = adminInviteTokenRepository.findByToken(request.getToken());
        if(ObjectUtils.isEmpty(byToken)) {
            throw new BusinessException(ErrorCode.TOKEN_NOT_FOUND.getCode(), ErrorCode.TOKEN_NOT_FOUND.getMessage());
        }
        User user = byToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(true);
        userRepository.save(user);
        byToken.setStatus(AdminInviteToken.Status.USED);
    }
}
