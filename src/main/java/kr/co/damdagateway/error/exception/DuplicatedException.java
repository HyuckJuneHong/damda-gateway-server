package kr.co.damdagateway.error.exception;

import kr.co.damdagateway.error.model.ErrorCode;

public class DuplicatedException extends BusinessLogicException{
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
