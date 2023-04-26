package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.*;
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
    public Flux<VariableQueryResponse> queryTestPlatformVariables(VariablesQueryVo vo) {
        return variableRepository
                .findByUsernameAndVariableProfile(vo.username(), vo.profileName())
                .map(ConvertUtils::toVariableQueryResponse);
    }

    @Override
    public Flux<VariableQueryResponse> queryTestPlatformVariable(VariableQueryVo vo) {
        return variableRepository
                .findByUsernameAndVariableProfileAndVariableName(vo.username(), vo.profileName(), vo.variableName())
                .map(ConvertUtils::toVariableQueryResponse);
    }

    @Override
    public Mono<Void> createTestPlatformVariable(VariableCreateVo vo) {
        return variableRepository
                .findByUsernameAndVariableProfileAndVariableNameAndVariableScope(vo.username(), vo.profileName(), vo.request().variableName(), vo.request().variableScope())
                .flatMap(i -> Mono.error(new ClientTestPlatformException("variable create error: exists")))
                .switchIfEmpty(Mono
                        .just(ConvertUtils.toTestPlatformVariable(vo))
                        .flatMap(variableRepository::save)
                        .doOnNext(i -> logger.info("create variable {}", i.variableName())))
                .then();
    }

    @Override
    public Mono<Void> updateTestPlatformVariable(VariableUpdateVo vo) {
        return variableRepository
                .setVariableValueAndVariableDescriptionFor(vo.request().variableValue(), vo.request().variableDescription(),
                        vo.username(), vo.variableProfile(), vo.request().variableName(), vo.request().variableScope())
                .doOnNext(i -> logger.info("update {} variable", i))
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformVariable(VariableDeleteVo vo) {
        return variableRepository
                .deleteByUsernameAndVariableProfileAndAndVariableNameAndVariableScope(
                        vo.username(), vo.variableProfile(), vo.variableName(), vo.variableScope())
                .doOnNext(i -> logger.info("delete {} variable.", i))
                .then();
    }

}
