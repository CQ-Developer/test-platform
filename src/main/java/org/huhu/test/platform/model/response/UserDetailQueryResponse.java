package org.huhu.test.platform.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.huhu.test.platform.model.table.TestPlatformUser;

import java.time.LocalDateTime;
import java.util.List;

public record UserDetailQueryResponse(
        String username, List<String> userRoles,
        Boolean enabled, Boolean locked,
        @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss") LocalDateTime registerTime,
        @JsonFormat(pattern = "yyyy-MM-hh HH:mm:ss") LocalDateTime expiredTime) {

    public static UserDetailQueryResponse build(TestPlatformUser user, List<String> userRoles) {
        return new UserDetailQueryResponse(user.getUsername(), userRoles, user.getEnabled(),
                user.getLocked(), user.getRegisterTime(), user.getExpiredTime());
    }

}
