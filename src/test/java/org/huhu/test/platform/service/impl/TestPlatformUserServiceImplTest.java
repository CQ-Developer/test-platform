package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.repository.TestPlatformUserRepository;
import org.huhu.test.platform.repository.TestPlatformUserRoleRepository;
import org.huhu.test.platform.service.TestPlatformUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TestPlatformUserServiceImplTest {

    @MockBean
    TestPlatformUserRepository userRepository;

    @MockBean
    TestPlatformUserRoleRepository userRoleRepository;

    @Autowired
    TestPlatformUserService userService;

    @Test
    void queryTestPlatformUser() {
    }

    @Test
    void queryTestPlatformUsers() {
    }

    @Test
    void createTestPlatformUser() {
    }

    @Test
    void deleteTestPlatformUser() {
    }

}
