-- 创建默认用户
insert into t_test_user (username, password, expired_time)
values ('root', '$2a$18$a8ocNRkAeuSR001XNxyNuusND.ckrQNUHBS916GDkvrmLfdO78SbO', date_add(now(), interval 1 year));

-- 创建默认角色
insert into t_test_user_role (username, user_role)
values ('root', 'ADMIN');

-- 创建测试数据
insert into t_test_global_variable (username, variable_name, variable_value)
values ('root', 'test1', 'value1'), ('root', 'test2', 'value2');
