package com.mark.ifamily.exception;


import javax.ws.rs.core.Response.Status;

/**
 * rest service 异常
 *
 * Date: 13-8-23 下午4:44
 */
public class HttpServiceException extends RpcServiceException {

    protected Status status;

    public HttpServiceException(int errorCode, Status status) {
        this(errorCode,status,null);
    }
    public HttpServiceException(int errorCode, Status status, Throwable cause) {
        super(status.getReasonPhrase(),cause);
        this.errorCode = errorCode;
        this.status = status;
    }
    public Status getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public static class BadRequestException extends HttpServiceException {
        public BadRequestException() {
            super(ERROR_CODE_BAD_REQUEST,Status.BAD_REQUEST);
        }
    }

    public static class AuthenticationException extends HttpServiceException {
        public AuthenticationException() {
            super(ERROR_CODE_UNAUTHORIZED,Status.UNAUTHORIZED);
        }
    }

    public static class ServiceUnavailableException extends HttpServiceException {
        public ServiceUnavailableException(Throwable cause) {
            super(ERROR_CODE_SERVICE_UNAVAILABLE,Status.SERVICE_UNAVAILABLE,cause);
        }
    }

    public static class InternalException extends HttpServiceException {
        public InternalException(Throwable cause) {
            super(ERROR_CODE_INTERNAL_SERVER_ERROR,Status.INTERNAL_SERVER_ERROR,cause);
        }
    }

}
