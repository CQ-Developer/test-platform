package org.huhu.test.platform.controller;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.request.UserProfileModifyRequest;
import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import org.huhu.test.platform.model.vo.UserProfileModifyVo;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
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
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USER_PROFILE;
import static org.huhu.test.platform.constant.TestPlatformDefaultName.DEFAULT_PROFILE_NAME;

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
    public Mono<UserProfileQueryResponse> queryUserProfile(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMap(userProfileService::queryTestPlatformUserProfile);
    }

    @PutMapping
    public Mono<Void> createUserProfile(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<UserProfileModifyRequest> request) {
        var profile = request.map(UserProfileModifyRequest::profileName);
        return authentication
                .map(Authentication::getName)
                .zipWith(profile, UserProfileModifyVo::new)
                .flatMap(userProfileService::createTestPlatformUserProfile);
    }

    @PostMapping
    public Mono<Void> activeUserProfile(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<UserProfileModifyRequest> request) {
        var profile = request.map(UserProfileModifyRequest::profileName);
        return authentication
                .map(Authentication::getName)
                .zipWith(profile, UserProfileModifyVo::new)
                .flatMap(userProfileService::activeTestPlatformUserProfile);
    }

    @DeleteMapping("/{profileName}")
    public Mono<Void> deleteUserProfile(Mono<Authentication> authentication,
            @Pattern(regexp = USER_PROFILE) @PathVariable("profileName") String profileName) {
        if (StrUtil.equals(DEFAULT_PROFILE_NAME, profileName)) {
            throw new ClientTestPlatformException("profile default delete error");
        }
        return authentication
                .map(Authentication::getName)
                .zipWith(Mono.just(profileName), UserProfileModifyVo::new)
                .flatMap(userProfileService::deleteTestPlatformUserProfile);
    }

}
