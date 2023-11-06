--create the tables
create table customers(id number(6) GENERATED ALWAYS AS IDENTITY, username VARCHAR2(20), password VARCHAR2(20), constraint user_pk primary key(id, username));
alter table customers drop constraint user_pk;
alter table customers modify username varchar2(20) unique;
alter table customers add CONSTRAINT user_pk primary key(id);

create table courses(id number(6) generated always as identity, course_name VARCHAR2(100), author varchar(100), length VARCHAR2(50), date_of_upload DATE
, url VARCHAR2(200), CONSTRAINT courses_pk primary key(id));
alter table courses add summary varchar2(4000);

create table customer_profiles(id number(6), first_name varchar2(100), last_name varchar2(100), dob date, address varchar2(200),
constraint customer_profile_pk primary key(id), constraint customer_profiles_customers_fk foreign key(id) references customers(id) on delete cascade);

create table course_customer_relations(customer_id, course_id, constraint course_customer_relations_customer_fk foreign key(customer_id) references customers(id),
constraint course_customer_relations_courses_fk foreign key(course_id) references courses(id));

alter table customers add first_name varchar2(100) not null;
alter table customers add last_name varchar2(100) not null;
alter table customers add dob date not null;
alter table customers add country varchar2(100) not null;

drop table customer_profiles;

alter table customers modify password varchar2(500) not null;
alter table customers add role varchar2(100) not null;

--select queries for the tables
select * from customers;
select * from courses;
select * from course_customer_relations;
