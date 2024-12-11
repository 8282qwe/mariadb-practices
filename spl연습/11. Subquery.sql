--
-- Subquery
--

--
-- 1) select 절, insert
--

select (select 1 + 1
        from dual)
from dual;
# insert into t1 values(null,(select max(no)+1 from t1));

--
-- 2) from 절의 서브쿼리
--

select a.n, a.r
from (select now() n, sysdate() s, 3 + 1 r
      from dual) a;

--
-- 2) where 절의 서브쿼리
--

-- 예제) 현재 Fai Bale이 근무하는 부서에서 근무하는 직원의 사번, 전체 이름을 출력해보세요,
select a.emp_no '사번', concat(a.first_name, ' ', a.last_name) '전체 이름'
from employees a
         join dept_emp b on a.emp_no = b.emp_no
where b.dept_no = (select b.dept_no
                   from employees a
                            join dept_emp b on a.emp_no = b.emp_no
                   where b.to_date = '9999-01-01'
                     and concat(a.first_name, ' ', a.last_name) = 'Fai Bale');

-- 3-1) 단일행 연산자 : =, >, <, >=, <=, <>, !=
-- 실습 문제 1
-- 현재, 전체 사원의 평균 연봉보다 적은 급여를 받는 사원의 이름과 급여를 출력하세요.
SELECT CONCAT(em.first_name, ' ', em.last_name) AS '이름', s.salary AS '급여'
FROM employees em
         JOIN salaries s ON em.emp_no = s.emp_no
WHERE s.to_date = '9999-01-01'
  AND s.salary < (SELECT AVG(salary)
                  FROM salaries
                  WHERE to_date = '9999-01-01');

-- 실습 문제 2
-- 현재, 직책별 평균급여 중에 가장 적은 평균 급여의 직책이름과 그 평균급여를 출력하세요.

# 최소값 구하기
select min(avg)
from (select avg(sa.salary) avg
      from titles ti
               join salaries sa on ti.emp_no = sa.emp_no
      where ti.to_date = '9999-01-01'
        and sa.to_date = '9999-01-01'
      group by ti.title) a;

# Subquery만 쓰는 버전
select ti.title '직책이름', avg(sa.salary) '평균급여'
from titles ti
         join salaries sa on ti.emp_no = sa.emp_no
where ti.to_date = '9999-01-01'
  and sa.to_date = '9999-01-01'
group by ti.title
having 평균급여 = (select min(avg)
               from (select avg(sa.salary) avg
                     from titles ti
                              join salaries sa on ti.emp_no = sa.emp_no
                     where ti.to_date = '9999-01-01'
                       and sa.to_date = '9999-01-01'
                     group by ti.title) a);

# 가장 쉬운 방법 : tok-k
select ti.title '직책이름', avg(sa.salary) '평균급여'
from titles ti
         join salaries sa on ti.emp_no = sa.emp_no
where ti.to_date = '9999-01-01'
  and sa.to_date = '9999-01-01'
group by ti.title
order by 평균급여 asc
limit 1;

-- 3-2) 복수행 연산자 : in, not in, 비교연산자any, 비교연산자all
-- any 사용법
-- 1. =any : in
-- 2. >and, >=any : 최소값
-- 3. <=any, <=any : 최대값
-- 4. <>any, !=any : not in

-- all 사용법
-- 1. =all : (x)
-- 2. >all, >=all : 최댓값
-- 3. <all, <=all : 최솟값
-- 4. <>all, !=all

-- 실습 문제 3
-- 현재 급여가 50000 이상인 직원의 이름과 급여를 출력하세요.
select a.first_name, b.salary
from employees a,
     salaries b
where a.emp_no = b.emp_no
  and b.to_date = '9999-01-01'
  and b.salary > 50000;

-- 실습 문제 4
-- 현재, 각 부서별 최고급여를 받고 있는 직원의 이름, 부서이름, 급여를 출력해보세요.
select concat(a.first_name, ' ', a.last_name) '이름', b.dept_name '부서이름', c.salary '급여'
from employees a,
     departments b,
     salaries c,
     dept_emp d
where a.emp_no = d.emp_no
  and c.emp_no = a.emp_no
  and d.dept_no = b.dept_no
  and c.to_date = '9999-01-01'
   and d.to_date = '9999-01-01'
  and (d.dept_no, c.salary) in (select d.dept_no, max(c.salary)
                                from salaries c,
                                     dept_emp d
                                where c.emp_no = d.emp_no
                                  and c.to_date = '9999-01-01'
                                  and d.to_date = '9999-01-01'
                                group by d.dept_no);

-- sol2) subquery + join
SELECT
    CONCAT(e.first_name, ' ', e.last_name) AS '이름',
    d.dept_name AS '부서이름',
    s.salary AS '급여'
FROM
    employees e
    JOIN dept_emp de ON e.emp_no = de.emp_no
    JOIN departments d ON de.dept_no = d.dept_no
    JOIN salaries s ON e.emp_no = s.emp_no
    JOIN (
        SELECT
            de.dept_no,
            MAX(s.salary) AS max_salary
        FROM
            dept_emp de
            JOIN salaries s ON de.emp_no = s.emp_no
        GROUP BY
            de.dept_no
    ) max_salaries ON de.dept_no = max_salaries.dept_no AND s.salary = max_salaries.max_salary
WHERE
    de.to_date = '9999-01-01'
    AND s.to_date = '9999-01-01';




