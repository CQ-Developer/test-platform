-- 创建默认用户
insert into t_test_user (username, password, expired_time)
values ('root', '$2a$08$LFJI.wwH0y0yEf9ADfTbXue8z3UftLw0JHaZ21VuJYUZ7gNJmgDtq', '2099-01-01 00:00:00'),
       ('chen', '$2a$08$BqZuaYRt.RySS02dkvt4veA8lpTMQI8BnihtvHLjAW8il/e9UsEKa', '2099-01-01 00:00:00');

-- 创建默认角色
insert into t_test_user_role (username, role_name)
values ('root', 'ADMIN'),
       ('chen', 'DEV');

-- 创建测试数据
insert into t_test_global_variable (username, variable_name, variable_value)
values ('root', 'test1', 'value1'),
       ('root', 'test2', 'value2');
