package ru.ezhov.knowledgebook.connection;

/**
 * класс с запросами для работы
 *
 * @author RRNDeonisiusEZH
 */
public final class Querys
{

    public static final String SELECT_ID = "select next value for id;";

    public static final String SELECT_TREE
            = "WITH LINK\n"
            + "(\n"
            + "     id         \n"
            + "    ,name       \n"
            + "    ,level                \n"
            + ") AS (\n"
            + "    select \n"
            + "         id\n"
            + "        ,name\n"
            + "        ,0 \n"
            + "    from tree where parentId is null\n"
            + "    UNION ALL\n"
            + "    select\n"
            + "         tree.id         \n"
            + "        ,IFNULL(LINK.name || '/', '') || tree.name      \n"
            + "        ,LEVEL + 1             \n"
            + "    from LINK INNER JOIN tree ON LINK.ID = tree.parentId\n"
            + ")\n"
            + "SELECT \n"
            + "     t1.id         \n"
            + "    ,t1.name as fullPath      \n"
            + "    ,t1.level         \n"
            + "    ,t2.name       \n"
            + "    ,IFNULL(t2.parentId, 0) as parentId\n"
            + "    ,IFNULL(t2.code, '') as code\n"
            + "    ,IFNULL(t2.description, '') as description\n"
            + "    ,t2.firstAdd         \n"
            + "    ,t2.dateFirstAdd     \n"
            + "    ,t2.favorites        \n"
            + "    ,t2.languageBacklight\n"
            + "    ,t2.lastChange       \n"
            + "    ,t2.dateLastChange   \n"
            + "    ,IFNULL(t2.nameFile, '') as nameFile\n"
            + "    ,IFNULL(t2.sizeFile, 0) as sizeFile\n"
            + "FROM LINK t1 \n"
            + "left join RESULT_QUERY t2 on\n"
            + "    t1.ID = t2.ID\n"
            + "ORDER BY t1.name; ";

    public static final String INSERT_TREE
            = "insert into tree\n"
            + "(\n"
            + "    id\n"
            + "   ,name\n"
            + "   ,parentId\n"
            + ")\n"
            + "values (?, ?, ?)";

    public static final String INSERT_RECORDS
            = "insert into records\n"
            + "(\n"
            + "     id                 \n"
            + "    ,code               \n"
            + "    ,description        \n"
            + "    ,firstAdd           \n"
            + "    ,dateFirstAdd       \n"
            + "    ,languageBacklight  \n"
            + ")\n"
            + "values\n"
            + "(\n"
            + "     ?  --id                 int not null unique\n"
            + "    ,?  --code               varchar(max)\n"
            + "    ,?  --description        varchar(8000)\n"
            + "    ,?  --firstAdd           varchar(100) not null\n"
            + "    ,?  --dateFirstAdd       timestamp not null\n"
            + "    ,?  --languageBacklight  varchar(20) not null\n"
            + ")";

    public static final String UPDATE_TREE
            = "update tree\n"
            + "    set name = ?\n"
            + "where\n"
            + "    id = ?";

    public static final String UPDATE_RECORDS
            = "update records\n"
            + "    set\n"
            + "         code = ?\n"
            + "        ,description = ?\n"
            + "        ,languageBacklight = ?\n"
            + "        ,lastChange = ?\n"
            + "        ,dateLastChange = ?\n"
            + "where \n"
            + "    id = ?";

    public static final String DELETE_TREE
            = "delete from tree where id = ?";

    public static final String DELETE_RECORDS
            = "delete from records where id = ?";

    public static final String UPDATE_FAVORITE
            = "update records\n"
            + "    set favorites = ?\n"
            + "where\n"
            + "    id = ?";

    public static final String SELECT_TREE_FOR_LIST
            = "WITH LINK\n"
            + "(\n"
            + "     id         \n"
            + "    ,name       \n"
            + "    ,level                \n"
            + ") AS (\n"
            + "    select \n"
            + "         id\n"
            + "        ,name\n"
            + "        ,0 \n"
            + "    from tree where parentId is null\n"
            + "    UNION ALL\n"
            + "    select\n"
            + "         tree.id         \n"
            + "        ,IFNULL(LINK.name || '/', '') || tree.name      \n"
            + "        ,LEVEL + 1             \n"
            + "    from LINK INNER JOIN tree ON LINK.ID = tree.parentId\n"
            + ")\n"
            + "SELECT \n"
            + "     t1.id         \n"
            + "    ,t1.name as fullPath      \n"
            + "    ,t1.level         \n"
            + "    ,t2.name       \n"
            + "    ,IFNULL(t2.parentId, 0) as parentId\n"
            + "    ,IFNULL(t2.code, '') as code\n"
            + "    ,IFNULL(t2.description, '') as description\n"
            + "    ,t2.firstAdd         \n"
            + "    ,t2.dateFirstAdd     \n"
            + "    ,t2.favorites        \n"
            + "    ,t2.languageBacklight\n"
            + "    ,t2.lastChange       \n"
            + "    ,t2.dateLastChange   \n"
            + "    ,IFNULL(t2.nameFile, '') as nameFile\n"
            + "    ,IFNULL(t2.sizeFile, 0) as sizeFile\n"
            + "FROM LINK t1 \n"
            + "left join RESULT_QUERY t2 on\n"
            + "    t1.ID = t2.ID\n"
            + "where\n"
            + "t2.favorites = true\n"
            + "ORDER BY t1.name; ";

}
