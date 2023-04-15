package org.huhu.test.platform.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试平台变量删除值对象单元测试
 *
 * @author 18551681083@163.com
 * @see GlobalVariableDeleteVo
 * @since 0.0.1
 */
class GlobalVariableDeleteVoTest {

    @Test
    void testGlobalVariableDeleteVo() {
        var vo = new GlobalVariableDeleteVo("root", "url");
        assertEquals("root", vo.username());
        assertEquals("url", vo.variableName());
    }

}