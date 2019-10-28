package libapp.daos;

import libapp.Application;
import libapp.models.LibAppUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibAppUsersDao
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

    public static LibAppUser read(String username) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE principal = ?");
        stmt.setString(1, username);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next())
        {
            String credentials = resultSet.getString("credentials");
            String role = resultSet.getString("role");
            String email = resultSet.getString("email");
            return new LibAppUser(username, credentials, role, email);
        }
        return null;
    }
}
