package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TestPlatformUserRepository extends R2dbcRepository<TestPlatformUser, Long> {}
