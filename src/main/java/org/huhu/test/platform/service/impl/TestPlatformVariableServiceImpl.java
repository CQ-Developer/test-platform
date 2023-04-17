package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformVariable;
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

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

@Service
public class TestPlatformVariableServiceImpl implements TestPlatformVariableService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformVariableServiceImpl.class);

    private final TestPlatformVariableRepository variableRepository;

    TestPlatformVariableServiceImpl(TestPlatformVariableRepository variableRepository) {
        this.variableRepository = variableRepository;
    }

    @Override
    public Flux<VariableQueryResponse> queryTestPlatformVariable(String username) {
        return queryTestPlatformVariable(variableRepository.findByUsername(username));
    }

    @Override
    public Flux<VariableQueryResponse> queryTestPlatformVariable(VariableQueryVo vo) {
        return queryTestPlatformVariable(variableRepository.findByUsernameAndVariableName(vo.username(), vo.variableName()));
    }

    /**
     * 查询变量
     *
     * @param findVariable 查询结果
     */
    private Flux<VariableQueryResponse> queryTestPlatformVariable(Flux<TestPlatformVariable> findVariable) {
        return findVariable
                .map(ConvertUtils::toVariableQueryResponse)
                .sort(comparing(VariableQueryResponse::variableName)
                        .thenComparing(VariableQueryResponse::variableScope, comparingInt(TestPlatformVariableScope::getScope)));
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
