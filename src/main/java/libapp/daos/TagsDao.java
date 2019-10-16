package libapp.daos;

import libapp.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagsDao
{
    private static Connection conn;

    static
    {
        try
        {
            conn = Application.getDatabaseConnection();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllTags() throws SQLException
    {
        List<String> allTags = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT name FROM tags");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next())
        {
            String name = resultSet.getString("name");
            allTags.add(name);
        }
        return allTags;
    }
}
