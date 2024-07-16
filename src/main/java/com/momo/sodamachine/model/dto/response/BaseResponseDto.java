package com.momo.sodamachine.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResponseDto {
    private String respCode;
    private String respMsg;
    private Object data;
}
