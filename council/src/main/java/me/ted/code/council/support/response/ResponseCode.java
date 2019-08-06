package me.ted.code.council.support.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    OK("0000", "Success"),
    INTERNAL_ERROR("9000", "Internal error");

    private final String code;
    private final String message;
}
