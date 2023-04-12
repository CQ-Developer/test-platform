drop table if exists t_test_user;
create table if not exists t_test_user (
  user_id bigint not null auto_increment,
  username varchar(16) not null,
  password varchar(128) not null,
  enabled boolean default 1,
  locked boolean default 0,
  register_time datetime default now(),
  expired_time datetime default now(),
  primary key (user_id),
  unique key (username)
);

drop table if exists t_test_user_role;
create table if not exists t_test_user_role (
  role_id bigint not null auto_increment,
  role_name varchar(8) not null,
  username varchar(16) not null,
  primary key (role_id),
  unique key (username, role_name)
);

drop table if exists t_test_global_variable;
create table if not exists t_test_global_variable (
  variable_id bigint not null auto_increment,
  variable_name varchar(32) not null,
  variable_value varchar(256) not null,
  variable_description varchar(512),
  username varchar(16) not null,
  primary key (variable_id),
  unique key (username, variable_name)
);
