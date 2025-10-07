package com.food.foodorderapi.library.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {


    USER_NAME_ALREADY_EXIST("ERR001", "Username Already Exist"),
    PASSWORD_MISMATCH("ERR002", "Password Mismatch"),;





    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
