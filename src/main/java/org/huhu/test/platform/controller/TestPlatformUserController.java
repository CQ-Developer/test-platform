package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformUserResponse;
import org.huhu.test.platform.model.response.QueryTestPlatformUsersResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/management")
public class TestPlatformUserController {

    private final TestPlatformUserService userService;

    public TestPlatformUserController(TestPlatformUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public Flux<QueryTestPlatformUsersResponse> query() {
        return userService.queryTestPlatformUsers();
    }

    @GetMapping("/user/{username}")
    public Mono<QueryTestPlatformUserResponse> query(@PathVariable("username") String username) {
        return userService.queryTestPlatformUser(username);
    }

    @PutMapping("/user")
    public Mono<Void> create(@RequestBody @Validated Mono<AddTestPlatformUserRequest> request) {
        return request.flatMap(userService::createTestPlatformUser);
    }

}
