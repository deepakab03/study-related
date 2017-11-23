--IF EXISTS doesn't work with apache derby but this is ignored via Spring!
DROP TABLE prof;
DROP TABLE dept;

--college department table
CREATE TABLE dept (
	id		 INTEGER 			NOT NULL,
	name 	 VARCHAR (255)		NOT NULL
);

--college professor table
CREATE TABLE prof (
	id		 INTEGER 			NOT NULL,
	dept_id  INTEGER    		NOT NULL,
	name 	 VARCHAR (255)		NOT NULL,
	salary   INTEGER    		NOT NULL
);

---------------------SOLUTION
---Works on postgres SQl
--select d.name, avg(p.salary) as avg_sal from prof p, dept d where p.dept_id = d.id group by d.name order by avg_sal desc limit 1;

--Alternative is to use select top 1 for SYBASE, SQL Server OR FETCH FIRST 1 ROWS ONLY for apache derby (also postgres) so the last one might be the most 
--cross platform one supported