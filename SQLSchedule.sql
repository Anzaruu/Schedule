CREATE DATABASE university_schedule

USE university_schedule
go

CREATE TABLE users (
    users_id INT identity (1, 1) PRIMARY KEY,
    users_username VARCHAR(30) NOT NULL UNIQUE,
    users_passw VARCHAR(20) NOT NULL
);

CREATE TABLE teacher (
    teacher_id INT identity (1, 1) PRIMARY KEY,
    teacher_name VARCHAR(50) NOT NULL,
	teacher_surname VARCHAR(50) NOT NULL,
    teacher_lastname VARCHAR(50) NOT NULL,
    teacher_department VARCHAR(50) NOT NULL
);

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

drop table users;
drop table teacher;
drop table course;
drop table schedule;