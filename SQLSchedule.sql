CREATE DATABASE university_schedule

USE university_schedule
go

CREATE TABLE users (
    users_id INT identity (1, 1) PRIMARY KEY,
    users_username VARCHAR(30) NOT NULL UNIQUE,
    users_passw VARCHAR(20) NOT NULL
);

CREATE TABLE groups (
    group_id INT identity (1, 1) PRIMARY KEY,
    group_name VARCHAR(100) NOT NULL,
    group_major VARCHAR(50) NOT NULL,
    group_year INT NOT NULL
);

CREATE TABLE teacher (
    teacher_id INT identity (1, 1) PRIMARY KEY,
    users_id INT NOT NULL,
    teacher_name VARCHAR(50) NOT NULL,
	teacher_surname VARCHAR(50) NOT NULL,
    teacher_lastname VARCHAR(50) NOT NULL,
    teacher_department VARCHAR(50) NOT NULL
);

alter table teacher 
drop column users_id

CREATE TABLE course (
    course_id INT identity (1, 1) PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    course_department VARCHAR(50) NOT NULL
);


CREATE TABLE schedule (
    schedule_id INT identity (1, 1) PRIMARY KEY,
    course_id INT NOT NULL,
    teacher_id INT NOT NULL,
    schedule_room int NOT NULL,
	schedule_para int NOT NULL,
    schedule_day VARCHAR(15) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (course_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher (teacher_id)
);

CREATE TABLE enrollments (
    enrollment_id INT identity (1, 1) PRIMARY KEY,
    schedule_id INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (group_id),
    FOREIGN KEY (schedule_id) REFERENCES schedule (schedule_id)
);

drop table users;
drop table groups;
drop table teacher;
drop table course;
drop table schedule;
drop table enrollments;