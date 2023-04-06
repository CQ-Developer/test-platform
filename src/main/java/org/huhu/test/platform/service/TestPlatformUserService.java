package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.table.TestPlatformUser;
import reactor.core.publisher.Mono;

public interface TestPlatformUserService {

    Mono<TestPlatformUser> queryTestPlatformUser(String username);

    Mono<Void> saveTestPlatformUser(AddTestPlatformUserRequest request);

}
