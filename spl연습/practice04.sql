-- 서브쿼리(SUBQUERY) SQL 문제입니다.

-- 단 조회결과는 급여의 내림차순으로 정렬되어 나타나야 합니다. 

-- 문제1.
-- 현재 전체 사원의 평균 급여보다 많은 급여를 받는 사원은 몇 명이나 있습니까?
select count(*)
from employees em join salaries sa on em.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
where sa.salary > (select avg(salary)
from salaries where to_date='9999-01-01');

-- 문제2. 
-- 현재, 각 부서별로 최고의 급여를 받는 사원의 사번, 이름, 부서 급여을 조회하세요. 단 조회결과는 급여의 내림차순으로 정렬합니다.
# select de.dept_no,max(sa.salary)
# from dept_emp de join salaries sa on de.emp_no = sa.salary and sa.to_date = '9999-01-01'
# group by de.dept_no;

select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', sa.salary '부서급여'
from employees em
         join dept_emp de on em.emp_no = de.emp_no
         join salaries sa on em.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
where (de.dept_no,sa.salary) in (select de.dept_no,max(sa.salary)
from dept_emp de join salaries sa on de.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
group by de.dept_no)
order by sa.salary desc ;


-- 문제3.
-- 현재, 사원 자신들의 부서의 평균급여보다 급여가 많은 사원들의 사번, 이름 그리고 급여를 조회하세요 
# select de.dept_no, avg(sa.salary)
# from dept_emp de
#          join salaries sa on de.emp_no = sa.salary and sa.to_date = '9999-01-01'
# group by de.dept_no;

select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', sa.salary '급여'
from employees em
         join dept_emp de on em.emp_no = de.emp_no
         join salaries sa on em.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
join (select de.dept_no, avg(sa.salary) '평균급여'
from dept_emp de
         join salaries sa on de.emp_no = sa.salary and sa.to_date = '9999-01-01'
group by de.dept_no) avg_sal on de.dept_no = avg_sal.dept_no
where sa.salary > avg_sal.평균급여 ;

-- 문제4.
-- 현재, 사원들의 사번, 이름, 그리고 매니저 이름과 부서 이름을 출력해 보세요.
select dep.emp_no '사번', dep.name '이름' , dem.name '매니저이름'
from (select em.emp_no emp_no, concat(em.first_name,' ',em.last_name) name, de.dept_no dept_no
      from employees em
               join dept_emp de on em.emp_no = de.emp_no and de.to_date = '9999-01-01') dep
         join (select concat(em.first_name,' ',em.last_name) name, dm.dept_no dept_no
               from employees em
                        join dept_manager dm on em.emp_no = dm.emp_no and dm.to_date = '9999-01-01'
) dem on dep.dept_no = dem.dept_no;

-- 문제5.
-- 현재, 평균급여가 가장 높은 부서의 사원들의 사번, 이름, 직책 그리고 급여를 조회하고 급여 순으로 출력하세요.
# select de.dept_no
# from dept_emp de
#          join salaries sa on de.emp_no = sa.emp_no
# where de.to_date = '9999-01-01'
#   and sa.to_date = '9999-01-01'
# group by de.dept_no
# order by avg(sa.salary) desc
# limit 1;

select em.emp_no '사번', concat(em.first_name, ' ', em.last_name) '이름', ti.title '직책', sa.salary '급여'
from employees em
         join dept_emp de on em.emp_no = de.emp_no and de.to_date = '9999-01-01'
join titles ti on em.emp_no = ti.emp_no and ti.to_date = '9999-01-01'
join salaries sa on em.emp_no = sa.emp_no and sa.to_date = '9999-01-01'
where de.dept_no = (select de.dept_no
                    from dept_emp de
                             join salaries sa on de.emp_no = sa.emp_no
                    where de.to_date = '9999-01-01'
                      and sa.to_date = '9999-01-01'
                    group by de.dept_no
                    order by avg(sa.salary) desc
                    limit 1)
order by sa.salary desc ;


-- 문제6.
-- 현재, 평균 급여가 가장 높은 부서의 이름 그리고 평균급여를 출력하세요.
select dep.dept_name '부서', avg(sa.salary) '평균급여'
from dept_emp de
         join salaries sa on de.emp_no = sa.emp_no and de.to_date = '9999-01-01'
         join departments dep on de.dept_no = dep.dept_no and sa.to_date = '9999-01-01'
group by dep.dept_name
order by avg(sa.salary) desc
limit 1;

-- 문제7.
-- 현재, 평균 급여가 가장 높은 직책의 타이틀 그리고 평균급여를 출력하세요.
select ti.title '직책', avg(sa.salary) '평균급여'
from titles ti
         join salaries sa on sa.emp_no = ti.emp_no and sa.to_date = '9999-01-01' and ti.to_date = '9999-01-01'
group by ti.title
order by 평균급여 desc
limit 1;


