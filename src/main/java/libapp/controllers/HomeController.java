package libapp.controllers;

import libapp.daos.BookingsDao;
import libapp.models.Booking;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController
{
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) throws SQLException
    {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        List<Booking> bookings = BookingsDao.readUserBookings(username);
        model.addAttribute("bookings", bookings);
        return "home";
    }
}
