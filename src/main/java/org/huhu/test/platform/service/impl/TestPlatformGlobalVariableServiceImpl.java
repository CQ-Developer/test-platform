package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.response.QueryTestPlatformGlobalVariableResponse;
import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import org.huhu.test.platform.repository.TestPlatformGlobalVariableRepository;
import org.huhu.test.platform.service.TestPlatformGlobalVariableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

    private QueryTestPlatformGlobalVariableResponse buildQueryResponse(TestPlatformGlobalVariable globalVariable) {
        QueryTestPlatformGlobalVariableResponse response = new QueryTestPlatformGlobalVariableResponse();
        response.setVariableId(globalVariable.getVariableId());
        response.setVariableName(globalVariable.getVariableName());
        response.setVariableValue(globalVariable.getVariableValue());
        response.setVariableDescription(globalVariable.getVariableDescription());
        return response;
    }

    @Override
    public Flux<Void> deleteTestPlatformGlobalVariable(Long userId, Long variableId) {
        return variableRepository
                .findAll(Example.of(new TestPlatformGlobalVariable(userId)))
                .map(TestPlatformGlobalVariable::getVariableId)
                .filter(variableId::equals)
                .doOnNext(id -> logger.info("delete global variable {}", id))
                .flatMap(variableRepository::deleteById);
    }

}
