delete from Employees;
delete from Tasks;
delete from SolvedTasks;

insert into Tasks(taskId, taskText) values ('5001', 'Gehe mit einem bisher unbekannten Mitarbeiter Kaffee trinken');
insert into Tasks(taskId, taskText) values ('5002', 'Spiele mit neuen Mitarbeitern ein Brettspiel');
insert into Tasks(taskId, taskText) values ('5003', 'Gehe mit jemanden in der Mittagspause spazierien');
insert into Tasks(taskId, taskText) values ('5004', 'Bringe zwei neue Mitarbeiter in bekanntschaft zueinander');


insert into Employees (id, name, password, taskId) values ('1001', 'Peter', '12345678','5001');
insert into Employees (id, name, password, taskId) values ('1002', 'Hans', '12345678','5002');
insert into Employees (id, name, password, taskId) values ('1003', 'Lukas', '12345678','5003');
insert into Employees (id, name, password, taskId) values ('1004', 'Lisa', '12345678','5004');

insert into SolvedTasks (solvedId, employeeId, taskId, solvedWith) values ('9001', '1001', '5002', 'Lisa');
