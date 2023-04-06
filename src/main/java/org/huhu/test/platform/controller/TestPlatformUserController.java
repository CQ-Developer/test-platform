package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rest/user")
public class TestPlatformUserController {

    private final TestPlatformUserService testPlatformUserService;

    public TestPlatformUserController(TestPlatformUserService testPlatformUserService) {
        this.testPlatformUserService = testPlatformUserService;
    }

    @PostMapping("/add")
    public Mono<Void> addTestPlatformUser(
            @RequestBody @Validated Mono<AddTestPlatformUserRequest> request) {
        return request.flatMap(testPlatformUserService::saveTestPlatformUser);
    }

}
