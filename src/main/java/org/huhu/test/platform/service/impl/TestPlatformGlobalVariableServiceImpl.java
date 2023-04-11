package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.huhu.test.platform.model.response.GlobalVariableModifyResponse;
import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.model.vo.GlobalVariableDeleteVo;
import org.huhu.test.platform.model.vo.GlobalVariableUpdateVo;
import org.huhu.test.platform.repository.TestPlatformGlobalVariableRepository;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class TestPlatformGlobalVariableServiceImpl implements TestPlatformGlobalVariableService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformGlobalVariableServiceImpl.class);

    private final TestPlatformGlobalVariableRepository variableRepository;

    public TestPlatformGlobalVariableServiceImpl(TestPlatformGlobalVariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    @Override
    public Flux<GlobalVariableQueryResponse> queryTestPlatformGlobalVariables(String username) {
        return variableRepository
                .findByUsername(username)
                .map(GlobalVariableQueryResponse::build);
    }

    @Override
    public Mono<GlobalVariableModifyResponse> updateTestPlatformGlobalVariable(GlobalVariableUpdateVo vo) {
        var deleteVariable = Mono
                .zip(Mono.just(vo.username()), Mono.just(vo.variableName()), GlobalVariableDeleteVo::new);
        var createVariable = Mono
                .zip(Mono.just(vo.username()), Mono.just(vo.request()), GlobalVariableCreateVo::new);
        return deleteVariable
                .flatMap(this::deleteTestPlatformGlobalVariable)
                .then(createVariable)
                .flatMap(this::createTestPlatformGlobalVariable);
    }

    @Override
    public Mono<GlobalVariableModifyResponse> createTestPlatformGlobalVariable(GlobalVariableCreateVo vo) {
        var globalVariable = new TestPlatformGlobalVariable(vo.username());
        GlobalVariableModifyRequest request = vo.request();
        globalVariable.setVariableName(request.variableName());
        globalVariable.setVariableValue(request.variableValue());
        Optional.ofNullable(request.variableDescription()).ifPresent(globalVariable::setVariableDescription);
        return variableRepository
                .save(globalVariable)
                .doOnNext(item -> logger.info("save global variable {}", item.getVariableName()))
                .map(GlobalVariableModifyResponse::build);
    }

    @Override
    public Mono<Integer> deleteTestPlatformGlobalVariable(GlobalVariableDeleteVo vo) {
        return variableRepository
                .deleteByUsernameAndVariableName(vo.username(), vo.variableName())
                .doOnNext(i -> logger.info("delete {} global variable.", i));
    }

}
