package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试平台用户环境 {@link RestController}
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@RestController
@RequestMapping("/user/profile")
public class TestPlatformUserProfileController {

    private final TestPlatformUserProfileService userProfileService;

    public TestPlatformUserProfileController(TestPlatformUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public Flux<UserProfileQueryResponse> queryUserProfile(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(userProfileService::queryTestPlatformUserProfile);
    }

}
