--college department table
CREATE TABLE dept (
	id		 integer 	NOT NULL,
	name 	 text		NOT NULL
);

--college professor table
CREATE TABLE prof (
	id		 integer 	NOT NULL,
	dept_id  integer    NOT NULL,
	name 	 text		NOT NULL,
	salary   integer    NOT NULL
);

INSERT INTO dept VALUES (1, 'ELECTRONICS');
INSERT INTO dept VALUES (2, 'MECHANICAL');
INSERT INTO dept VALUES (3, 'COMPUTER_SCIENCE');
INSERT INTO dept VALUES (4, 'INDUSTIRAL');

INSERT INTO prof VALUES (1, 1, 'Raghu', 2000);
INSERT INTO prof VALUES (2, 1, 'Kiran', 5000);
INSERT INTO prof VALUES (3, 1, 'Somu', 2000);
INSERT INTO prof VALUES (4, 2, 'Chomu', 8000);
INSERT INTO prof VALUES (5, 2, 'Pappu', 2000);
INSERT INTO prof VALUES (6, 2, 'Ghonchu', 1000);
INSERT INTO prof VALUES (7, 3, 'Dabru', 3000);
INSERT INTO prof VALUES (8, 3, 'Shilpa', 6000);
INSERT INTO prof VALUES (9, 3, 'Dindu', 1000);
INSERT INTO prof VALUES (10, 4, 'Sampan', 4000);
INSERT INTO prof VALUES (11, 4, 'Shereen', 3000);
INSERT INTO prof VALUES (12, 4, 'Bhanu', 5000);

--update prof set salary = 2000 where name = 'Somu';

--solution
select d.name, avg(p.salary) as avg_sal from prof p, dept d where p.dept_id = d.id group by d.name order by avg_sal desc limit 1;