package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UserCreationRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public Flux<UserQueryResponse> query() {
        return userService.queryTestPlatformUsers();
    }

    @GetMapping("/user/{username}")
    public Mono<UserDetailQueryResponse> query(@PathVariable("username") String username) {
        return userService.queryTestPlatformUser(username);
    }

    @PutMapping("/user")
    public Mono<Void> create(@RequestBody @Validated Mono<UserCreationRequest> request) {
        return request.flatMap(userService::createTestPlatformUser);
    }

    @DeleteMapping("/user/{username}")
    public Mono<Void> delete(@PathVariable("username") String username) {
        return userService.deleteTestPlatformUser(username);
    }

}
