package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.GlobalVariableCreateRequest;
import org.huhu.test.platform.model.request.GlobalVariableUpdateRequest;
import org.huhu.test.platform.model.response.GlobalVariableCreateResponse;
import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.response.GlobalVariableUpdateResponse;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.model.vo.GlobalVariableUpdateVo;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
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

@RestController
@RequestMapping("/global")
public class TestPlatformGlobalVariableController {

    private final TestPlatformGlobalVariableService globalVariableService;

    public TestPlatformGlobalVariableController(
            TestPlatformGlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }

    @GetMapping("/variable")
    public Flux<GlobalVariableQueryResponse> query(Mono<Authentication> authentication) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(globalVariableService::queryTestPlatformGlobalVariables);
    }

    @PutMapping("/variable")
    public Mono<GlobalVariableCreateResponse> create(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<GlobalVariableCreateRequest> request) {
        return Mono.zip(authentication.map(Authentication::getName), request, GlobalVariableCreateVo::build)
                   .flatMap(globalVariableService::createTestPlatformGlobalVariable);
    }

    @PostMapping("/variable/{variableId}")
    public Mono<GlobalVariableUpdateResponse> update(Mono<Authentication> authentication,
            @PathVariable("variableId") Long variableId,
            @Validated @RequestBody Mono<GlobalVariableUpdateRequest> request) {
        return Mono.zip(authentication.map(Authentication::getName), Mono.just(variableId), request)
                   .map(GlobalVariableUpdateVo::build)
                   .flatMap(globalVariableService::updateTestPlatformGlobalVariable);
    }

    @DeleteMapping("/variable/{variableId}")
    public Flux<Void> delete(Mono<Authentication> authentication, @PathVariable("variableId") Long variableId) {
        return authentication
                .map(Authentication::getName)
                .flatMapMany(globalVariableService::queryTestPlatformGlobalVariables)
                .map(GlobalVariableQueryResponse::getVariableId)
                .filter(variableId::equals)
                .flatMap(globalVariableService::deleteTestPlatformGlobalVariable);
    }

}
