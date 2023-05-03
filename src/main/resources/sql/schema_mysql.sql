drop table if exists t_test_user;
create table if not exists t_test_user (
  user_id bigint not null auto_increment,
  username varchar(16) not null,
  password varchar(128) not null,
  enabled boolean not null default 1,
  locked boolean not null default 0,
  password_time datetime not null default now(),
  register_time datetime not null default now(),
  expired_time datetime not null default now(),
  primary key (user_id),
  unique key (username)
);

drop table if exists t_test_user_role;
create table if not exists t_test_user_role (
  role_id bigint not null auto_increment,
  role_level tinyint not null default 1,
  username varchar(16) not null,
  primary key (role_id),
  unique key (username, role_level)
);

drop table if exists t_test_user_profile;
create table if not exists t_test_user_profile (
  profile_id bigint not null auto_increment,
  profile_name varchar(32) not null default 'default',
  username varchar(16) not null,
  primary key (profile_id),
  unique key (username, profile_name)
);

drop table if exists t_test_variable;
create table if not exists t_test_variable (
  variable_id bigint not null auto_increment,
  variable_name varchar(32) not null,
  variable_value varchar(256) not null,
  variable_scope tinyint not null,
  variable_profile varchar(16) not null default 'default',
  variable_description varchar(512),
  username varchar(16) not null,
  primary key (variable_id),
  unique key (username, variable_profile, variable_name, variable_scope)
);

drop table if exists t_test_case;
create table if not exists t_test_case (
  case_id bigint not null auto_increment,
  case_name varchar(256) not null,
  case_method tinyint not null,
  case_uri varchar(512) not null,
  username varchar(16) not null,
  primary key (case_id),
  unique key (username, case_name)
);

drop table if exists t_test_case_auth;
create table if not exists t_test_case_auth (
  auth_id bigint not null auto_increment,
  auth_type tinyint not null,
  auth_content varchar(512) not null,
  case_name varchar(256) not null,
  username varchar(16) not null,
  primary key (auth_id),
  unique key (username, auth_type)
);
