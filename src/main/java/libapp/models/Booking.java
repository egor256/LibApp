package libapp.models;

public class Booking
{
    public enum Status {Ordered, Received, Returned}

    private int id;
    private int bookId;
    private String username;
    private Status status;

    public Booking(int id, int bookId, String username, Status status)
    {
        this.id = id;
        this.bookId = bookId;
        this.username = username;
        this.status = status;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
}
