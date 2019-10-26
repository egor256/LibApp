package libapp.controllers;

import libapp.daos.BookingsDao;
import libapp.daos.BooksDao;
import libapp.models.Book;
import libapp.models.Booking;
import libapp.models.BookingViewModel;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingsController
{
    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/bookings")
    public String bookings(Model model) throws SQLException
    {
        List<Booking> bookings = BookingsDao.readAllBookings();
        List<BookingViewModel> bookingsViewModels = new ArrayList<>();
        for (Booking booking : bookings)
        {
            Book book = BooksDao.read(booking.getBookId());
            BookingViewModel vm = new BookingViewModel(
                    booking.getId(),
                    booking.getUsername(),
                    book.getName(),
                    book.getAuthor(),
                    booking.getStatus()
            );
            bookingsViewModels.add(vm);
        }
        model.addAttribute("bookingsViewModels", bookingsViewModels);
        return "bookings";
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/bookings/delete")
    public String delete(int bookingId) throws SQLException
    {
        Booking booking = BookingsDao.read(bookingId);
        int bookId = booking.getBookId();
        if (BookingsDao.delete(bookingId))
        {
            BooksDao.incrementQuantity(bookId);
        }
        return "redirect:/bookings"; //TODO: message
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @PostMapping("/bookings/update")
    public String update(@ModelAttribute("booking") Booking booking) throws SQLException
    {
        BookingsDao.update(booking);
        return "redirect:/bookings"; //TODO: message
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/bookings/update")
    public String update(int bookingId, Model model) throws SQLException
    {
        Booking booking = BookingsDao.read(bookingId);
        model.addAttribute("booking", booking);
        return "booking_update";
    }
}
