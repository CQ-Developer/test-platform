package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.request.UpdateTestPlatformGlobalVariableRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.response.UpdateTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
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
    public Flux<QueryTestPlatformGlobalVariableResponse> queryTestPlatformGlobalVariables(Long userId) {
        return variableRepository
                .findAll(Example.of(new TestPlatformGlobalVariable(userId)))
                .map(this::buildQueryResponse);
    }

    @Override
    public Mono<Void> deleteTestPlatformGlobalVariable(Long variableId) {
        logger.info("delete global variable {}", variableId);
        return variableRepository.deleteById(variableId);
    }

    @Override
    public Mono<UpdateTestPlatformGlobalVariableResponse> updateTestPlatformGlobalVariable(UpdateTestPlatformGlobalVariableRequest request) {
        TestPlatformGlobalVariable globalVariable = new TestPlatformGlobalVariable();
        globalVariable.setVariableId(request.getVariableId());
        globalVariable.setVariableName(request.getVariableName());
        globalVariable.setVariableValue(request.getVariableValue());
        globalVariable.setVariableDescription(request.getVariableDescription());
        return variableRepository
                .save(globalVariable)
                .doOnNext(item -> logger.info("update global variable {}", item.getVariableId()))
                .map(this::buildUpdateResponse);
    }

    private QueryTestPlatformGlobalVariableResponse buildQueryResponse(TestPlatformGlobalVariable globalVariable) {
        QueryTestPlatformGlobalVariableResponse response = new QueryTestPlatformGlobalVariableResponse();
        response.setVariableId(globalVariable.getVariableId());
        response.setVariableName(globalVariable.getVariableName());
        response.setVariableValue(globalVariable.getVariableValue());
        response.setVariableDescription(globalVariable.getVariableDescription());
        return response;
    }

    private UpdateTestPlatformGlobalVariableResponse buildUpdateResponse(TestPlatformGlobalVariable globalVariable) {
        UpdateTestPlatformGlobalVariableResponse response = new UpdateTestPlatformGlobalVariableResponse();
        response.setVariableId(response.getVariableId());
        response.setVariableName(globalVariable.getVariableName());
        response.setVariableValue(globalVariable.getVariableValue());
        response.setVariableDescription(globalVariable.getVariableDescription());
        return response;
    }

}
