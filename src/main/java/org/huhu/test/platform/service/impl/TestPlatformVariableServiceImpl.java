package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableDeleteVo;
import org.huhu.test.platform.model.vo.VariableQueryVo;
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
    public Flux<VariableQueryResponse> queryTestPlatformVariable(String username) {
        return variableRepository
                .findByUsername(username)
                .map(ConvertUtils::toVariableQueryResponse);
    }

    @Override
    public Flux<VariableQueryResponse> queryTestPlatformVariable(VariableQueryVo vo) {
        return variableRepository
                .findByUsernameAndVariableName(vo.username(), vo.variableName())
                .map(ConvertUtils::toVariableQueryResponse);
    }

    @Override
    public Mono<Void> createTestPlatformVariable(VariableCreateVo vo) {
        return variableRepository
                .findByUsernameAndVariableNameAndVariableScope(vo.username(), vo.request().variableName(), vo.request().variableScope())
                .switchIfEmpty(variableRepository.save(ConvertUtils.toTestPlatformVariable(vo)))
                .doOnNext(i -> logger.info("save variable {}.", i.getVariableName()))
                .then();
    }

    @Override
    public Mono<Void> updateTestPlatformVariable(VariableUpdateVo vo) {
        var deleteVariable = Mono
                .zip(Mono.just(vo.username()), Mono.just(vo.variableName()), Mono.just(vo.variableScope()))
                .map(ConvertUtils::toVariableDeleteVo)
                .flatMap(this::deleteTestPlatformVariable);
        var createVariable = Mono
                .zip(Mono.just(vo.username()), Mono.just(vo.request()), VariableCreateVo::new)
                .flatMap(this::createTestPlatformVariable);
        return deleteVariable.then(createVariable);
    }

    @Override
    public Mono<Void> deleteTestPlatformVariable(VariableDeleteVo vo) {
        return variableRepository
                .deleteByUsernameAndVariableNameAndVariableScope(vo.username(), vo.variableName(), vo.variableScope())
                .doOnNext(i -> logger.info("delete {} variable.", i))
                .then();
    }

}
