package org.huhu.test.platform.controller;

import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.model.request.UserProfileCreateRequest;
import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import org.huhu.test.platform.model.vo.UserProfileModifyVo;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.springframework.security.core.Authentication;
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

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USER_PROFILE;

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

    TestPlatformUserProfileController(TestPlatformUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public Flux<UserProfileQueryResponse> queryUserProfile(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(userProfileService::queryTestPlatformUserProfile);
    }

    @PutMapping
    public Mono<Void> createUserProfile(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<UserProfileCreateRequest> request) {
        var profileName = request.map(UserProfileCreateRequest::profileName);
        return authentication
                .map(Authentication::getName)
                .zipWith(profileName, UserProfileModifyVo::new)
                .flatMap(userProfileService::createTestPlatformUserProfile);
    }

    @DeleteMapping("/{profileName}")
    public Mono<Void> deleteUserProfile(Mono<Authentication> authentication,
            @Pattern(regexp = USER_PROFILE) @PathVariable("profileName") String profileName) {
        return authentication
                .map(Authentication::getName)
                .zipWith(Mono.just(profileName), UserProfileModifyVo::new)
                .flatMap(userProfileService::deleteTestPlatformUserProfile);
    }

}
