package kr.co.damdagateway.error.exception;

import kr.co.damdagateway.error.model.ErrorCode;

public class JwtTokenExpiredException extends BusinessLogicException{
    public JwtTokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
