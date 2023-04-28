package org.huhu.test.platform.repository;

import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
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
     * @param variableProfile 变量环境
     */
    Flux<TestPlatformVariable> findByUsernameAndVariableProfile(String username, String variableProfile);

    /**
     * 查询变量
     *
     * @param username 用户名
     * @param variableProfile 环境名
     * @param variableName 变量名
     */
    Flux<TestPlatformVariable> findByUsernameAndVariableProfileAndVariableName(
            String username, String variableProfile, String variableName);

    /**
     * 查询变量是否存在
     *
     * @param username 用户名
     * @param variableProfile 环境名
     * @param variableName 变量名
     * @param variableScope 变量作用域
     */
    Mono<Boolean> existsByUsernameAndVariableProfileAndVariableNameAndVariableScope(
            String username, String variableProfile, String variableName, TestPlatformVariableScope variableScope);

    /**
     * 更新变量
     *
     * @param variableValue 变量值
     * @param variableDescription 变量描述
     * @param username 用户名
     * @param variableProfile 环境名
     * @param variableName 变量名
     * @param variableScope 变量作用域
     */
    @Query("""
            update t_test_variable
            set variable_value = :variableValue,
                variable_description = :variableDescription
            where username = :username
              and variable_profile = :variableProfile
              and variable_name = :variableName
              and variable_scope = :variableScope
            """)
    @Modifying
    Mono<Integer> setVariableValueAndVariableDescriptionFor(String variableValue, String variableDescription,
            String username, String variableProfile, String variableName, TestPlatformVariableScope variableScope);

    /**
     * 删除变量
     *
     * @param username 用户名
     */
    Mono<Integer> deleteByUsername(String username);

    /**
     * 删除变量
     *
     * @param username 用户名
     * @param variableProfile 变量环境
     * @param variableName 变量名
     * @param variableScope 变量作用域
     */
    Mono<Integer> deleteByUsernameAndVariableProfileAndAndVariableNameAndVariableScope(
            String username, String variableProfile, String variableName, TestPlatformVariableScope variableScope);

}
