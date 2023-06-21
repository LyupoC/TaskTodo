drop table if exists todolist.comment;
drop table if exists todolist.tag_task;
drop table if exists todolist.tag;
drop table if exists todolist.task;
drop table if exists todolist.list;

drop table if exists todolist.user_roles;
drop table if exists todolist.roles;
drop table if exists todolist.users;

CREATE TABLE todolist.users(
user_id INT GENERATED ALWAYS AS IDENTITY,
username VARCHAR(255) NOT NULL,
email VARCHAR(50),
profile_info VARCHAR(255), 
enabled smallint,
password VARCHAR(68) not null,
PRIMARY KEY(user_id)
);


CREATE TABLE todolist.list(
list_id INT GENERATED ALWAYS AS IDENTITY,
user_id INT,
title VARCHAR(50) NOT NULL,
description VARCHAR(255),
created_by INT,
updated_by INT,
status smallint,
created_date DATE,
updated_date DATE,
deadline_date DATE,
planned_start_date DATE,
PRIMARY KEY(list_id),
CONSTRAINT fk_user
  FOREIGN KEY(user_id) 
  REFERENCES todolist.users(user_id)
);


CREATE TABLE todolist.task(
task_id INT GENERATED ALWAYS AS IDENTITY,
user_id INT,
list_id INT,
title VARCHAR(60) NOT NULL,
description VARCHAR(255),
created_by INT,
updated_by INT,
status smallint,
priority smallint,
created_date DATE,
updated_date DATE,
deadline_date DATE,
planned_start_date DATE,
PRIMARY KEY(task_id),
CONSTRAINT fk_user
FOREIGN KEY(user_id) 
REFERENCES todolist.users(user_id),
CONSTRAINT fk_list
FOREIGN KEY(list_id) 
REFERENCES todolist.list(list_id) 
);

CREATE TABLE todolist.comment(
comment_id INT GENERATED ALWAYS AS IDENTITY,
user_id INT,
list_id INT,
task_id INT,
updated_by INT,
title VARCHAR(60) NOT NULL,
content VARCHAR(255),
created_date DATE,
updated_date DATE,
PRIMARY KEY(comment_id),
CONSTRAINT cc_user
FOREIGN KEY(user_id) 
REFERENCES todolist.users(user_id),
CONSTRAINT cc_task
FOREIGN KEY(task_id) 
REFERENCES todolist.task(task_id) ,
CONSTRAINT cc_list
FOREIGN KEY(list_id) 
REFERENCES todolist.list(list_id),
CONSTRAINT cc_user_updated
FOREIGN KEY(updated_by) 
REFERENCES todolist.users(user_id)
);


CREATE TABLE todolist.roles (
role_id int GENERATED ALWAYS AS IDENTITY,
rolename varchar(50) NOT NULL,
PRIMARY KEY (role_id)
);

CREATE TABLE todolist.user_roles (
user_id int ,
role_id int ,
PRIMARY KEY (user_id, role_id), 
constraint user_fkey 
foreign KEY(user_id) 
references todolist.users(user_id),
constraint role_fkey
foreign KEY(role_id)
references todolist.roles(role_id)

);


INSERT INTO todolist.users  (username, email, profile_info, enabled, password)
    VALUES ('susan', 's.mayer@gmail.com', 'info', 1, '$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q');
   
INSERT INTO todolist.users  (username, email, profile_info, enabled, password)
    VALUES ('john', 'john.smith@gmail.com', 'info', 1, '$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q');
   
INSERT INTO todolist.roles (rolename)
	VALUES ('USER');



insert into todolist.user_roles (user_id, role_id)
      select user_id, role_id 
        from todolist.users u 
        join todolist.roles r
          on ( u.username = 'susan' 
              and r.rolename = 'USER'
             );
            
insert into todolist.user_roles (user_id, role_id)
      select user_id, role_id 
        from todolist.users u 
        join todolist.roles r
          on ( u.username = 'john' 
              and r.rolename = 'USER'
             );
            
            