SELECT distinct s4.title, s4.year as year_start, year_end
FROM     
      (SELECT s1.id as id1, MAX(s2.year) as year_end
      FROM shows s1
      JOIN shows s2 on s1.id = s2.series
      AND s1.kind = 'series'
      AND s1.finished = '1'
      AND s2.kind = 'episod'
      GROUP BY s1.id
      HAVING COUNT(1)>20
      ) s3 JOIN shows s4 on s3.id1 = s4.id;

    
    