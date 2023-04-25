package org.huhu.test.platform.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.constant.TestPlatformUserModifyPath;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserModifyRequest;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.springframework.security.core.Authentication;
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
    public Mono<UserDetailQueryResponse> querySelfUser(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMap(userService::queryTestPlatformUserDetail);
    }

    @GetMapping("/{username}")
    public Mono<UserDetailQueryResponse> queryUserDetail(@PathVariable("username") @Pattern(regexp = USERNAME) String username) {
        return userService.queryTestPlatformUserDetail(username);
    }

    @GetMapping("/all")
    public Flux<UserQueryResponse> queryUsers() {
        return userService.queryTestPlatformUser();
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

    @PostMapping("/{modify}")
    public Mono<Void> modifyUser(@PathVariable("modify") TestPlatformUserModifyPath modifyPath,
            @Validated @RequestBody Mono<UserModifyRequest> request) {
        return switch (modifyPath) {
            case RENEW -> request.flatMap(userService::renewTestPlatformUser);
            case ENABLE -> request.map(UserModifyRequest::username).flatMap(userService::enableTestPlatformUser);
            case DISABLE -> request.map(UserModifyRequest::username).flatMap(userService::disableTestPlatformUser);
            case LOCK -> request.map(UserModifyRequest::username).flatMap(userService::lockTestPlatformUser);
            case UNLOCK -> request.map(UserModifyRequest::username).flatMap(userService::unlockTestPlatformUser);
        };
    }

}
