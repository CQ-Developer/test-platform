package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.response.CaseQueryResponse;
import org.huhu.test.platform.service.TestPlatformCaseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试平台用例 {@link RestController} 类
 *
 * @author 18551681083@163.com
 * @since 0.0.2
 */
@RestController
public class TestPlatformCaseController {

    private final TestPlatformCaseService caseService;

    TestPlatformCaseController(TestPlatformCaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    public Flux<CaseQueryResponse> queryCase(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(caseService::queryTestPlatformCase);
    }

}
