package org.huhu.test.platform.controller;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.model.request.VariableModifyRequest;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.huhu.test.platform.service.TestPlatformVariableService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.VARIABLE_NAME;

/**
 * 测试平台变量 {@link RestController} 类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
@Validated
@RestController
@RequestMapping("/variable")
public class TestPlatformVariableController {

    private final TestPlatformVariableService variableService;

    private final TestPlatformUserProfileService userProfileService;

    TestPlatformVariableController(
            TestPlatformVariableService variableService,
            TestPlatformUserProfileService userProfileService) {
        this.variableService = variableService;
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public Flux<VariableQueryResponse> queryVariables(Mono<Authentication> authentication) {
        var username = authentication
                .map(Authentication::getName);
        var profile = authentication
                .map(Authentication::getName)
                .flatMap(userProfileService::queryTestPlatformUserActiveProfile);
        return Mono.zip(username, profile, ConvertUtils::toVariablesQueryVo)
                   .flatMapMany(variableService::queryTestPlatformVariables);
    }

    @GetMapping("/{variableName}")
    public Flux<VariableQueryResponse> queryVariable(Mono<Authentication> authentication,
            @PathVariable(name = "variableName", required = false) @Pattern(regexp = VARIABLE_NAME) String variableName) {
        var username = authentication
                .map(Authentication::getName);
        var profile = authentication
                .map(Authentication::getName)
                .flatMap(userProfileService::queryTestPlatformUserActiveProfile);
        return Mono.zip(username, profile, Mono.just(variableName))
                   .map(ConvertUtils::toVariableQueryVo)
                   .flatMapMany(variableService::queryTestPlatformVariable);
    }

    @PutMapping
    public Mono<Void> createVariable(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<VariableModifyRequest> request) {
        var username = authentication
                .map(Authentication::getName);
        var profile = authentication
                .map(Authentication::getName)
                .flatMap(userProfileService::queryTestPlatformUserActiveProfile);
        return Mono.zip(username, profile, request)
                   .map(ConvertUtils::toVariableCreateVo)
                   .flatMap(variableService::createTestPlatformVariable);
    }

    // todo 此处业务逻辑有bug待修复
    @PostMapping("/{variableName}")
    public Mono<Void> updateVariable(Mono<Authentication> authentication,
            @Validated @RequestBody Mono<VariableModifyRequest> request) {
        var username = authentication
                .map(Authentication::getName);
        var profile = authentication
                .map(Authentication::getName)
                .flatMap(userProfileService::queryTestPlatformUserActiveProfile);
        return Mono.zip(username, profile, request)
                   .map(ConvertUtils::toVariableUpdateVo)
                   .flatMap(variableService::updateTestPlatformVariable);
    }

    @DeleteMapping("/{variableName}")
    public Mono<Void> deleteVariable(Mono<Authentication> authentication,
            @PathVariable("variableName") @Size(max = 32) @Pattern(regexp = VARIABLE_NAME) String variableName,
            @RequestParam("variableScope") TestPlatformVariableScope variableScope) {
        var username = authentication
                .map(Authentication::getName);
        var profile = authentication
                .map(Authentication::getName)
                .flatMap(userProfileService::queryTestPlatformUserActiveProfile);
        return Mono.zip(username, profile, Mono.just(variableName), Mono.just(variableScope))
                   .map(ConvertUtils::toVariableDeleteVo)
                   .flatMap(variableService::deleteTestPlatformVariable);
    }

}
