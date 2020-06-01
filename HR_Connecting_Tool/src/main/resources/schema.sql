create table if not exists Employees(  
id varchar(8) primary key,
name varchar(25) not null,  
password varchar(25) not null, 
taskId varchar(8) not null);

create table if not exists Tasks(
taskId varchar(8) primary key,
taskText varchar(150) not null);

create table if not exists SolvedTasks(
solvedId int primary key,
employeeId varchar(8),
taskId varchar(8),
solvedWith varchar(25) Not Null 
);

alter table Employees add foreign key (taskId) references Tasks(taskId);

alter table SolvedTasks add foreign key (employeeId) references Employees(id);
alter table SolvedTasks add foreign key (taskId) references Tasks(taskId);