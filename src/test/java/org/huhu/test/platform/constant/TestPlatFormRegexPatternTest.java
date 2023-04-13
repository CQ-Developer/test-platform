package org.huhu.test.platform.constant;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.PASSWORD;
import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.USERNAME;
import static org.huhu.test.platform.constant.TestPlatFormRegexPattern.VARIABLE_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 测试平台正则表达式单元测试
 *
 * @author 18551681083@163.com
 * @see org.huhu.test.platform.constant.TestPlatFormRegexPattern
 * @since 0.0.1
 */
class TestPlatFormRegexPatternTest {

    final Pattern username = Pattern.compile(USERNAME);

    final Pattern password = Pattern.compile(PASSWORD);

    final Pattern variableName = Pattern.compile(VARIABLE_NAME);

    @Test
    void testUsername() {
        var matcher = username.matcher("Jackie_Chen");
        assertTrue(matcher.matches());
    }

    @Test
    void testUsernameInvalidChar() {
        var matcher = username.matcher("Jackie-Chen");
        assertFalse(matcher.matches());
    }

    @Test
    void testUsernameShort() {
        var matcher = username.matcher("Tom");
        assertFalse(matcher.matches());
    }

    @Test
    void testUsernameLong() {
        var matcher = username.matcher("Winston_Leonard_Spencer_Churchill");
        assertFalse(matcher.matches());
    }

    @Test
    void testPassword() {
        var matcher = password.matcher("jack:A@2#3_c-P");
        assertTrue(matcher.matches());
    }

    @Test
    void testPasswordInvalidChar() {
        var matcher = password.matcher("jack:A^2#3_c-P");
        assertFalse(matcher.matches());
    }

    @Test
    void testPasswordShort() {
        var matcher = password.matcher("jack");
        assertFalse(matcher.matches());
    }

    @Test
    void testPasswordLong() {
        var matcher = password.matcher("abcABC@abcABC#abcABC_abcABC:abcABC-abcABC");
        assertFalse(matcher.matches());
    }

    @Test
    void testVariableName() {
        var matcher = variableName.matcher("base_url");
        assertTrue(matcher.matches());
    }

    @Test
    void testVariableNameInvalidChar() {
        var matcher = variableName.matcher("base-url");
        assertFalse(matcher.matches());
    }

    @Test
    void testVariableNameShort() {
        var matcher = variableName.matcher("");
        assertFalse(matcher.matches());
    }

    @Test
    void testVariableNameLong() {
        var matcher = variableName.matcher("base_url_base_url_base_url_base_url");
        assertFalse(matcher.matches());
    }

}
