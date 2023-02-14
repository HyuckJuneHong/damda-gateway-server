package kr.co.damdagateway.error.exception;

import kr.co.damdagateway.error.model.ErrorCode;

public class JwtTokenInvalidException extends UserDefineException{
    public JwtTokenInvalidException(ErrorCode errorCode){
        super(errorCode);
    }
}