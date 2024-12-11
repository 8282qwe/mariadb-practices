--
-- join
--

-- 예제: 이름이 'parto hitomi'인 직원의 현재 직책을 구하세요.
select *
from employees
where concat(first_name,' ',last_name) = 'parto hitomi';
-- 11052
select title
from titles
where emp_no = 11052;

select e.emp_no, e.first_name, e.last_name, t.title
from employees e,
     titles t
where e.emp_no = t.emp_no -- join condition
  and concat(e.first_name, ' ', e.last_name) = 'parto hitomi'; -- row selector
