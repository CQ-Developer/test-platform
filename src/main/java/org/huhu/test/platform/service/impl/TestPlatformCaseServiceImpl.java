package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.response.CaseQueryResponse;
import org.huhu.test.platform.repository.TestPlatformCaseRepository;
import org.huhu.test.platform.service.TestPlatformCaseService;
import org.huhu.test.platform.util.ConvertUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TestPlatformCaseServiceImpl implements TestPlatformCaseService {

    private final TestPlatformCaseRepository caseRepository;

    TestPlatformCaseServiceImpl(TestPlatformCaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    @Override
    public Flux<CaseQueryResponse> queryTestPlatformCase(String username) {
        return caseRepository
                .findByUsername(username)
                .map(ConvertUtils::toCaseQueryResponse);
    }

}
