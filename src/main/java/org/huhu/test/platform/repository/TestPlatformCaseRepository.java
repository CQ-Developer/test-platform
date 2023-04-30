package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformCase;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface TestPlatformCaseRepository extends R2dbcRepository<TestPlatformCase, Long> {

    Flux<TestPlatformCase> findByUsername(String username);

}
