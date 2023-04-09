package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformUserResponse;
import org.huhu.test.platform.model.response.QueryTestPlatformUsersResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/management")
public class TestPlatformUserController {

    private final TestPlatformUserService userService;

    public TestPlatformUserController(TestPlatformUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Flux<QueryTestPlatformUsersResponse> queryTestPlatformUsers() {
        return userService.queryTestPlatformUsers();
    }

    @GetMapping("/user/{username}")
    public Mono<QueryTestPlatformUserResponse> queryTestPlatformUser(
            @PathVariable("username") String username) {
        return userService.queryTestPlatformUser(username);
    }

    @PutMapping("/user")
    public Mono<Void> addTestPlatformUser(
            @RequestBody @Validated Mono<AddTestPlatformUserRequest> request) {
        return request.flatMap(userService::saveTestPlatformUser);
    }

}
