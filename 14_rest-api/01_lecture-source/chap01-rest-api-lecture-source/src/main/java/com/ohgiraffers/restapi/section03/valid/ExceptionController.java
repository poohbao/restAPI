package com.ohgiraffers.restapi.section03.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(UserNotFoundException e) {

        String code = "ERROR_CODE_0001";
        String description = "회원 정보 조회에 실패하셨습니다.";
        String detail = e.getMessage();

        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e) {

        String code = "";
        String description = "";
        String detail = "";

        /* 유효성 검사에서 error 가 발생한다면? */
        if (e.getBindingResult().hasErrors()) {
            detail = e.getBindingResult().getFieldError().getDefaultMessage(); // e.getMessage() 동일한 의미
            System.out.println("detail = " + detail);

            // NotNull, size, NotBlank ...
            String bindResultCode = e.getBindingResult().getFieldError().getCode();

            switch (bindResultCode) {
                case "NotBlank" :
                    code = "ERROR_CODE_0002";
                    description = "필수 값이 누락되었습니다!!";
                    break;

                case "size" :
                    code = "ERROR_CODE_0003";
                    description = "글자 수를 확인해주세요...";
                    break;
            }
        }

        return new ResponseEntity<>(new ErrorResponse(code, description, detail), HttpStatus.BAD_REQUEST);

    }

}