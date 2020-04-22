package com.seven.error;

/**
 * @Description: TODO
 * @Author chendongdong
 * @Date 2020/4/22 10:59
 * @Version V1.0
 **/
/**
 * 业务异常，在业务处理过程中，明确已知的异常
 *
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
