package goorm.webide.user.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrors {
    REGISTER_FAIL(false ,"회원가입에 실패했습니다."),
    REGISTER_SUCCESS(true,"회원가입에 성공했습니다.");

    private final Boolean result;
    private final String message;
}