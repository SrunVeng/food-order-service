package com.food.foodorderapi.library.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {


    USER_NAME_ALREADY_EXIST("ERR001", "UserName Already Exist");






    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
