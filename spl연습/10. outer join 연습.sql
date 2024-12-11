insert into department value (null, '총무');
insert into department value (null, '개발');
insert into department value (null, '영업');
insert into department value (null, '기획');

select *
from department;

insert into employee values(null, '둘리',1);
insert into employee values(null, '마이콜',2);
insert into employee values(null, '또치',3);
insert into employee values(null, '길동',null);

select *
from employee;

-- inner join
select a.name '이름', b.name '부서'
from employee a
         join department b on a.department_id = b.id;

-- left (outer) join
select a.name '이름', ifnull(b.name,'없음') '부서'
from employee a
         left outer join department b on a.department_id = b.id;

-- right (outer) join
select ifnull(a.name,'없음') '이름', b.name '부서'
from employee a
         right outer join department b on a.department_id = b.id;

-- full (outer) join
-- mariaDB는 지원하지 않음