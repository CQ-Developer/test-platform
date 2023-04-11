package org.huhu.test.platform.controller;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.huhu.test.platform.model.response.GlobalVariableModifyResponse;
import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.model.vo.GlobalVariableDeleteVo;
import org.huhu.test.platform.model.vo.GlobalVariableUpdateVo;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
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
    public Mono<GlobalVariableModifyResponse> create(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<GlobalVariableModifyRequest> request) {
        return Mono.zip(authentication.map(Authentication::getName), request, GlobalVariableCreateVo::new)
                   .flatMap(globalVariableService::createTestPlatformGlobalVariable);
    }

    @PostMapping("/variable/{variableName}")
    public Mono<GlobalVariableModifyResponse> update(Mono<Authentication> authentication,
            @PathVariable("variableName") @Pattern(regexp = "^[A-Za-z0-9-_]{1,32}$") String variableName,
            @Validated @RequestBody Mono<GlobalVariableModifyRequest> request) {
        return Mono.zip(authentication.map(Authentication::getName), Mono.just(variableName), request)
                   .map(GlobalVariableUpdateVo::build)
                   .flatMap(globalVariableService::updateTestPlatformGlobalVariable);
    }

    @DeleteMapping("/variable/{variableName}")
    public Mono<Integer> delete(Mono<Authentication> authentication,
            @PathVariable("variableName") @Size(max = 32) @Pattern(regexp = "^[A-Za-z0-9-_]+$") String variableName) {
        return Mono.zip(authentication.map(Authentication::getName), Mono.just(variableName), GlobalVariableDeleteVo::new)
                   .flatMap(globalVariableService::deleteTestPlatformGlobalVariable);
    }

}
