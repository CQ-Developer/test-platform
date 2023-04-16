package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformVariableRepository extends R2dbcRepository<TestPlatformVariable, Long> {

    /**
     * 查询变量
     *
     * @param username 用户名
     */
    Flux<TestPlatformVariable> findByUsername(String username);

    /**
     * 删除变量
     *
     * @param username 用户名
     * @param variableName 变量名
     */
    Mono<Integer> deleteByUsernameAndVariableName(String username, String variableName);

}
