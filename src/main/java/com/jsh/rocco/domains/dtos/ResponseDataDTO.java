package com.jsh.rocco.domains.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseDataDTO {
    private int code;
    private String message;
    private Object data;
}
