package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.response.TestPlatformErrorResponse;
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
    public ResponseEntity<TestPlatformErrorResponse> handleWebExchangeBindException(WebExchangeBindException exception) {
        logger.error("request parameter error.", exception);
        return ResponseEntity.ok(TestPlatformErrorResponse.clientError().withError(CLIENT_REQUEST_PARAMETER_INVALID));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TestPlatformErrorResponse> handleException(Exception exception) {
        logger.error("server unknown error.", exception);
        return ResponseEntity.ok(TestPlatformErrorResponse.serverError());
    }

}
