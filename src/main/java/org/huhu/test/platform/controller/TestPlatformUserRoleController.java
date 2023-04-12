package org.huhu.test.platform.controller;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.request.UserRoleModifyRequest;
import org.huhu.test.platform.service.TestPlatformUserRoleService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/management/role")
public class TestPlatformUserRoleController {

    private final TestPlatformUserRoleService userRoleService;

    public TestPlatformUserRoleController(TestPlatformUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping
    public Flux<TestPlatformRoleName> query(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(userRoleService::queryTestPlatformUserRole);
    }

    @PutMapping
    public Mono<Void> create(@Validated @RequestBody Mono<UserRoleModifyRequest> request) {
        return request.flatMap(userRoleService::createTestPlatformUserRole);
    }

    @DeleteMapping
    public Mono<Void> delete(@Validated @RequestBody Mono<UserRoleModifyRequest> request) {
        return request.flatMap(userRoleService::deleteTestPlatformUseRole);
    }

}
