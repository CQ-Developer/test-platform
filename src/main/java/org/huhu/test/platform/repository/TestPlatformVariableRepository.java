package org.huhu.test.platform.repository;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 测试平台变量 {@link org.springframework.data.repository.Repository} 接口
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public interface TestPlatformVariableRepository extends R2dbcRepository<TestPlatformVariable, Long> {

    /**
     * 查询变量
     *
     * @param username 用户名
     */
    Flux<TestPlatformVariable> findByUsername(String username);

    /**
     * 查询变量
     *
     * @param username 用户名
     * @param variableName 变量名
     */
    Flux<TestPlatformVariable> findByUsernameAndVariableName(String username, String variableName);

    /**
     * 查询变量
     *
     * @param username 用户名
     * @param variableName 变量名
     * @param variableScope 变量作用域
     */
    Flux<TestPlatformVariable> findByUsernameAndVariableNameAndVariableScope(String username, String variableName, TestPlatformVariableScope variableScope);

    /**
     * 删除变量
     *
     * @param username 用户名
     * @param variableName 变量名
     * @param variableScope 变量作用域
     */
    Mono<Integer> deleteByUsernameAndVariableNameAndVariableScope(String username, String variableName, TestPlatformVariableScope variableScope);

}
