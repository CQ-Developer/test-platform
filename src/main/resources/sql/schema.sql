create table t_test_user (
  user_id bigint not null auto_increment,
  username varchar(16) not null,
  password varchar(128) not null,
  enabled boolean default 1,
  locked boolean default 0,
  register_time datetime default current_timestamp,
  expired_time datetime,
  primary key (user_id),
  unique key (username)
);

create table t_test_user_role (
  role_id bigint not null auto_increment,
  username varchar(16) not null,
  user_role varchar(8) not null,
  primary key (role_id),
  unique key (username)
);

create table t_test_global_variable (
  variable_id bigint not null auto_increment,
  username bigint not null,
  variable_name varchar(32) not null,
  variable_value varchar(256) not null,
  variable_description varchar(512),
  primary key (variable_id),
  unique key (username, variable_name)
);
