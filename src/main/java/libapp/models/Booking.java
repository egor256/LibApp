package libapp.models;

public class Booking
{
    public enum Status {Ordered, Received, Returned}
    public int id;
    public int bookId;
    public String username;
    public Status status;

    public Booking(int id, int bookId, String username, Status status)
    {
        this.id = id;
        this.bookId = bookId;
        this.username = username;
        this.status = status;
    }
}
