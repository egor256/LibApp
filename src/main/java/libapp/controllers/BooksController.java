package libapp.controllers;

import libapp.daos.BooksDao;
import libapp.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
public class BooksController
{
    @GetMapping("/books")
    public String books(Model model) throws SQLException
    {
        List<Book> books = BooksDao.readAll();
        model.addAttribute("books", books);
        return "books";
    }
}
