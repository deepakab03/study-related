--Q : Given the following data definition, for the given rectangles, 
--write a query that selects each distinct value of area 
--and the number of rectangles having that area

DROP TABLE rectangles IF EXISTS;

CREATE TABLE rectangles (
	id 		INTEGER NOT NULL PRIMARY KEY,
	width 	INTEGER NOT NULL,
	height 	INTEGER NOT NULL
);

INSERT INTO rectangles VALUES (1, 30, 40);
INSERT INTO rectangles VALUES (2, 60, 20);
INSERT INTO rectangles VALUES (3, 50, 10);
INSERT INTO rectangles VALUES (4, 40, 30);
INSERT INTO rectangles VALUES (5, 25, 20);
INSERT INTO rectangles VALUES (6, 100, 5);
INSERT INTO rectangles VALUES (7, 250, 2);
INSERT INTO rectangles VALUES (8, 80, 50);
INSERT INTO rectangles VALUES (9, 100, 4);
INSERT INTO rectangles VALUES (10, 70, 2);
INSERT INTO rectangles VALUES (11, 120, 4);
INSERT INTO rectangles VALUES (12, 150, 12);
INSERT INTO rectangles VALUES (13, 20, 50);
INSERT INTO rectangles VALUES (14, 100, 10);

SELECT (width * height) as area, count(*)
FROM rectangles
GROUP BY area
--HAVING count(*) > 1
--ORDER BY area asc
--limit 1