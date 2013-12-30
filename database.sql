    set foreign_key_checks = 0;
    drop table if exists Address;

    drop table if exists Grades;

    drop table if exists Student;

    drop table if exists Student_Teacher;

    drop table if exists Teacher;
    set foreign_key_checks = 1;
    create table Address (
        id bigint not null auto_increment unique,
        city varchar(255),
        primary key (id)
    );

    create table Grades (
        id bigint not null auto_increment unique,
        english integer,
        math integer,
        science integer,
        primary key (id)
    );

    create table Student (
        id bigint not null auto_increment unique,
        first_name varchar(255),
        gender varchar(255),
        last_name varchar(255),
        level integer,
        address_id bigint,
        grades_id bigint,
        primary key (id)
    );

    create table Student_Teacher (
        student_id bigint not null,
        teacher_id bigint not null,
        primary key (student_id, teacher_id)
    );

    create table Teacher (
        id bigint not null auto_increment unique,
        first_name varchar(255),
        gender varchar(255),
        last_name varchar(255),
        address_id bigint,
        primary key (id)
    );

    alter table Student 
        add index FKF3371A1B425098EF (address_id), 
        add constraint FKF3371A1B425098EF 
        foreign key (address_id) 
        references Address (id);

    alter table Student 
        add index FKF3371A1BB00509E5 (grades_id), 
        add constraint FKF3371A1BB00509E5 
        foreign key (grades_id) 
        references Grades (id);

    alter table Student_Teacher 
        add index FKFCAED99E99DA6A2F (teacher_id), 
        add constraint FKFCAED99E99DA6A2F 
        foreign key (teacher_id) 
        references Teacher (id);

    alter table Student_Teacher 
        add index FKFCAED99E893F1F8F (student_id), 
        add constraint FKFCAED99E893F1F8F 
        foreign key (student_id) 
        references Student (id);

    alter table Teacher 
        add index FKD6A63C2425098EF (address_id), 
        add constraint FKD6A63C2425098EF 
        foreign key (address_id) 
        references Address (id);