package org.huhu.test.platform.controller;

import jakarta.validation.constraints.Pattern;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.vo.UserRoleDeleteVo;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;

@Validated
@RestController
@RequestMapping("/user/role")
public class TestPlatformUserRoleController {

    private final TestPlatformUserRoleService userRoleService;

    TestPlatformUserRoleController(TestPlatformUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping
    public Flux<TestPlatformRoleLevel> queryUserRole(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(userRoleService::queryTestPlatformUserRole);
    }

    @PutMapping
    public Mono<Void> createUserRole(@Validated @RequestBody Mono<UserRoleCreateRequest> request) {
        return request.flatMap(userRoleService::createTestPlatformUserRole);
    }

    @DeleteMapping("/{roleLevel}")
    public Mono<Void> deleteUserRole(@PathVariable("roleLevel") TestPlatformRoleLevel roleLevel,
            @RequestParam("username") @Pattern(regexp = USERNAME) String username) {
        return userRoleService.deleteTestPlatformUseRole(new UserRoleDeleteVo(username, roleLevel));
    }

}
