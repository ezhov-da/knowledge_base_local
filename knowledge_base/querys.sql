--------------------------------------------------------------------------------
/*таблица содержит данные для дерева*/
-- drop table tree
create table tree
(
     id         integer not null PRIMARY KEY
    ,name       varchar(100) not null
    ,parentId   int
);
CREATE INDEX id ON tree(id);

/*таблица содержит данные для формы*/
-- drop table records
create table records
(
     id                 int not null unique
    ,code               varchar(max)
    ,description        varchar(8000)
    ,firstAdd           varchar(100) not null
    ,dateFirstAdd       timestamp not null
    ,favorites          bit not null default 0
    ,languageBacklight  varchar(20) not null
    ,lastChange         varchar(100)
    ,dateLastChange     timestamp
);
--FOREIGN KEY(id) REFERENCES records(id) ON DELETE CASCADE;
CREATE INDEX idRecords ON records(id);

/*таблица содержит данные о приложенном файле*/
-- drop table files
create table files
(
      id        integer not null unique
     ,nameFile  varchar(256) not null
     ,sizeFile  int not null
     ,fileInput  blob not null
);
CREATE INDEX idFiles ON files(id);
--------------------------------------------------------------------------------
select * from tree
select * from records
select * from files

/*
    delete from tree;
    delete from records;
    delete from files;
*/



CREATE SEQUENCE IF NOT EXISTS id

-- вносим данные по главному узлу
insert into tree
(
    id
   ,name
)
values
(
     next value for id
    ,'информация'
)

--
insert into records
(
     id                 
    ,code               
    ,description        
    ,firstAdd           
    ,dateFirstAdd       
    ,favorites  
    ,languageBacklight
)
values
(
     97
    ,'--'
    ,'--'
    ,'ezhov_da'
    ,CURRENT_DATE()
    ,0  
    ,'plain'
)


--ВЬЮШКА ДЛЯ ПОСТРОЕНИЯ ДЕРЕВА
-- drop view RESULT_QUERY


CREATE VIEW RESULT_QUERY
(
     id         
    ,name       
    ,PARENTID                
    ,code             
    ,description      
    ,firstAdd         
    ,dateFirstAdd     
    ,favorites        
    ,languageBacklight
    ,lastChange       
    ,dateLastChange   
    ,nameFile 
    ,sizeFile 
    ,fileInput
) AS 
select
     t1.id         
    ,t1.name       
    ,t1.PARENTID                
    ,t2.code             
    ,t2.description      
    ,t2.firstAdd         
    ,t2.dateFirstAdd     
    ,t2.favorites        
    ,t2.languageBacklight
    ,t2.lastChange       
    ,t2.dateLastChange   
    ,t3.nameFile 
    ,t3.sizeFile 
    ,t3.fileInput
from tree t1
inner join records t2 on
    t1.ID = t2.ID
left join files t3 on
    t1.ID = t3.ID

select * from RESULT_QUERY



--строим запрос для дерева






WITH LINK
(
     id         
    ,name       
    ,level                
) AS (
    select 
         id
        ,name
        ,0
    from tree where parentId is null
    UNION ALL
    select
         tree.id         
        ,IFNULL(LINK.name || '/', '') || tree.name      
        ,LEVEL + 1             
    from LINK INNER JOIN tree ON LINK.ID = tree.parentId
)
SELECT 
     t1.id         
    ,t1.name as fullPath      
    ,t1.level         
    ,t2.name       
    ,IFNULL(t2.parentId, 0) as parentId
    ,IFNULL(t2.code, '') as code
    ,IFNULL(t2.description, '') as description
    ,t2.firstAdd         
    ,t2.dateFirstAdd     
    ,t2.favorites        
    ,t2.languageBacklight
    ,t2.lastChange       
    ,t2.dateLastChange   
    ,IFNULL(t2.nameFile, '') as nameFile
    ,IFNULL(t2.sizeFile, 0) as sizeFile
FROM LINK t1 
left join RESULT_QUERY t2 on
    t1.ID = t2.ID
ORDER BY t1.name; 





--
select next value for id

insert into tree
(
    id
   ,name
   ,parentId
)
values (?, ?, ?)
--
insert into records
(
     id                 
    ,code               
    ,description        
    ,firstAdd           
    ,dateFirstAdd       
    ,favorites          
    ,languageBacklight  
    ,lastChange         
    ,dateLastChange     
)
values
(
     ?  --id                 int not null unique
    ,?  --code               varchar(max)
    ,?  --description        varchar(8000)
    ,?  --firstAdd           varchar(100) not null
    ,?  --dateFirstAdd       timestamp not null
    ,?  --favorites          bit not null default 0
    ,?  --languageBacklight  varchar(20) not null
    ,?  --lastChange         varchar(100)
    ,?  --dateLastChange     timestamp
)


------
update tree
    set name = ?
where
    id = ?


update records
    set
         code = ?
        ,description = ?
        ,languageBacklight = ?
        ,lastChange = ?
        ,dateLastChange = ?
where 
    id = ?


delete from tree where id = ?

delete from records where id = ?


update records
    set favorites = ?
where
    id = ?

