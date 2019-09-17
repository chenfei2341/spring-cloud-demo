
/*
 * <pre>
 * 标  题: ControllerExceptionHandler.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-09-02 // 输入完成日期
 * </pre>
 * <pre>
 * 修改记录1:
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author Chenfei
 */

package com.znv.peim.northbound.common.interceptor;

import com.znv.peim.northbound.common.bean.ErrorMessageResult;
import com.znv.peim.northbound.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * 统一错误处理
 * @author ChenFei
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 参数校验
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessageResult constraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e.toString(), e);
        return new ErrorMessageResult(HttpStatus.FORBIDDEN.value(), "参数校验失败");
    }

    /**
     * 参数错误，非法参数，非Request参数
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResult illegalArgumentException(IllegalArgumentException e) {
        logger.error(e.getMessage(), e);
        return new ErrorMessageResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * 404
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageResult noHandlerFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        return new ErrorMessageResult(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    /**
     * 400 - Bad Request
     * 参数类型错误
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    public ErrorMessageResult handleMissingServletRequestParameterException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ErrorMessageResult(HttpStatus.BAD_REQUEST.value(), "参数错误：" + e.getMessage());
    }

    /**
     * 业务主动抛出错误
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessageResult businessException(BusinessException e) {
        logger.error(e.getMessage(), e);
        return new ErrorMessageResult(e.getCode(), e.getMessage());
    }

    /**
     * 统一错误处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageResult unknownException(Exception e) {
        logger.error(e.getMessage(), e);
        return new ErrorMessageResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
    }
}