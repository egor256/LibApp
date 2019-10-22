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

    private static List<String> readBookTags(int id) throws SQLException
    {
        List<String> tags = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT t.name FROM tags t " +
            "INNER JOIN bookTags bt ON t.id = bt.tagId " +
            "INNER JOIN books b ON bt.bookId = b.id " +
            "WHERE b.id = ?"
        );
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next())
        {
            String tag = resultSet.getString("name");
            tags.add(tag);
        }
        return tags;
    }

    private static void writeBookTags(int id, List<String> tags) throws SQLException
    {
        for (String tag : tags)
        {
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM tags WHERE name = ?");
            stmt.setString(1, tag);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
            {
                int tagId = resultSet.getInt("id");
                PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO bookTags (bookId, tagId) VALUES (?, ?)");
                stmt2.setInt(1, id);
                stmt2.setInt(2, tagId);
                stmt2.execute();
            }
        }
    }

    private static void deleteBookTags(int id) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM bookTags WHERE bookId = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void create(Book book) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO books (name, author, quantity) VALUES (?, ?, ?)");
        stmt.setString(1, book.name);
        stmt.setString(2, book.author);
        stmt.setInt(3, book.quantity);
        stmt.execute();
        writeBookTags(book.id, book.tags);
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
            List<String> tags = readBookTags(id);
            int quantity = resultSet.getInt("quantity");
            return new Book(id, name, author, tags, quantity);
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
            List<String> tags = readBookTags(id);
            int quantity = resultSet.getInt("quantity");
            allBooks.add(new Book(id, name, author, tags, quantity));
        }
        return allBooks;
    }

    public static void update(Book book) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET name = ?, author = ?, quantity = ?, WHERE id = ?");
        stmt.setString(1, book.name);
        stmt.setString(2, book.author);
        stmt.setInt(3, book.quantity);
        stmt.setInt(4, book.id);
        stmt.execute();
        deleteBookTags(book.id);
        writeBookTags(book.id, book.tags);
    }

    public static void delete(int id) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM books WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
        deleteBookTags(id);
    }

    // Returns true if decrement was successful (quantity > 0)
    public static boolean decrementQuantity(int id) throws SQLException
    {
        PreparedStatement stmt = conn.prepareStatement("UPDATE books SET quantity = quantity - 1 WHERE id = ? AND quantity > 0");
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }
}
