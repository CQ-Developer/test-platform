package org.huhu.test.platform.util;

import org.huhu.test.platform.constant.TestPlatformErrorCode;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.exception.ServerTestPlatformException;
import org.huhu.test.platform.exception.TestPlatformException;
import org.huhu.test.platform.model.request.GlobalVariableModifyRequest;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.GlobalVariableQueryResponse;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.model.vo.GlobalVariableCreateVo;
import org.huhu.test.platform.model.vo.GlobalVariableUpdateVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 类型转换工具类
 *
 * @author 18551681083@163.com
 * @since 0.0.1
 */
public class ConvertUtils {

    /**
     * 私有构造函数
     */
    private ConvertUtils() {}

    /**
     * 将 {@link TestPlatformErrorCode} 转换到为 {@link ErrorResponse}
     *
     * @param error 错误码
     */
    public static ErrorResponse from(TestPlatformErrorCode error) {
        return new ErrorResponse(error.getErrorCode(), error.getErrorMessage());
    }

    /**
     * 将 {@link TestPlatformException} 转换到为 {@link TestPlatformException}
     *
     * @param exception 异常
     */
    public static ErrorResponse from(TestPlatformException exception) {
        return from(exception.getTestPlatformError());
    }

    /**
     * 将 {@link TestPlatformGlobalVariable} 转换到为 {@link GlobalVariableQueryResponse}
     *
     * @param globalVariable 全局变量表
     */
    public static GlobalVariableQueryResponse from(TestPlatformGlobalVariable globalVariable) {
        return new GlobalVariableQueryResponse(globalVariable.getVariableName(),
                globalVariable.getVariableValue(), globalVariable.getVariableDescription());
    }

    /**
     * 将 {@link TestPlatformUser} {@link Byte} 转换到为 {@link UserDetailQueryResponse}
     *
     * @param user 用户
     * @param roleLevels 用户角色
     */
    public static UserDetailQueryResponse from(TestPlatformUser user, List<TestPlatformRoleLevel> roleLevels) {
        return new UserDetailQueryResponse(user.getUsername(), roleLevels,
                user.getEnabled(), user.getLocked(), user.getRegisterTime(), user.getExpiredTime());
    }

    /**
     * 将 {@link TestPlatformUserRole} 转换到为 {@link Mono<UserQueryResponse>}
     *
     * @param groupedFlux 用户名分组
     */
    public static Mono<UserQueryResponse> monoFrom(
            GroupedFlux<String, TestPlatformUserRole> groupedFlux) {
        var username = Mono.just(groupedFlux.key());
        var roleNames = groupedFlux.map(TestPlatformUserRole::getRoleLevel).collectList();
        return Mono.zip(username, roleNames, UserQueryResponse::new);
    }

    /**
     * 将 {@link GlobalVariableCreateVo} 转换到为 {@link TestPlatformGlobalVariable}
     *
     * @param vo 变量创建值对象
     */
    public static TestPlatformGlobalVariable from(GlobalVariableCreateVo vo) {
        var globalVariable = new TestPlatformGlobalVariable();
        globalVariable.setUsername(vo.username());
        GlobalVariableModifyRequest request = vo.request();
        globalVariable.setVariableName(request.variableName());
        globalVariable.setVariableValue(request.variableValue());
        Optional.ofNullable(request.variableDescription())
                .ifPresent(globalVariable::setVariableDescription);
        return globalVariable;
    }

    /**
     * 将 {@link UserCreateRequest} 转换到为 {@link TestPlatformUser}
     *
     * @param request 用户创建请求
     */
    public static TestPlatformUser from(UserCreateRequest request) {
        var user = new TestPlatformUser();
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setExpiredTime(LocalDateTime.now().plusYears(1L));
        return user;
    }

    /**
     * 将 {@link UserRoleCreateRequest} 转换到为 {@link UserRoleCreateRequest}
     *
     * @param request 用户角色创建请求
     */
    public static TestPlatformUserRole from(UserRoleCreateRequest request) {
        return new TestPlatformUserRole(request.roleName(), request.username());
    }

    /**
     * 将 {@link UserRoleCreateRequest} 转换到为 {@link Flux<TestPlatformUserRole>}
     *
     * @param request 用户角色创建请求
     */
    public static Flux<TestPlatformUserRole> fluxFrom(UserCreateRequest request) {
        var roles = request.roleLevel();
        var roleName = Flux.fromIterable(roles);
        var username = Flux.just(request.username()).repeat(roles.size() - 1);
        return Flux.zip(roleName, username, TestPlatformUserRole::new);
    }

    /**
     * 将 {@link Tuple3} 转换到为 {@link GlobalVariableUpdateVo}
     *
     * @param tuple3 用户名 变量名 变量变更请求
     */
    public static GlobalVariableUpdateVo from(Tuple3<String, String, GlobalVariableModifyRequest> tuple3) {
        return new GlobalVariableUpdateVo(tuple3.getT1(), tuple3.getT2(), tuple3.getT3());
    }

    /**
     * 将 {@link Byte} 转换到为 {@link TestPlatformRoleLevel}
     *
     * @param roleLevel 角色级别
     */
    public static TestPlatformRoleLevel from(Byte roleLevel) {
        for (TestPlatformRoleLevel testPlatformRoleLevel : TestPlatformRoleLevel.values()) {
            if (testPlatformRoleLevel.getLevel() == roleLevel.intValue()) {
                return testPlatformRoleLevel;
            }
        }
        throw new ServerTestPlatformException("database role level invalid");
    }

}
