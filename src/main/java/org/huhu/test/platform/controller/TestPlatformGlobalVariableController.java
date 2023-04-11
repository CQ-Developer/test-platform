package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UpdateTestPlatformGlobalVariableRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.response.UpdateTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.vo.UpdateGlobalVariableVo;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/global")
public class TestPlatformGlobalVariableController {

    private final TestPlatformGlobalVariableService globalVariableService;

    public TestPlatformGlobalVariableController(
            TestPlatformGlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }

    @GetMapping("/variable")
    public Flux<QueryTestPlatformGlobalVariableResponse> query(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(globalVariableService::queryTestPlatformGlobalVariables);
    }

    @PutMapping("/variable")
    public Mono<Void> create() {
        return Mono.empty();
    }

    @PostMapping("/variable/{variableId}")
    public Mono<UpdateTestPlatformGlobalVariableResponse> update(Mono<Authentication> authentication,
            @PathVariable("variableId") Long variableId, Mono<UpdateTestPlatformGlobalVariableRequest> request) {
        return Mono.zip(authentication.map(Authentication::getName), Mono.just(variableId), request)
                   .map(UpdateGlobalVariableVo::fromTuple3)
                   .flatMap(globalVariableService::updateTestPlatformGlobalVariable);
    }

    @DeleteMapping("/variable/{variableId}")
    public Flux<Void> delete(Mono<Authentication> authentication, @PathVariable("variableId") Long variableId) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(globalVariableService::queryTestPlatformGlobalVariables)
                .map(QueryTestPlatformGlobalVariableResponse::getVariableId)
                .filter(variableId::equals)
                .flatMap(globalVariableService::deleteTestPlatformGlobalVariable);
    }

}
