package libapp.models;

import java.util.List;

public class Book
{
    public int id;
    public String name;
    public String author;
    public List<String> tags;
    public int quantity;

    public Book(int id, String name, String author, List<String> tags, int quantity)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.tags = tags;
        this.quantity = quantity;
    }
}
