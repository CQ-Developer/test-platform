package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import static org.huhu.test.platform.constant.TestPlatformError.CLIENT_REQUEST_PARAMETER_INVALID;

@RestControllerAdvice
public class TestPlatformErrorController {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformErrorController.class);

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException exception) {
        logger.error("request parameter error.", exception);
        return ResponseEntity.ok(ErrorResponse.clientError().withError(CLIENT_REQUEST_PARAMETER_INVALID));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        logger.error("server unknown error.", exception);
        return ResponseEntity.ok(ErrorResponse.serverError());
    }

}
