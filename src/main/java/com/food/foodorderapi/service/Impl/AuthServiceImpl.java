package com.food.foodorderapi.service.Impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
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
import org.springframework.util.StringUtils;

import com.food.foodorderapi.client.Gmail.GmailClient;
import com.food.foodorderapi.dto.request.*;
import com.food.foodorderapi.dto.response.AdminResultDto;
import com.food.foodorderapi.dto.response.UserLoginResultDto;
import com.food.foodorderapi.dto.response.UserProfileResultDto;
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
    public void userRegister(UserRegisterRequestDto userRegisterRequestDto) {

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
        if (!userRegisterRequestDto.getPassword().equals(userRegisterRequestDto.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH.getCode(), ErrorCode.PASSWORD_MISMATCH.getMessage());
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
        gmailClient.sendOtpForRegister(userRegisterRequestDto.getEmail(), code);
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
        user.setGender(requestDto.getGender());
        user.setDob(requestDto.getDob());
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
        gmailClient.sendResetLink(user.getEmail(), presetToken.getToken());

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
        User byusername = userRepository.findByusername(requestDto.getUsername());
        if(!ObjectUtils.isEmpty(byusername)){
            throw new BusinessException(ErrorCode.USER_NAME_ALREADY_EXIST.getCode(), ErrorCode.USER_NAME_ALREADY_EXIST.getMessage());
        }
        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setGender(requestDto.getGender());
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
        String token = TokenGenerator.generateToken();
        AdminInviteToken tokenset = new AdminInviteToken();
        tokenset.setToken(token);
        tokenset.setUser(user);
        tokenset.setStatus(AdminInviteToken.Status.OPEN);
        adminInviteTokenRepository.save(tokenset);
        gmailClient.sendAdminInvite(user.getEmail(), user.getUsername(), token);
    }

    @Override
    @Transactional
    public void deleteAdmin(AdminDeleteRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.USER_NOT_FOUND.getCode(),
                        ErrorCode.USER_NOT_FOUND.getMessage()
                ));

        var normalized = user.getRoles().stream()
                .map(Role::getName)
                .map(s -> s == null ? "" : s.toUpperCase().replaceFirst("^ROLE_", ""))
                .toList();

        boolean isAdmin = normalized.contains("ADMIN");
        boolean isSuperAdmin = normalized.contains("SUPERADMIN");

        if (!isAdmin || isSuperAdmin) {
            // adjust error as you like
            throw new BusinessException("403", "Not allowed to delete this user");
        }
        adminInviteTokenRepository.deleteAllByUserId(user.getId());
        userRepository.delete(user);     // <-- lets JPA clean join table properly
        userRepository.flush();          // optional: execute SQL immediately
        gmailClient.sendRemoveConfirmationtoUser(user.getEmail());
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
        if (ObjectUtils.isEmpty(byToken)) {
            throw new BusinessException(ErrorCode.TOKEN_NOT_FOUND.getCode(), ErrorCode.TOKEN_NOT_FOUND.getMessage());
        }
        User user = byToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsVerified(true);
        userRepository.save(user);
        byToken.setStatus(AdminInviteToken.Status.USED);
        adminInviteTokenRepository.save(byToken);
    }

    @Override
    public void updateAdmin(AdminUpdateRequestDto requestDto) {

    }

    @Override
    public UserProfileResultDto getbyUsername(String username) {
        User byusername = userRepository.findByusername(username);
        if (ObjectUtils.isEmpty(byusername)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage());
        }
        return userMapper.toUserProfileResultDto(byusername);
    }

    @Override
    public void deletebyUsername(String username) {
        User byusername = userRepository.findByusername(username);
        if (ObjectUtils.isEmpty(byusername)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage());
        }
        userRepository.delete(byusername);
    }

    @Override
    public void updateByUsername(String username, UserUpdateProfileRequestDto dto) {
        User user = userRepository.findByusername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage());
        }

        boolean dirty = false;

        dirty |= setIfChanged(user::setFirstName, user.getFirstName(), dto.getFirstName());
        dirty |= setIfChanged(user::setLastName, user.getLastName(), dto.getLastName());
        dirty |= setIfChanged(user::setPhoneNumber, user.getPhoneNumber(), dto.getPhoneNumber());
        dirty |= setIfChanged(user::setGender, user.getGender(), dto.getGender());

        // DOB (adjust setter/type if it's LocalDate)
        if (StringUtils.hasText(dto.getDob())) {
            dirty |= setIfChanged(user::setDob, user.getDob(), dto.getDob());
        }

        // Email change → send OTP (do NOT update user.email here)
        if (StringUtils.hasText(dto.getEmail())) {
            String targetEmail = dto.getEmail().trim();

            // If email actually changes, validate uniqueness then send OTP
            if (!Objects.equals(user.getEmail(), targetEmail)) {
                // 1) Uniqueness guard (case-insensitive)
                if (userRepository.existsByEmailIgnoreCase(targetEmail)) {
                    // if that email is already owned by *this* user, it's fine; otherwise block
                    if (!Objects.equals(user.getEmail(), targetEmail)) {
                        throw new BusinessException("Email is already in used", "Email is already in use.");
                    }
                }

                Instant now = Instant.now();

                // 2) Close any existing OPEN OTPs for this email/purpose
                var openList = emailVerificationOTPRepository
                        .findOpenByEmailAndPurpose(targetEmail, EmailVerificationOTP.Purpose.UPDATE_EMAIL);
                for (EmailVerificationOTP o : openList) {
                    if (o.getExpiresAt() != null && o.getExpiresAt().isBefore(now)) {
                        o.setStatus(EmailVerificationOTP.Status.EXPIRED);
                    } else {
                        o.setStatus(EmailVerificationOTP.Status.USED);
                    }
                }
                if (!openList.isEmpty()) {
                    emailVerificationOTPRepository.saveAll(openList);
                }

                // 3) Create a fresh OTP
                String code = OTPGenerator.generate6DigitOtp();

                EmailVerificationOTP rec = new EmailVerificationOTP();
                rec.setEmail(targetEmail);
                rec.setOtp(code);
                rec.setPurpose(EmailVerificationOTP.Purpose.UPDATE_EMAIL);
                rec.setStatus(EmailVerificationOTP.Status.OPEN);
                rec.setAttempts(0);
                rec.setCreatedAt(now);
                rec.setExpiresAt(now.plus(Duration.ofMinutes(5)));
                emailVerificationOTPRepository.save(rec);

                gmailClient.sendOtpForUpdateEmail(targetEmail, code);
            }
        }

        if (dirty) {
            userRepository.save(user);
        }

    }

    @Override
    @Transactional
    public void verifyUpdateEmail(String username, UserUpdateProfileVerifyRequestDto dto) {
        User user = userRepository.findByusername(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage());
        }

        String emailParam = Optional.ofNullable(dto.getEmail()).map(String::trim).orElse("");
        String otpParam = Optional.ofNullable(dto.getOtp()).map(String::trim).orElse("");

        if (!StringUtils.hasText(emailParam) || !StringUtils.hasText(otpParam)) {
            throw new BusinessException(ErrorCode.OTP_INVALID.getCode(), ErrorCode.OTP_INVALID.getMessage());
        }

        // fetch OPEN OTPs for that email/purpose and match by code
        var openList = emailVerificationOTPRepository
                .findOpenByEmailAndPurpose(emailParam, EmailVerificationOTP.Purpose.UPDATE_EMAIL);
        if (openList.isEmpty()) {
            throw new BusinessException(ErrorCode.OTP_INVALID.getCode(), ErrorCode.OTP_INVALID.getMessage());
        }

        // Find the newest record that matches the submitted code
        Optional<EmailVerificationOTP> matchedOpt = openList.stream()
                .filter(o -> Objects.equals(o.getOtp(), otpParam))
                .max(Comparator.comparing(EmailVerificationOTP::getCreatedAt));

        // If code doesn't match any OPEN record → increment attempts on the newest open record and fail
        if (matchedOpt.isEmpty()) {
            EmailVerificationOTP newest = openList.stream()
                    .max(Comparator.comparing(EmailVerificationOTP::getCreatedAt))
                    .orElse(openList.getFirst()); // safe for Java 8+

            int attempts = Optional.of(newest.getAttempts()).orElse(0) + 1;
            newest.setAttempts(attempts);
            // Optional lockout after N bad tries: mark EXPIRED to force a new code
            if (attempts >= 5) {
                newest.setStatus(EmailVerificationOTP.Status.EXPIRED);
            }
            emailVerificationOTPRepository.save(newest);

            throw new BusinessException(ErrorCode.OTP_INVALID.getCode(), ErrorCode.OTP_INVALID.getMessage());
        }

        EmailVerificationOTP rec = matchedOpt.get();
        Instant now = Instant.now();

        if (rec.getExpiresAt() != null && rec.getExpiresAt().isBefore(now)) {
            rec.setStatus(EmailVerificationOTP.Status.EXPIRED);
            emailVerificationOTPRepository.save(rec);
            throw new BusinessException(ErrorCode.OTP_EXPIRED.getCode(), ErrorCode.OTP_EXPIRED.getMessage());
        }

        // Success → consume the OTP and update email
        rec.setStatus(EmailVerificationOTP.Status.USED);
        emailVerificationOTPRepository.save(rec);

        // Guard again for email uniqueness race
        if (userRepository.existsByEmailIgnoreCase(rec.getEmail())
                && !Objects.equals(user.getEmail(), rec.getEmail())) {
            throw new BusinessException("Email is already in use", "Email is already in use.");
        }

        user.setEmail(rec.getEmail());
        userRepository.save(user);

        gmailClient.sendConfirmationRegistered(user.getEmail());
    }

    private boolean setIfChanged(Consumer<String> setter, String current, String incoming) {
        if (StringUtils.hasText(incoming) && !Objects.equals(current, incoming)) {
            setter.accept(incoming);
            return true;
        }
        return false;
    }
}
