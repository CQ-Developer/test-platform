package org.huhu.test.platform.model.table;

import org.huhu.test.platform.constant.TestPlatformRoleName;
import org.huhu.test.platform.model.request.UserCreateRequest;
import org.huhu.test.platform.model.request.UserRoleModifyRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;

@Table("t_test_user_role")
public class TestPlatformUserRole {

    @Id
    @Column("role_id")
    private Long roleId;

    @Column("role_name")
    private TestPlatformRoleName roleName;

    private String username;

    public TestPlatformUserRole() {}

    public TestPlatformUserRole(TestPlatformRoleName roleName, String username) {
        this.roleName = roleName;
        this.username = username;
    }

    public static TestPlatformUserRole from(UserRoleModifyRequest request) {
        return new TestPlatformUserRole(request.roleName(), request.username());
    }

    public static Flux<TestPlatformUserRole> from(UserCreateRequest request) {
        var roles = request.roles();
        var roleName = Flux.fromIterable(roles);
        var username = Flux.just(request.username()).repeat(roles.size() - 1);
        return Flux.zip(roleName, username, TestPlatformUserRole::new);
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TestPlatformRoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(TestPlatformRoleName roleName) {
        this.roleName = roleName;
    }

}
