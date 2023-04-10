package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UpdateTestPlatformGlobalVariableRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.response.UpdateTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.security.TestPlatformUserDetails;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
                .map(Authentication::getPrincipal)
                .cast(TestPlatformUserDetails.class)
                .map(TestPlatformUserDetails::getUserId)
                .flatMapMany(globalVariableService::queryTestPlatformGlobalVariables);
    }

    @PostMapping("/variable/{variableId}")
    public Flux<UpdateTestPlatformGlobalVariableResponse> update(Mono<Authentication> authentication,
            @PathVariable("variableId") Long variableId,
            Mono<UpdateTestPlatformGlobalVariableRequest> request) {
        return query(authentication)
                .map(QueryTestPlatformGlobalVariableResponse::getVariableId)
                .filter(variableId::equals)
                .zipWith(request, (id, item) -> {
                    item.setVariableId(id);
                    return item;
                })
                .flatMap(globalVariableService::updateTestPlatformGlobalVariable);
    }

    @DeleteMapping("/variable/{variableId}")
    public Flux<Void> delete(Mono<Authentication> authentication,
            @PathVariable("variableId") Long variableId) {
        return query(authentication)
                .map(QueryTestPlatformGlobalVariableResponse::getVariableId)
                .filter(variableId::equals)
                .flatMap(globalVariableService::deleteTestPlatformGlobalVariable);
    }

}
