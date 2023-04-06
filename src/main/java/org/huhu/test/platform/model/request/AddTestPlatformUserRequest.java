package org.huhu.test.platform.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.huhu.test.platform.constant.TestPlatformRole;

import java.util.List;

public class AddTestPlatformUserRequest {

    @NotBlank
    @Size(min = 4, max = 16)
//    @Pattern(regexp = "")
    private String username;

    @NotBlank
//    @Pattern(regexp = "")
    private String password;

    @NotNull
    @Size(min = 1, max = 3)
    private List<TestPlatformRole> roles;

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

}
