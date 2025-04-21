package com.springboot.bus2tangticket.config;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        String message = "Lỗi ràng buộc dữ liệu";

        if (rootCause instanceof SQLIntegrityConstraintViolationException) {
            message = "Không thể xóa do ràng buộc foreign key";
        }

        return ResponseEntity.badRequest().body(
                new BaseResponse<>(ResponseStatus.FAILED, message, null)
        );
    }
}
