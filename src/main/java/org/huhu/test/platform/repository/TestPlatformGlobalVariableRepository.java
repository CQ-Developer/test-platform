package org.huhu.test.platform.repository;

import org.huhu.test.platform.model.table.TestPlatformGlobalVariable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TestPlatformGlobalVariableRepository extends R2dbcRepository<TestPlatformGlobalVariable, Long> {}
