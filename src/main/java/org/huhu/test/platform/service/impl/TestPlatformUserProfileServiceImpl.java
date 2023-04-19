package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.huhu.test.platform.constant.TestPlatformRedisKey.USER_PROFILE_ACTIVE;

@Service
public class TestPlatformUserProfileServiceImpl implements TestPlatformUserProfileService {

    private final TestPlatformUserProfileRepository userProfileRepository;

    private final ReactiveRedisTemplate<Object, Object> redisTemplate;

    public TestPlatformUserProfileServiceImpl(TestPlatformUserProfileRepository userProfileRepository,
            ReactiveRedisTemplate<Object, Object> redisTemplate) {
        this.userProfileRepository = userProfileRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<UserProfileQueryResponse> queryTestPlatformUserProfile(String username) {
        var findActiveProfile = redisTemplate
                .opsForValue()
                .get(USER_PROFILE_ACTIVE.getKey(username))
                .switchIfEmpty(Mono.just("default"))
                .cast(String.class);
        var findProfiles = userProfileRepository
                .findByUsername(username)
                .map(TestPlatformUserProfile::profileName)
                .collectList();
        return Mono.zip(findActiveProfile, findProfiles, UserProfileQueryResponse::new);
    }

}
