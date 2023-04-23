package org.huhu.test.platform.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserModifyRequest;
import org.huhu.test.platform.model.request.UserRenewRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;
import static org.huhu.test.platform.constant.TestPlatformDefaultName.ROOT_USERNAME;

/**
 * 测试平台用户 {@link RestController} 类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Validated
@RestController
@RequestMapping("/user")
public class TestPlatformUserController {

    private final TestPlatformUserService userService;

    TestPlatformUserController(TestPlatformUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserQueryResponse> queryUser() {
        return userService.queryTestPlatformUser();
    }

    @GetMapping("/{username}")
    public Mono<UserDetailQueryResponse> queryUserDetail(@PathVariable("username") @Pattern(regexp = USERNAME) String username) {
        return userService.queryTestPlatformUser(username);
    }

    @PutMapping
    public Mono<Void> createUser(@Validated @RequestBody Mono<UserCreateRequest> request) {
        return request.flatMap(userService::createTestPlatformUser);
    }

    @DeleteMapping("/{username}")
    public Mono<Void> deleteUser(@PathVariable("username") @Pattern(regexp = USERNAME) String username) {
        if (StrUtil.equals(ROOT_USERNAME, username)) {
            return Mono.error(new ClientTestPlatformException("user root delete error"));
        }
        return userService.deleteTestPlatformUser(username);
    }

    @PostMapping("/renew")
    public Mono<Void> renewUser(@Validated @RequestBody Mono<UserRenewRequest> request) {
        return request.flatMap(userService::renewTestPlatformUser);
    }

    @PostMapping("/enable")
    public Mono<Void> enableUser(@Validated @RequestBody Mono<UserModifyRequest> request) {
        return request.map(UserModifyRequest::username).flatMap(userService::enableTestPlatformUser);
    }

    @PostMapping("/disable")
    public Mono<Void> disableUser(@Validated @RequestBody Mono<UserModifyRequest> request) {
        return request.map(UserModifyRequest::username).flatMap(userService::disableTestPlatformUser);
    }

    @PostMapping("/lock")
    public Mono<Void> lockUser(@Validated @RequestBody Mono<UserModifyRequest> request) {
        return request.map(UserModifyRequest::username).flatMap(userService::lockTestPlatformUser);
    }

    @PostMapping("/unlock")
    public Mono<Void> unlockUser(@Validated @RequestBody Mono<UserModifyRequest> request) {
        return request.map(UserModifyRequest::username).flatMap(userService::unlockTestPlatformUser);
    }

}
