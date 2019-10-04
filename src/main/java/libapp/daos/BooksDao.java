package libapp.daos;

import libapp.Application;
import libapp.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BooksDao
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

    public static void create(Book book) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (name, author) VALUES (?, ?)");
        stmt.setString(1, book.name);
        stmt.setString(2, book.author);
        stmt.execute();
    }

    public static Book read(int id) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next())
        {
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            return new Book(id, name, author);
        }
        return null;
    }

    public static List<Book> readAll() throws SQLException
    {
        List<Book> allBooks = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM books");
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            allBooks.add(new Book(id, name, author));
        }
        return allBooks;
    }

    public static void update(Book book) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET name = ?, author = ? WHERE id = ?");
        stmt.setString(1, book.name);
        stmt.setString(2, book.author);
        stmt.setInt(3, book.id);
        stmt.execute();
    }

    public static void delete(int id) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }
}
