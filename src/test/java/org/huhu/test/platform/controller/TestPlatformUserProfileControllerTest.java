package org.huhu.test.platform.controller;

import org.huhu.test.platform.model.request.UserProfileModifyRequest;
import org.huhu.test.platform.model.response.ErrorResponse;
import org.huhu.test.platform.model.response.UserProfileQueryResponse;
import org.huhu.test.platform.model.vo.UserProfileModifyVo;
import org.huhu.test.platform.service.TestPlatformUserProfileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;

@WithMockUser
@WebFluxTest(TestPlatformUserProfileController.class)
class TestPlatformUserProfileControllerTest {

    @MockBean
    TestPlatformUserProfileService userProfileService;

    @Autowired
    WebTestClient webClient;

    @Test
    void queryUserProfile() {
        var response = new UserProfileQueryResponse("default", List.of("dev", "default"));
        doReturn(Mono.just(response))
                .when(userProfileService)
                .queryTestPlatformUserProfile(anyString());
        webClient.get()
                 .uri("/user/profile")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(UserProfileQueryResponse.class)
                 .value(UserProfileQueryResponse::active, equalTo("default"))
                 .value(UserProfileQueryResponse::candidates, hasItems("default", "dev"));
    }

    @Test
    void createUserProfile() {
        doReturn(Mono.empty())
                .when(userProfileService)
                .createTestPlatformUserProfile(any(UserProfileModifyVo.class));
        var request = new UserProfileModifyRequest("dev");
        webClient.mutateWith(csrf())
                 .put()
                 .uri("/user/profile")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @Test
    void createUserProfileError() {
        var request = new UserProfileModifyRequest("");
        webClient.mutateWith(csrf())
                 .put()
                 .uri("/user/profile")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

    @Test
    void activeUserProfile() {
        doReturn(Mono.empty())
                .when(userProfileService)
                .activeTestPlatformUserProfile(any(UserProfileModifyVo.class));
        var request = new UserProfileModifyRequest("dev");
        webClient.mutateWith(csrf())
                 .post()
                 .uri("/user/profile")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @Test
    void activeUserProfileError() {
        var request = new UserProfileModifyRequest("");
        webClient.mutateWith(csrf())
                 .post()
                 .uri("/user/profile")
                 .bodyValue(request)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

    @Test
    void deleteUserProfile() {
        doReturn(Mono.empty())
                .when(userProfileService)
                .deleteTestPlatformUserProfile(any(UserProfileModifyVo.class));
        webClient.mutateWith(csrf())
                 .delete()
                 .uri("/user/profile/{profileName}", "dev")
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"default", "@dev"})
    void deleteUserProfileError(String profileName) {
        webClient.mutateWith(csrf())
                 .delete()
                 .uri("/user/profile/{profileName}", profileName)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody(ErrorResponse.class)
                 .value(ErrorResponse::code, equalTo(1000))
                 .value(ErrorResponse::message, equalTo("client error"));
    }

}
