package org.huhu.test.platform.service.impl;

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

@Service
public class TestPlatformGlobalVariableServiceImpl implements TestPlatformGlobalVariableService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformGlobalVariableServiceImpl.class);

    private final TestPlatformGlobalVariableRepository variableRepository;

    TestPlatformGlobalVariableServiceImpl(TestPlatformGlobalVariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    @Override
    public Flux<GlobalVariableQueryResponse> queryTestPlatformGlobalVariables(String username) {
        return variableRepository
                .findByUsername(username)
                .map(GlobalVariableQueryResponse::from);
    }

    @Override
    public Mono<Void> updateTestPlatformGlobalVariable(GlobalVariableUpdateVo vo) {
        var username = Mono.just(vo.username());
        // 删除全局变量
        var variableName = Mono.just(vo.variableName());
        var deleteVariable = Mono
                .zip(username, variableName, GlobalVariableDeleteVo::new)
                .flatMap(this::deleteTestPlatformGlobalVariable);
        // 新增全局变量
        var request = Mono.just(vo.request());
        var createVariable = Mono
                .zip(username, request, GlobalVariableCreateVo::new)
                .flatMap(this::createTestPlatformGlobalVariable);
        // 执行
        return deleteVariable.then(createVariable);
    }

    @Override
    public Mono<Void> createTestPlatformGlobalVariable(GlobalVariableCreateVo vo) {
        return variableRepository
                .save(TestPlatformGlobalVariable.from(vo))
                .doOnNext(item -> logger.info("save global variable {}", item.getVariableName()))
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformGlobalVariable(GlobalVariableDeleteVo vo) {
        return variableRepository
                .deleteByUsernameAndVariableName(vo.username(), vo.variableName())
                .doOnNext(i -> logger.info("delete {} global variable.", i))
                .then();
    }

}
