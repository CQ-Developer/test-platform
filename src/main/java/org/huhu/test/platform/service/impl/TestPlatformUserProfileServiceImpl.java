package org.huhu.test.platform.service.impl;

import org.huhu.test.platform.exception.ClientTestPlatformException;
import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import org.huhu.test.platform.model.table.TestPlatformUserProfile;
import org.huhu.test.platform.model.vo.UserProfileModifyVo;
import org.huhu.test.platform.repository.TestPlatformUserProfileRepository;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.huhu.test.platform.util.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.huhu.test.platform.constant.TestPlatformDefaultName.DEFAULT_PROFILE_NAME;
import static org.huhu.test.platform.constant.TestPlatformRedisKey.USER_PROFILE_ACTIVE;

@Service
public class TestPlatformUserProfileServiceImpl implements TestPlatformUserProfileService {

    private final Logger logger = LoggerFactory.getLogger(TestPlatformUserProfileServiceImpl.class);

    private final TestPlatformUserProfileRepository userProfileRepository;

    private final ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate;

    TestPlatformUserProfileServiceImpl(TestPlatformUserProfileRepository userProfileRepository,
            ReactiveRedisTemplate<Object, Object> reactiveRedisTemplate) {
        this.userProfileRepository = userProfileRepository;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Override
    public Mono<Void> activeTestPlatformUserProfile(UserProfileModifyVo vo) {
        return userProfileRepository
                .findByUsernameAndProfileName(vo.username(), vo.profileName())
                .switchIfEmpty(Mono.error(new ClientTestPlatformException("active profile error: profile not exists")))
                .flatMap(profile -> reactiveRedisTemplate
                        .opsForValue()
                        .set(USER_PROFILE_ACTIVE.getKey(vo.username()), profile, Duration.ofHours(24L)))
                .then();
    }

    @Override
    public Mono<UserProfileQueryResponse> queryTestPlatformUserProfile(String username) {
        var findActiveProfile = queryTestPlatformUserActiveProfile(username);
        var findAllProfiles = userProfileRepository
                .findByUsername(username)
                .map(TestPlatformUserProfile::profileName)
                .collectList();
        return Mono.zip(findActiveProfile, findAllProfiles, UserProfileQueryResponse::new);
    }

    @Override
    public Mono<String> queryTestPlatformUserActiveProfile(String username) {
        return reactiveRedisTemplate
                .opsForValue()
                .get(USER_PROFILE_ACTIVE.getKey(username))
                .switchIfEmpty(reactiveRedisTemplate
                        .opsForValue()
                        .set(USER_PROFILE_ACTIVE.getKey(username), DEFAULT_PROFILE_NAME, Duration.ofHours(24L))
                        .thenReturn(DEFAULT_PROFILE_NAME))
                .cast(String.class);
    }

    @Override
    public Mono<Void> createTestPlatformUserProfile(UserProfileModifyVo vo) {
        var saveProfile = userProfileRepository
                .save(ConvertUtils.toTestPlatformUserProfile(vo))
                .doOnNext(i -> logger.info("save profile {}", i.profileName()));
        return userProfileRepository
                .findByUsernameAndProfileName(vo.username(), vo.profileName())
                .doOnNext(i -> logger.info("profile {} exists", i.profileName()))
                .flatMap(i -> Mono.error(new ClientTestPlatformException("user profile exists")))
                .switchIfEmpty(saveProfile)
                .then();
    }

    @Override
    public Mono<Void> deleteTestPlatformUserProfile(UserProfileModifyVo vo) {
        return userProfileRepository
                .deleteByUsernameAndProfileName(vo.username(), vo.profileName())
                .doOnNext(i -> logger.info("delete {} profile", i))
                .then();
    }

}
