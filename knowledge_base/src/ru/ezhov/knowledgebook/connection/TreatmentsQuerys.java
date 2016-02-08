package ru.ezhov.knowledgebook.connection;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author RRNDeonisiusEZH
 */
public class TreatmentsQuerys
{

    public static synchronized void insertNode(
            String name,
            int parentId,
            String code,
            String description,
            String lanString
    ) throws SQLException, ClassNotFoundException, UnsupportedEncodingException
    {

        int id = getId();
        Connection connection = ApplicationConnection.getInstance();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(Querys.INSERT_TREE);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, parentId);
        preparedStatement.execute();
        preparedStatement.close();
        preparedStatement = connection.prepareStatement(Querys.INSERT_RECORDS);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, code);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, System.getProperty("user.name"));
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setString(6, lanString);
        preparedStatement.execute();
        preparedStatement.close();

    }

    public static synchronized void updateNode(
            int id,
            String name,
            String code,
            String description,
            String lanString
    ) throws SQLException, ClassNotFoundException, UnsupportedEncodingException
    {

        Connection connection = ApplicationConnection.getInstance();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(Querys.UPDATE_TREE);
        preparedStatement.setInt(2, id);
        preparedStatement.setString(1, name);
        preparedStatement.execute();
        preparedStatement.close();
        preparedStatement = connection.prepareStatement(Querys.UPDATE_RECORDS);
        preparedStatement.setString(1, code);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, lanString);
        preparedStatement.setString(4, System.getProperty("user.name"));
        preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setInt(6, id);
        preparedStatement.execute();
        preparedStatement.close();

    }

    public static synchronized void deleteNode(int id) throws SQLException, ClassNotFoundException, UnsupportedEncodingException
    {
        Connection connection = ApplicationConnection.getInstance();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(Querys.DELETE_TREE);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();
        preparedStatement = connection.prepareStatement(Querys.DELETE_RECORDS);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public static synchronized void favorite(Boolean favorite, int id) throws SQLException, ClassNotFoundException, UnsupportedEncodingException
    {
        Connection connection = ApplicationConnection.getInstance();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(Querys.UPDATE_FAVORITE);
        preparedStatement.setBoolean(1, favorite);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public static synchronized int getId() throws SQLException, ClassNotFoundException, UnsupportedEncodingException
    {
        Connection connection = ApplicationConnection.getInstance();
        ResultSet resultSet = connection.createStatement().executeQuery(Querys.SELECT_ID);
        int id = 0;
        while (resultSet.next())
        {
            id = resultSet.getInt(1);
        }

        resultSet.close();
        return id;

    }

}
