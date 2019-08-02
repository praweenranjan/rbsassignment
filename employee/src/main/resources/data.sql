create table employee
(
  empid integer not null,
  name varchar(255) not null,
  hiredate timestamp,
  salary double,
  primary key(empid)
);

insert into employee(empid, name, hiredate,salary) values(10001,'Praween', sysdate(), 80000);
insert into employee(empid, name, hiredate,salary) values(10002,'Ranjan', sysdate(), 90000);
insert into employee values(10003,'Mamta', sysdate(), 70000);

/*insert into employeejpa(EMPID, name, hireDate,salary) values(10001,'Praween', sysdate(), 80000);
insert into employeejpa(EMPID, name, hireDate,salary) values(10002,'Ranjan', sysdate(), 90000);
insert into employeejpa(EMPID, name, hireDate,salary) values(10003,'Mamta', sysdate(), 70000);*/