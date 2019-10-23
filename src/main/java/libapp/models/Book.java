package libapp.models;

import java.util.List;

public class Book
{
    private int id;
    private String name;
    private String author;
    private List<String> tags;
    private int quantity;

    public Book(int id, String name, String author, List<String> tags, int quantity)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.tags = tags;
        this.quantity = quantity;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
