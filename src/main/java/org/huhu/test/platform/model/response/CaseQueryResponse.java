package org.huhu.test.platform.model.response;

import org.huhu.test.platform.constant.TestPlatformCaseMethod;

/**
 * 测试平台测试用例查询响应
 *
 * @param caseName 用例名称
 * @param caseMethod 用例请求方法
 * @param caseUri 用例请求地址
 *
 * @author 18551681083@163.com
 * @since 0.0.2
 */
public record CaseQueryResponse(
        String caseName,
        TestPlatformCaseMethod caseMethod,
        String caseUri) {}
