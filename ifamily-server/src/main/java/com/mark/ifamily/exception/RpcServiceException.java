package com.mark.ifamily.exception;

/**
 * Date: 13-9-2下午1:48
 */
public class RpcServiceException extends Exception {
    protected static int ERROR_CODE_INTERNAL_SERVER_ERROR = 1000;
    protected static int ERROR_CODE_BAD_REQUEST = 1001;
    protected static int ERROR_CODE_UNAUTHORIZED = 1002;
    protected static int ERROR_CODE_SERVICE_UNAVAILABLE = 1003;
    protected static int ERROR_CODE_SERVICE_BUSY = 1004;

    protected int errorCode;

    public RpcServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public RpcServiceException(String message, Throwable cause) {
        this(message, cause,0);
    }

    public RpcServiceException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public static class BadRequestException extends RpcServiceException {
        public BadRequestException() {
            super(ERROR_CODE_BAD_REQUEST);
        }
    }

    public static class AuthenticationException extends RpcServiceException {
        public AuthenticationException() {
            super(ERROR_CODE_UNAUTHORIZED);
        }
    }
    
    public static class ServiceBusyException extends RpcServiceException{
    	public ServiceBusyException(){
    		super(ERROR_CODE_SERVICE_BUSY);
    	}
    }

        public static class InternalException extends RpcServiceException {
        public InternalException(Throwable cause) {
            super(cause.getMessage(),cause,ERROR_CODE_INTERNAL_SERVER_ERROR);
        }
    }
}
