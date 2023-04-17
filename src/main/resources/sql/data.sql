-- 创建默认用户
insert into t_test_user (username, password, expired_time)
values ('root', '$2a$08$LFJI.wwH0y0yEf9ADfTbXue8z3UftLw0JHaZ21VuJYUZ7gNJmgDtq', date_add(now(), interval 1 year)),
       ('chen', '$2a$08$BqZuaYRt.RySS02dkvt4veA8lpTMQI8BnihtvHLjAW8il/e9UsEKa', date_add(now(), interval 1 year));

-- 创建默认角色
insert into t_test_user_role (username, role_level)
values ('root', 3),
       ('chen', 2);

-- 创建测试数据
insert into t_test_variable (username, variable_name, variable_value, variable_scope)
values ('root', 'test1', 'value1', 0),
       ('root', 'test2', 'value2', 0);
