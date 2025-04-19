package com.springboot.bus2tangticket.dto.response.responseUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private ResponseStatus status;
    private String message;
    private T data;
}

