package libapp.daos;

import libapp.Application;
import libapp.models.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingsDao
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

    public static void create(Booking booking) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO bookings (bookId, principal, status) VALUES (?, ?, ?)");
        stmt.setInt(1, booking.bookId);
        stmt.setString(2, booking.username);
        stmt.setInt(3, booking.status.ordinal());
        stmt.execute();
    }

    public static List<Booking> readUserBookings(String username) throws SQLException
    {
        List<Booking> bookings = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings WHERE principal = ?");
        stmt.setString(1, username);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt("id");
            int bookId = resultSet.getInt("bookId");
            int status = resultSet.getInt("status");
            bookings.add(new Booking(id, bookId, username, Booking.Status.values()[status]));
        }
        return bookings;
    }
}
