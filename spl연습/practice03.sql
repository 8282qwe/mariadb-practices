-- 테이블 조인(JOIN) SQL 문제입니다.

-- 문제 1. 
-- 현재 급여가 많은 직원부터 직원의 사번, 이름, 그리고 연봉을 출력 하시오.
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', b.sal '연봉'
from employees em
         join (select emp_no, avg(salary) sal from salaries where to_date = '9999-01-01' group by emp_no) b
              on em.emp_no = b.emp_no;

-- 문제2.
-- 전체 사원의 사번, 이름, 현재 직책을 이름 순서로 출력하세요.
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', ti.title 현재직책
from employees em
         join titles ti on em.emp_no = ti.emp_no
where ti.to_date = '9999-01-01';

-- 문제3.
-- 전체 사원의 사번, 이름, 현재 부서를 이름 순서로 출력하세요.
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', dep.dept_name '현재부서'
from employees em
         join dept_emp de on em.emp_no = de.emp_no and to_date = '9999-01-01'
join departments dep on de.dept_no = dep.dept_no;

-- 문제4.
-- 현재 근무중인 전체 사원의 사번, 이름, 연봉, 직책, 부서를 모두 이름 순서로 출력합니다.
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', ti.title 현재직책, dep.dept_name '현재부서'
from employees em
         join dept_emp de on em.emp_no = de.emp_no and to_date = '9999-01-01'
         join departments dep on de.dept_no = dep.dept_no
         join titles ti on em.emp_no = ti.emp_no and de.to_date = '9999-01-01'
order by 이름;

-- 문제5.
-- 'Technique Leader'의 직책으로 과거에 근무한 적이 있는 모든 사원의 사번과 이름을 출력하세요.
-- (현재 'Technique Leader'의 직책으로 근무하는 사원은 고려하지 않습니다.)
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름'
from employees em
         join titles ti on em.emp_no = ti.emp_no and ti.to_date != '9999-01-01' and ti.title = 'Technique Leader';


-- 문제6.
-- 직원 이름(last_name) 중에서 S로 시작하는 직원들의 이름, 부서명, 직책을 조회하세요.
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', ti.title 현재직책, dep.dept_name '현재부서'
from employees em
         join dept_emp de on em.emp_no = de.emp_no and to_date = '9999-01-01'
         join departments dep on de.dept_no = dep.dept_no and to_date = '9999-01-01'
         join titles ti on em.emp_no = ti.emp_no and ti.to_date = '9999-01-01'
where em.last_name like 'S%';

-- 문제7.
-- 현재, 직책이 Engineer인 사원 중에서 현재 급여가 40,000 이상인 사원들의 사번, 이름, 급여 그리고 타이틀을 급여가 큰 순서대로 출력하세요.
select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', sa.salary '급여'
from employees em
         join titles ti on em.emp_no = ti.emp_no and ti.title = 'Engineer'
         join salaries sa on em.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
order by sa.salary desc ;

-- 문제8.
-- 현재, 평균급여가 50,000이 넘는 직책을 직책과 평균급여을 평균급여가 큰 순서대로 출력하세요.
select ti.title '직책',avg(sa.salary) '평균급여'
from titles ti join salaries sa on ti.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
group by ti.title
having 평균급여 > 50000
order by 평균급여 desc ;

-- 문제9.
-- 현재, 부서별 평균급여를 평균급여가 큰 순서대로 부서명과 평균연봉을 출력 하세요.
select dep.dept_name '부서명', avg(sa.salary) '평균연봉'
from dept_emp de
         join salaries sa on de.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
         join departments dep on de.dept_no = dep.dept_no
group by dep.dept_name
order by 평균연봉 desc;


-- 문제10.
-- 현재, 직책별 평균급여를 평균급여가 큰 직책 순서대로 직책명과 그 평균연봉을 출력 하세요.
select ti.title '직책',avg(sa.salary) '평균급여'
from titles ti join salaries sa on ti.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
group by ti.title
order by 평균급여 desc ;
