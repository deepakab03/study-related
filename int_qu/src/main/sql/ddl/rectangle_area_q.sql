--Q : Given the following data definition, for the given rectangles, 
--write a query that selects each distinct value of area 
--and the number of rectangles having that area

--IF EXISTS doesn't work in derby, error is ignored via Spring!
DROP TABLE rectangles;

CREATE TABLE rectangles (
	id 		INTEGER NOT NULL PRIMARY KEY,
	width 	INTEGER NOT NULL,
	height 	INTEGER NOT NULL
);


---------------------SOLUTION
--SELECT (width * height) as area, count(*) FROM rectangles GROUP BY area
--HAVING count(*) > 1" + 
--ORDER BY area asc" + 
--limit 1";