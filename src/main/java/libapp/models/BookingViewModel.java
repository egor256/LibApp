package libapp.models;

public class BookingViewModel
{
    private int bookingId;
    private String username;
    private String bookName;
    private String bookAuthor;
    private Booking.Status status;

    public BookingViewModel(int bookingId, String username, String bookName, String bookAuthor, Booking.Status status)
    {
        this.bookingId = bookingId;
        this.username = username;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.status = status;
    }

    public int getBookingId()
    {
        return bookingId;
    }

    public void setBookingId(int bookingId)
    {
        this.bookingId = bookingId;
    }

    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    public String getBookAuthor()
    {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor)
    {
        this.bookAuthor = bookAuthor;
    }

    public Booking.Status getStatus()
    {
        return status;
    }

    public void setStatus(Booking.Status status)
    {
        this.status = status;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
