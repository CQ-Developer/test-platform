-- 创建默认用户
insert into t_test_user (username, password, password_time, expired_time)
values ('jack', '123456', '2023-04-28 14:00:00', '2099-04-28 14:00:00'),
       ('rose', '123456', '2023-04-28 14:00:00', '2099-04-28 14:00:00');

-- 创建默认角色
insert into t_test_user_role (username, role_level)
values ('root', 3),
       ('chen', 2);

-- 创建默认环境
insert into t_test_user_profile (profile_name, username)
values ('default', 'root'),
       ('default', 'chen');

-- 创建测试数据
insert into t_test_variable (username, variable_name, variable_value, variable_scope, variable_profile)
values ('root', 'url', 'https://some.path', 4, 'default'),
       ('chen', 'application-id', 'test-platform', 4, 'default');
