package org.huhu.test.platform.controller;

import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.model.request.UserCreateRequest;
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

@Validated
@RestController
@RequestMapping("/management/user")
public class TestPlatformUserController {

    private final TestPlatformUserService userService;

    public TestPlatformUserController(TestPlatformUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserQueryResponse> query() {
        return userService.queryTestPlatformUsers();
    }

    @GetMapping("/{username}")
    public Mono<UserDetailQueryResponse> query(
            @PathVariable("username") @Pattern(regexp = "^[A-Za-z0-9-_]{4,16}$") String username) {
        return userService.queryTestPlatformUser(username);
    }

    @PutMapping
    public Mono<Void> create(@Validated @RequestBody Mono<UserCreateRequest> request) {
        return request.flatMap(userService::createTestPlatformUser);
    }

    @DeleteMapping("/{username}")
    public Mono<Void> delete(
            @PathVariable("username") @Pattern(regexp = "^[A-Za-z0-9-_]{4,16}$") String username) {
        // todo 不允许删除root用户
        return userService.deleteTestPlatformUser(username);
    }

}
