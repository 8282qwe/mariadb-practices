--
-- DDL/DML 연습
--

create table member
(
    id         int          not null auto_increment,
    email      varchar(200) not null,
    password   varchar(64)  not null,
    name       varchar(50)  not null,
    department varchar(100),
    primary key (id)
);

desc member;

alter table member
    add column juminbunho char(13) not null;
desc member;

alter table member
    drop juminbunho;
desc member;

alter table member
    add column juminbunho char(13) not null after email;
desc member;

alter table member
    change column department dept varchar(100) not null;
desc member;

alter table member
    add column profile text;
desc member;

alter table member
    drop juminbunho;
desc member;

-- insert
insert into member
values (null, '8282qwe@gmail.com', password('1234'), '이장우', '개발팀', null);

select *
from member;

insert into member (id, email, name, password, dept)
values (null, '8282qwe@gmail.com', '이장우2', password('1234'), '개발팀');

select *
from member;

-- update
update member
set email    = '8282qwe2@gmail.com',
    password = password('12345')
where id = 2;

-- delete
delete
from member
where id = 2;

-- transaction(tx)
select id, email
from member;

select @@autocommit;
insert into member
values (null, '8282qwe3@gmail.com', '이장우', password('123'), '개발팀2', null);
select id, email
from member;

-- tx:begin
set autocommit = 0;
select @@autocommit;
insert into member
values (null, '8282qwe3@gmail.com', '이장우', password('123'), '개발팀3', null);
select id, email
from member;

-- tx:end
commit;
# rollback;

select id, email
from member;
