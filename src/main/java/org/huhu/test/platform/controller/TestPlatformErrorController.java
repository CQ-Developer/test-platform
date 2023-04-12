package org.huhu.test.platform.controller;

import jakarta.validation.ConstraintViolationException;
import org.huhu.test.platform.exception.TestPlatformException;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatformError.CLIENT_ERROR;
import static org.huhu.test.platform.constant.TestPlatformError.CLIENT_REQUEST_PARAMETER_INVALID;
import static org.huhu.test.platform.constant.TestPlatformError.SERVER_ERROR;

@RestControllerAdvice
public class TestPlatformErrorController {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformErrorController.class);

    @ExceptionHandler({WebExchangeBindException.class, ConstraintViolationException.class, ServerWebInputException.class})
    public Mono<ErrorResponse> handleWebExchangeBindException(Exception exception) {
        logger.error("request parameter error.", exception);
        return Mono.just(ErrorResponse.from(CLIENT_REQUEST_PARAMETER_INVALID));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public Mono<ErrorResponse> handleMethodNotAllowedException(MethodNotAllowedException exception) {
        logger.error("client request error.", exception);
        return Mono.just(ErrorResponse.from(CLIENT_ERROR));
    }

    @ExceptionHandler(TestPlatformException.class)
    public Mono<ErrorResponse> handleTestPlatformException(TestPlatformException exception) {
        logger.error("test platform error.", exception);
        return Mono.just(ErrorResponse.from(exception));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleException(Exception exception) {
        logger.error("server unknown error.", exception);
        return Mono.just(ErrorResponse.from(SERVER_ERROR));
    }

}
