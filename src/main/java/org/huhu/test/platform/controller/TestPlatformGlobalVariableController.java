package org.huhu.test.platform.controller;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.model.vo.GlobalVariableDeleteVo;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.huhu.test.platform.util.ConvertUtils;
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

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.VARIABLE_NAME;

/**
 * 测试平台全局变量 {@link RestController} 类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Validated
@RestController
@RequestMapping("/variable/global")
public class TestPlatformGlobalVariableController {

    private final TestPlatformGlobalVariableService globalVariableService;

    TestPlatformGlobalVariableController(
            TestPlatformGlobalVariableService globalVariableService) {
        this.globalVariableService = globalVariableService;
    }

    @GetMapping
    public Flux<GlobalVariableQueryResponse> queryGlobalVariable(Mono<Authentication> authentication) {
        var username = authentication.map(Authentication::getName);
        return username.flatMapMany(globalVariableService::queryTestPlatformGlobalVariables);
    }

    @PutMapping
    public Mono<Void> createGlobalVariable(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<GlobalVariableModifyRequest> request) {
        var username = authentication.map(Authentication::getName);
        return Mono.zip(username, request, GlobalVariableCreateVo::new)
                   .flatMap(globalVariableService::createTestPlatformGlobalVariable);
    }

    @PostMapping("/{variableName}")
    public Mono<Void> updateGlobalVariable(Mono<Authentication> authentication,
            @PathVariable("variableName") @Pattern(regexp = VARIABLE_NAME) String variableName,
            @Validated @RequestBody Mono<GlobalVariableModifyRequest> request) {
        var username = authentication.map(Authentication::getName);
        return Mono.zip(username, Mono.just(variableName), request)
                   .map(ConvertUtils::from)
                   .flatMap(globalVariableService::updateTestPlatformGlobalVariable);
    }

    @DeleteMapping("/{variableName}")
    public Mono<Void> deleteGlobalVariable(Mono<Authentication> authentication,
            @PathVariable("variableName") @Size(max = 32) @Pattern(regexp = VARIABLE_NAME) String variableName) {
        var username = authentication.map(Authentication::getName);
        return Mono.zip(username, Mono.just(variableName), GlobalVariableDeleteVo::new)
                   .flatMap(globalVariableService::deleteTestPlatformGlobalVariable);
    }

}
