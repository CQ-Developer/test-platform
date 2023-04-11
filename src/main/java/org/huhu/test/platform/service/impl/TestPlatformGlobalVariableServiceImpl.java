package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.response.GlobalVariableUpdateResponse;
import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import org.huhu.test.platform.model.vo.UpdateGlobalVariableVo;
import org.huhu.test.platform.repository.TestPlatformGlobalVariableRepository;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .findAll(Example.of(new TestPlatformGlobalVariable(username)))
                .map(this::buildQueryResponse);
    }

    @Override
    public Mono<Void> deleteTestPlatformGlobalVariable(Long variableId) {
        logger.info("delete global variable {}", variableId);
        return variableRepository.deleteById(variableId);
    }

    @Override
    public Mono<GlobalVariableUpdateResponse> updateTestPlatformGlobalVariable(UpdateGlobalVariableVo vo) {
        TestPlatformGlobalVariable globalVariable = new TestPlatformGlobalVariable();
        globalVariable.setVariableId(vo.variableId());
        globalVariable.setVariableName(vo.variableName());
        globalVariable.setVariableValue(vo.variableValue());
        globalVariable.setVariableDescription(vo.variableDescription());
        return variableRepository
                .save(globalVariable)
                .doOnNext(item -> logger.info("update global variable {}", item.getVariableId()))
                .map(this::buildUpdateResponse);
    }

    private GlobalVariableQueryResponse buildQueryResponse(TestPlatformGlobalVariable globalVariable) {
        GlobalVariableQueryResponse response = new GlobalVariableQueryResponse();
        response.setVariableId(globalVariable.getVariableId());
        response.setVariableName(globalVariable.getVariableName());
        response.setVariableValue(globalVariable.getVariableValue());
        response.setVariableDescription(globalVariable.getVariableDescription());
        return response;
    }

    private GlobalVariableUpdateResponse buildUpdateResponse(TestPlatformGlobalVariable globalVariable) {
        GlobalVariableUpdateResponse response = new GlobalVariableUpdateResponse();
        response.setVariableId(response.getVariableId());
        response.setVariableName(globalVariable.getVariableName());
        response.setVariableValue(globalVariable.getVariableValue());
        response.setVariableDescription(globalVariable.getVariableDescription());
        return response;
    }

}
