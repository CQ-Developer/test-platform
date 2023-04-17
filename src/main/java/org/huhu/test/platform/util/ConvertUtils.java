package org.huhu.test.platform.util;

import cn.hutool.core.util.ObjectUtil;
import org.huhu.test.platform.constant.TestPlatformErrorCode;
import org.huhu.test.platform.constant.TestPlatformRoleLevel;
import org.huhu.test.platform.constant.TestPlatformVariableScope;
import org.huhu.test.platform.exception.ServerTestPlatformException;
import org.huhu.test.platform.exception.TestPlatformException;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserRoleCreateRequest;
import org.huhu.test.platform.model.request.VariableModifyRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.UserDetailQueryResponse;
import org.huhu.test.platform.model.response.UserQueryResponse;
import org.huhu.test.platform.model.response.UserRoleQueryResponse;
import org.huhu.test.platform.model.response.VariableQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUser;
import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.huhu.test.platform.model.table.TestPlatformVariable;
import org.huhu.test.platform.model.vo.VariableCreateVo;
import org.huhu.test.platform.model.vo.VariableDeleteVo;
import org.huhu.test.platform.model.vo.VariableUpdateVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuple4;

import java.time.LocalDateTime;
import java.util.List;

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
     * 将 {@link TestPlatformErrorCode} 转换为 {@link ErrorResponse}
     *
     * @param error 错误码
     */
    public static ErrorResponse toErrorResponse(TestPlatformErrorCode error) {
        return new ErrorResponse(error.getErrorCode(), error.getErrorMessage());
    }

    /**
     * 将 {@link TestPlatformException} 转换为 {@link TestPlatformException}
     *
     * @param exception 异常
     */
    public static ErrorResponse toErrorResponse(TestPlatformException exception) {
        return toErrorResponse(exception.getTestPlatformError());
    }

    /**
     * 将 {@link TestPlatformVariable} 转换为 {@link VariableQueryResponse}
     *
     * @param globalVariable 变量表
     */
    public static VariableQueryResponse toVariableQueryResponse(TestPlatformVariable globalVariable) {
        return new VariableQueryResponse(globalVariable.variableName(), globalVariable.variableValue(),
                globalVariable.variableScope(), globalVariable.variableDescription());
    }

    /**
     * 将 {@link TestPlatformUser} {@link Byte} 转换为 {@link UserDetailQueryResponse}
     *
     * @param user 用户
     * @param roleLevels 用户角色
     */
    public static UserDetailQueryResponse toUserDetailQueryResponse(TestPlatformUser user, List<TestPlatformRoleLevel> roleLevels) {
        return new UserDetailQueryResponse(user.username(), roleLevels,
                user.enabled(), user.locked(), user.registerTime(), user.expiredTime());
    }

    /**
     * 将 {@link TestPlatformUserRole} 转换为 {@link Mono<UserQueryResponse>}
     *
     * @param groupedFlux 用户名分组
     */
    public static Mono<UserQueryResponse> toUserQueryResponse(
            GroupedFlux<String, TestPlatformUserRole> groupedFlux) {
        var username = Mono.just(groupedFlux.key());
        var roleNames = groupedFlux.map(TestPlatformUserRole::roleLevel).collectList();
        return Mono.zip(username, roleNames, UserQueryResponse::new);
    }

    /**
     * 将 {@link VariableCreateVo} 转换为 {@link TestPlatformVariable}
     *
     * @param vo 变量创建值对象
     */
    public static TestPlatformVariable toTestPlatformVariable(VariableCreateVo vo) {
        return new TestPlatformVariable(null,
                vo.request().variableName(),
                vo.request().variableValue(),
                vo.request().variableScope(),
                vo.request().variableDescription(),
                vo.username());
    }

    /**
     * 将 {@link UserCreateRequest} 转换为 {@link TestPlatformUser}
     *
     * @param request 用户创建请求
     */
    public static TestPlatformUser toTestPlatformUser(UserCreateRequest request, String encodedPassword) {
        var expiredTime = ObjectUtil.isNull(request.expiredTime()) ? LocalDateTime.now().plusYears(1L) : request.expiredTime();
        return new TestPlatformUser(null, request.username(), encodedPassword, null, null, null, expiredTime);
    }

    /**
     * 将 {@link UserRoleCreateRequest} 转换为 {@link UserRoleCreateRequest}
     *
     * @param request 用户角色创建请求
     */
    public static TestPlatformUserRole toTestPlatformUserRole(UserRoleCreateRequest request) {
        return new TestPlatformUserRole(null, request.roleLevel(), request.username());
    }

    /**
     * 将 {@link UserRoleCreateRequest} 转换为 {@link Flux<TestPlatformUserRole>}
     *
     * @param request 用户角色创建请求
     */
    public static Flux<TestPlatformUserRole> toTestPlatformUserRole(UserCreateRequest request) {
        var roles = request.roleLevel();
        var roleLevel = Flux.fromIterable(roles);
        var username = Flux.just(request.username()).repeat(roles.size() - 1);
        return Flux.zip(username, roleLevel, UserRoleCreateRequest::new)
                   .map(ConvertUtils::toTestPlatformUserRole);
    }

    /**
     * 将 {@link Tuple4} 转换为 {@link VariableUpdateVo}
     *
     * @param tuple4 用户名 变量名 变量作用域 变量变更请求
     */
    public static VariableUpdateVo toVariableUpdateVo(Tuple4<String, String, TestPlatformVariableScope, VariableModifyRequest> tuple4) {
        return new VariableUpdateVo(tuple4.getT1(), tuple4.getT2(), tuple4.getT3(), tuple4.getT4());
    }

    /**
     * 将 {@link Tuple3} 转换为 {@link VariableDeleteVo}
     *
     * @param tuple3 用户名 变量名 变量作用域
     */
    public static VariableDeleteVo toVariableDeleteVo(Tuple3<String, String, TestPlatformVariableScope> tuple3) {
        return new VariableDeleteVo(tuple3.getT1(), tuple3.getT2(), tuple3.getT3());
    }

    /**
     * 将 {@link Byte} 转换为 {@link TestPlatformRoleLevel}
     *
     * @param roleLevel 角色级别
     */
    public static TestPlatformRoleLevel toTestPlatformRoleLevel(Byte roleLevel) {
        for (TestPlatformRoleLevel testPlatformRoleLevel : TestPlatformRoleLevel.values()) {
            if (testPlatformRoleLevel.getLevel() == roleLevel.intValue()) {
                return testPlatformRoleLevel;
            }
        }
        throw new ServerTestPlatformException("database role level invalid");
    }

    /**
     * 将 {@link Byte} 转换为 {@link TestPlatformVariableScope}
     *
     * @param variableScope 变量作用域
     */
    public static TestPlatformVariableScope toTestPlatformVariableScope(Byte variableScope) {
        for (TestPlatformVariableScope testPlatformVariableScope : TestPlatformVariableScope.values()) {
            if (testPlatformVariableScope.getScope() == variableScope.intValue()) {
                return testPlatformVariableScope;
            }
        }
        throw new ServerTestPlatformException("database variable scope invalid");
    }

    public static UserRoleQueryResponse toUserRoleQueryResponse(TestPlatformRoleLevel roleLevel) {
        return new UserRoleQueryResponse(roleLevel.name(), roleLevel);
    }
}
