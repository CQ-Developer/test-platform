package org.huhu.test.platform.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformRole;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class AddTestPlatformUserRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]{4,16}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]\\w{5,17}$")
    private String password;

    @NotNull
    @Size(min = 1, max = 3)
    private List<TestPlatformRole> roles;

    private Boolean enabled;

    private Boolean locked;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TestPlatformRole> getRoles() {
        return roles;
    }

    public void setRoles(List<TestPlatformRole> roles) {
        this.roles = roles;
    }

    public Optional<Boolean> getEnabled() {
        return Optional.ofNullable(enabled);
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Optional<Boolean> getLocked() {
        return Optional.ofNullable(locked);
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Optional<LocalDateTime> getExpiredTime() {
        return Optional.ofNullable(expiredTime);
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

}
