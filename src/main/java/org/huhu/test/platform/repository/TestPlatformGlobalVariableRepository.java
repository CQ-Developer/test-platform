package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformGlobalVariableRepository extends R2dbcRepository<TestPlatformGlobalVariable, Long> {

    /**
     * 查询全局变量
     *
     * @param username 用户名
     */
    Flux<TestPlatformGlobalVariable> findByUsername(String username);

    /**
     * 删除全局变量
     *
     * @param username 用户名
     * @param variableName 变量名
     */
    Mono<Integer> deleteByUsernameAndVariableName(String username, String variableName);

}
