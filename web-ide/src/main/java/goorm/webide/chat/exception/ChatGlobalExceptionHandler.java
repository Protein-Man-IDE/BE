package goorm.webide.chat.exception;

import goorm.webide.chat.dto.ChatApiResponse;
import goorm.webide.chat.dto.ChatRoomResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

/**
 * packageName    : goorm.webide.chat.exception
 * fileName       : ChatGlobalExceptionHandler
 * author         : won
 * date           : 2024/04/20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/20        won       최초 생성
 */

@ControllerAdvice
public class ChatGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ChatApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ChatApiResponse<String> response = ChatApiResponse.fail(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(ChatApiResponse.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ChatApiResponse<String>> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ChatApiResponse.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ChatApiResponse<String>> handleIllegalStateException(IllegalStateException ex) {
        return new ResponseEntity<>(ChatApiResponse.fail(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ChatApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ChatApiResponse.fail(ex.getMessage()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ChatApiResponse<String>> handleAllUncaughtException(Exception ex) {
        return new ResponseEntity<>(ChatApiResponse.fail("서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
