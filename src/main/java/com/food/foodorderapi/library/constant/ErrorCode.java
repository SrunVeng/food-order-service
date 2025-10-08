package com.food.foodorderapi.library.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {


    USER_NAME_ALREADY_EXIST("ERR001", "Username Already Exist"),
    EMAIL_ALREADY_EXIST("ERR002", "Email Already Exist"),
    PASSWORD_MISMATCH("ERR003", "Password Mismatch"),
    OTP_INVALID("ERR004", "OTP Invalid Please Request new One"),
    OTP_EXPIRED("ERR005", "OTP Expired"),
    OTP_INCORRECT("ERR006", "OTP Incorrect"),
    EMAIL_DOES_NOT_EXIST("ERR007", "Email Does Not Exist"),
    INVALID_EXPIRED_RESET_LINK("ERR008", " Invalid or expired reset link"),
    TOKEN_NOT_FOUND("ERR009", "TOKEN Not Found"),
    USER_NOT_FOUND("ERR010", "USER Not Found"),
    MENU_NOT_FOUND("ERR011", "MENU Not Found"),
    RESTAURANT_NOT_FOUND("ERR012", "Restaurant Not Found"),;






    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
