package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableDeleteVo;
import org.huhu.test.platform.model.vo.VariableUpdateVo;
import org.huhu.test.platform.repository.TestPlatformVariableRepository;
import org.huhu.test.platform.service.TestPlatformVariableService;
import org.huhu.test.platform.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TestPlatformVariableServiceImpl implements TestPlatformVariableService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformVariableServiceImpl.class);

    private final TestPlatformVariableRepository variableRepository;

    TestPlatformVariableServiceImpl(TestPlatformVariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    @Override
    public Flux<VariableQueryResponse> queryTestPlatformGlobalVariables(String username) {
        return variableRepository
                .findByUsername(username)
                .map(ConvertUtils::toVariableQueryResponse);
    }

    @Override
    public Mono<Void> updateTestPlatformGlobalVariable(VariableUpdateVo vo) {
        var username = Mono.just(vo.username());
        // 删除变量
        var variableName = Mono.just(vo.variableName());
        var deleteVariable = Mono
                .zip(username, variableName, VariableDeleteVo::new)
                .flatMap(this::deleteTestPlatformGlobalVariable);
        // 新增变量
        var request = Mono.just(vo.request());
        var createVariable = Mono
                .zip(username, request, VariableCreateVo::new)
                .flatMap(this::createTestPlatformGlobalVariable);
        // 执行
        return deleteVariable.then(createVariable);
    }

    @Override
    public Mono<Void> createTestPlatformGlobalVariable(VariableCreateVo vo) {
        return variableRepository
                .save(ConvertUtils.toTestPlatformVariable(vo))
                .doOnNext(item -> logger.info("save global variable {}", item.getVariableName()))
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformGlobalVariable(VariableDeleteVo vo) {
        return variableRepository
                .deleteByUsernameAndVariableName(vo.username(), vo.variableName())
                .doOnNext(i -> logger.info("delete {} global variable.", i))
                .then();
    }

}
