package org.huhu.test.platform.service;

import org.huhu.test.platform.model.request.AddTestPlatformUserRequest;
import org.huhu.test.platform.model.response.QueryTestPlatformUserResponse;
import org.huhu.test.platform.model.response.QueryTestPlatformUsersResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TestPlatformUserService {

    Mono<QueryTestPlatformUserResponse> queryTestPlatformUser(String username);

    Flux<QueryTestPlatformUsersResponse> queryTestPlatformUsers();

    Mono<Void> saveTestPlatformUser(AddTestPlatformUserRequest request);

}
