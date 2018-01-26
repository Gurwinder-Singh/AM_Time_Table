
-- select * from SUBJECTS;
-- ALTER TABLE SUB_ALLOC ADD  column  "GROUPS" INT ;

-- ALTER TABLE TIME_TABLE add column SUB_ALLOC_ID_SEC bigint;

select * from time_table ;

select t.*, s1.teach_id as teach_id1, s2.teach_id as teach_id2 from time_table t 
                    inner join sub_alloc s1 on (s1.id = t.sub_alloc_id) 
                    left join sub_alloc s2 on (s2.id = t.sub_alloc_id_sec);