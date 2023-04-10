package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.security.TestPlatformUserDetails;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/global")
public class TestPlatformGlobalVariableController {

    private final TestPlatformGlobalVariableService globalVariableService;

    public TestPlatformGlobalVariableController(TestPlatformGlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }

    @GetMapping("/variables")
    public Flux<QueryTestPlatformGlobalVariableResponse> query(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getPrincipal)
                .cast(TestPlatformUserDetails.class)
                .map(TestPlatformUserDetails::getUserId)
                .flatMapMany(globalVariableService::queryTestPlatformGlobalVariables);
    }

    @DeleteMapping("/variable/{id}")
    public Flux<Void> delete(Mono<Authentication> authentication, @PathVariable("id") Long variableId) {
        return authentication
                .map(Authentication::getPrincipal)
                .cast(TestPlatformUserDetails.class)
                .map(TestPlatformUserDetails::getUserId)
                .flatMapMany(userId -> globalVariableService.deleteTestPlatformGlobalVariable(userId, variableId));
    }

}
