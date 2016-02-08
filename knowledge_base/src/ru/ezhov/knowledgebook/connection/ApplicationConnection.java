package ru.ezhov.knowledgebook.connection;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ru.ezhov.knowledgebook.ApplicationPath;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class ApplicationConnection
{

    private static Connection connection;

    private ApplicationConnection()
    {
    }

    ;
            
    public static Connection getInstance() throws ClassNotFoundException, SQLException, UnsupportedEncodingException
    {
        if (connection == null || connection.isClosed())
        {
            connection = CreateConnection.getConnection();
        }
        return connection;
    }

    static class CreateConnection
    {

        private static final String NAME_BASE = "knowledge_book";

        public static Connection getConnection() throws ClassNotFoundException, SQLException, UnsupportedEncodingException
        {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:" + ApplicationPath.getPath() + NAME_BASE, null, null);
            return connection;
        }
    }
}
