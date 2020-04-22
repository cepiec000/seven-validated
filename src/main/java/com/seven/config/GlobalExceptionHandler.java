package com.seven.config;

import com.seven.common.ApiResult;
import com.seven.error.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * @author chendongdong
 * @since 2019-06-01
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 请求方法类型不存在
     *
     * @param e
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ApiResult handle(HttpServletRequest request,HttpRequestMethodNotSupportedException e) {
        String msg = "请求 method:" + request.getMethod() + " URI:" + request.getRequestURI() + " 因为 method 不匹配";
        log.error(msg);
        return ApiResult.fail(HttpStatus.METHOD_NOT_ALLOWED.value(),HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), msg);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ApiResult handle(HttpServletRequest request, NoHandlerFoundException e) {
        String msg = "没有找到 " + request.getMethod() + " URI:" + request.getRequestURI() + " 资源";
        log.error(msg);
        return ApiResult.fail(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), msg);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult requestParameterHandler(HttpServletRequest request, Exception ex) {
        String msg = "请求 " + request.getRequestURI() + " 参数有误，" + ex.getMessage();
        log.error(msg);
        return ApiResult.fail(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),msg);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public ApiResult handlerMethodArgumentNotValid(HttpServletRequest request, Exception ex) {
        log.error("参数异常 [path={}][code={}][msg={}][message={}]",request.getRequestURI(),HttpStatus.BAD_REQUEST.value(),ex.getMessage(),ex);
        BindingResult bindingResult;
        TreeMap result;
        List list;
        Iterator var8;
        FieldError error;
        if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            bindingResult = bindException.getBindingResult();
            result = new TreeMap();
            list = bindingResult.getFieldErrors();
            var8 = list.iterator();

            while (var8.hasNext()) {
                error = (FieldError) var8.next();
                result.put(error.getField(), error.getDefaultMessage());
            }

            return ApiResult.fail(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), result);
        } else if (!(ex instanceof MethodArgumentNotValidException)) {
            return ApiResult.fail(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        } else {
            MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) ex;
            bindingResult = notValidException.getBindingResult();
            result = new TreeMap();
            list = bindingResult.getFieldErrors();
            var8 = list.iterator();

            while (var8.hasNext()) {
                error = (FieldError) var8.next();
                result.put(error.getField(), error.getDefaultMessage());
            }

            return ApiResult.fail(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), result);
        }
    }
    /** 普通参数校验校验不通过会抛出 ConstraintViolationException
     * 必填参数没传校验不通过会抛出 ServletRequestBindingException
     * */
    @ExceptionHandler({ConstraintViolationException.class, ServletRequestBindingException.class})
    @ResponseBody
    public ApiResult handleValidationException(Exception ex) {
        String msg="";
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException t = (ConstraintViolationException) ex;
            msg = t.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
        } else if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException t = (MissingServletRequestParameterException) ex;
            msg = t.getParameterName() + " 不能为空";
        }
        log.error("{}-ex:{}",msg,ex);
        return ApiResult.fail(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),msg);
    }

    /**
     * 统一处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiResult handleBusinessException(BusinessException t) {
        log.error("捕获到业务异常, {}, msg: {}", t.getMessage(),t);
        return  ApiResult.fail("业务异常:"+t.getMessage());
    }


    /**
     * 统一处理未知异常
     */
    @ExceptionHandler
    @ResponseBody
    public ApiResult handleUnknownException(Throwable t) {
        // 未知异常
        log.error("捕获到未经处理的未知异常, {}", t);
        return ApiResult.unknownError("服务器未知异常:"+t.getMessage());
    }


}
