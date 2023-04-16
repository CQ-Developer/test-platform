package org.huhu.test.platform.controller;

import jakarta.validation.ConstraintViolationException;
import org.huhu.test.platform.exception.TestPlatformException;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatformErrorCode.CLIENT_ERROR;
import static org.huhu.test.platform.constant.TestPlatformErrorCode.SERVER_ERROR;

/**
 * 测试平台异常统一处理
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@RestControllerAdvice
public class TestPlatformErrorController {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformErrorController.class);

    /**
     * 处理请求参数或请求方法异常
     *
     * @param exception 异常
     */
    @ExceptionHandler({ConstraintViolationException.class,
            ServerWebInputException.class, MethodNotAllowedException.class})
    public Mono<ErrorResponse> handleClientError(Exception exception) {
        logger.error("client request error.", exception);
        return Mono.just(ConvertUtils.toErrorResponse(CLIENT_ERROR));
    }

    /**
     * 处理测试平台错误
     *
     * @param exception 异常
     */
    @ExceptionHandler(TestPlatformException.class)
    public Mono<ErrorResponse> handleTestPlatformError(TestPlatformException exception) {
        logger.info("test platform error.", exception);
        return Mono.just(ConvertUtils.toErrorResponse(exception));
    }

    /**
     * 处理服务端未知异常
     *
     * @param exception 异常
     */
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleUnknownError(Exception exception) {
        logger.error("server unknown error.", exception);
        return Mono.just(ConvertUtils.toErrorResponse(SERVER_ERROR));
    }

}
