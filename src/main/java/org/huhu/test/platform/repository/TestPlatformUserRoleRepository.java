package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformUserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TestPlatformUserRoleRepository extends R2dbcRepository<TestPlatformUserRole, Long> {}
