package com.jsh.rocco.domains.enums.results;

public enum EmailAuthResult implements Result<CommonResult>{
    EMAIL_CODE_ALREADY_EXISTS,
    FAILURE_DUPLICATE_EMAIL
}
