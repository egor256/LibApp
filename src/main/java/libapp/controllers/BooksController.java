package libapp.controllers;

import libapp.daos.BookingsDao;
import libapp.daos.BooksDao;
import libapp.daos.TagsDao;
import libapp.models.Book;
import libapp.models.BookFilter;
import libapp.models.Booking;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BooksController
{
    private static boolean isValidSearchString(String searchString)
    {
        return searchString.trim().length() >= 3;
    }

    private static boolean matches(String string, String searchString)
    {
        return string.toLowerCase().trim().contains(searchString.toLowerCase().trim());
    }

    @Secured({"ROLE_USER", "ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @PostMapping("/books")
    public String books(@ModelAttribute("filter") BookFilter filter, Model model) throws SQLException
    {
        List<Book> books = BooksDao.readAll();

        List<String> selectedTags = Arrays.asList(filter.getSelectedTags());
        books = books.stream()
                     .filter(book -> !Collections.disjoint(selectedTags, book.getTags()))
                     .collect(Collectors.toList());

        String searchString = filter.getSearchString();
        if (searchString != null && !searchString.trim().isEmpty())
        {
            if (!isValidSearchString(searchString))
            {
                return "redirect:/books?error";
            }

            books = books.stream()
                    .filter(book -> matches(book.getName(), searchString) || matches(book.getAuthor(), searchString))
                    .collect(Collectors.toList());
        }
        model.addAttribute("books", books);
        model.addAttribute("allTags", TagsDao.getAllTags());
        return "books";
    }

    @Secured({"ROLE_USER", "ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/books")
    public String books(Model model) throws SQLException
    {
        List<Book> books = BooksDao.readAll();
        List<String> tags = TagsDao.getAllTags();
        model.addAttribute("books", books);
        model.addAttribute("allTags", tags);
        BookFilter bookFilter = new BookFilter();
        bookFilter.setSelectedTags(tags.toArray(new String[0])); // Select all tags by default
        model.addAttribute("filter", bookFilter);
        return "books";
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/books/delete")
    public String delete(int bookId) throws SQLException
    {
        BooksDao.delete(bookId);
        return "redirect:/books"; //TODO: message
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @PostMapping("/books/update")
    public String update(@ModelAttribute("book") Book book) throws SQLException
    {
        BooksDao.update(book);
        return "redirect:/books";
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/books/update")
    public String update(int bookId, Model model) throws SQLException
    {
        Book book = BooksDao.read(bookId);
        List<String> tags = TagsDao.getAllTags();
        model.addAttribute("allTags", tags);
        model.addAttribute("book", book);
        return "book_update";
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @PostMapping("/books/add")
    public String add(@ModelAttribute("book") Book book) throws SQLException
    {
        BooksDao.create(book);
        return "redirect:/books";
    }

    @Secured({"ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/books/add")
    public String add(Model model) throws SQLException
    {
        Book book = new Book(-1, "", "", new ArrayList<>(), 0);
        List<String> tags = TagsDao.getAllTags();
        model.addAttribute("allTags", tags);
        model.addAttribute("book", book);
        return "book_add";
    }

    @Secured({"ROLE_USER", "ROLE_LIBRARIAN", "ROLE_ADMIN"})
    @GetMapping("/order")
    public String order(int bookId) throws SQLException
    {
        boolean decrementSuccess = BooksDao.decrementQuantity(bookId);
        if (!decrementSuccess)
        {
            //TODO: error message
            return "redirect:/books?error";
        }
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        BookingsDao.create(new Booking(-1, bookId, username, Booking.Status.Ordered));
        return "redirect:/home";
    }
}
