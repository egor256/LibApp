package libapp.controllers;

import libapp.daos.LibAppUsersDao;
import libapp.models.LibAppUser;
import libapp.models.RegistrationInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SignupController
{
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isValidEmail(String emailStr)
    {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @GetMapping("/signup")
    public String signup(Model model)
    {
        RegistrationInfo registrationInfo = new RegistrationInfo(null, null, null, null);
        model.addAttribute("registrationInfo", registrationInfo);
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(RegistrationInfo regInfo) throws SQLException
    {
        if (!isValidEmail(regInfo.getEmail()))
        {
            return "redirect:/signup?emailerror";
        }
        if (!regInfo.getPassword().equals(regInfo.getRepeatPassword()))
        {
            return "redirect:/signup?pwderror";
        }
        try
        {
            LibAppUsersDao.create(new LibAppUser(regInfo.getUsername(), regInfo.getPassword(), "PRE_AUTH_USER", regInfo.getEmail()));
        }
        catch (JdbcSQLIntegrityConstraintViolationException e)
        {
            return "redirect:/signup?error";
        }
        return "redirect:/login";
    }
}
