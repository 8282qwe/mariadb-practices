--
-- inner join
--

-- 예제1) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하세요.
select concat(a.first_name, ' ', a.last_name) '이름', b.title '직책'
from employees a,
     titles b
where a.emp_no = b.emp_no
  and b.to_date = '9999-01-01';

-- 예제2) 현재, 근무하고 있는 직원의 이름과 직책을 모두 출력하되 여성 엔지니어(Engineer)만 출력하세요.
select concat(a.first_name, ' ', a.last_name) '이름', b.title '직책'
from employees a,
     titles b
where b.title like '%Engineer%'
  and a.gender = 'f'
  and b.to_date = '9999-01-01'
  and a.emp_no = b.emp_no;

--
-- ANSI / ISO SQL1999 JOIN 표준 문법
--

-- 1) natural join
--    조인 대상이 되는 두 테이블에 이름이 같은 공통 컬럼이 있는 경우 사용할 수 있다.
--    조인 조건을 명시하지 않고 암묵적으로 조인이 된다.
select concat(a.first_name, ' ', a.last_name) '이름', b.title '직책'
from employees a
         natural join titles b
where b.to_date = '9999-01-01';

-- 2) join ~ using
-- natural join의 문제점
select count(*)
from salaries a
         natural join titles b
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01';

select count(*)
from salaries a
         join titles b using (emp_no) -- 키를 명시적으로 사용
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01';

-- 3) join ~ on
select count(*)
from salaries a
         join titles b on a.emp_no = b.emp_no -- 키를 명시적으로 사용
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01';

-- 실습 문제1
-- 현재, 직책별 평균 연봉을 큰 순서대로 출력하세요.
select a.title, avg(b.salary) 'avg'
from titles a
         join salaries b on a.emp_no = b.emp_no
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01'
group by a.title
order by avg desc ;

-- 실습 문제2
-- 현재, 직책별 평균 연봉과 직원 수를 구하되 직원수가 100명 이상인 직책만 출력하세요
select a.title '직책', avg(b.salary) '평균연봉', count(*) '인원수'
from titles a
         join salaries b on a.emp_no = b.emp_no
where a.to_date = '9999-01-01'
  and b.to_date = '9999-01-01'
group by a.title
having count(*) >= 100;

-- 실습 문제3
-- 현재, 부서별로 직책이 Enginner인 직원들에 대한 평균 연봉을 구하시오
-- projection : 부서 이름, 부서별 평균 연봉
SELECT dep.dept_name AS '부서 이름', AVG(sa.salary) AS '평균 연봉'
FROM dept_emp de
         JOIN departments dep ON de.dept_no = dep.dept_no
         JOIN salaries sa ON de.emp_no = sa.emp_no
         JOIN titles ti ON de.emp_no = ti.emp_no
WHERE ti.title = 'Engineer'
  and ti.to_date = '9999-01-01'
  and sa.to_date = '9999-01-01'
GROUP BY dep.dept_name;