package libapp.controllers;

import libapp.Application;
import libapp.EmailSender;
import libapp.daos.LibAppUsersDao;
import libapp.models.LibAppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/code")
public class CodeController
{
    private int verificationNumber;

    private void grantAuthority()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = new ArrayList<>(auth.getAuthorities());
        String preAuthRole = authorities.get(0).getAuthority();
        switch (preAuthRole)
        {
            case "ROLE_PRE_AUTH_USER":
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                break;
            case "ROLE_PRE_AUTH_LIBRARIAN":
                authorities.add(new SimpleGrantedAuthority("ROLE_LIBRARIAN"));
                break;
            case "ROLE_PRE_AUTH_ADMIN":
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            default:
                throw new RuntimeException("Unknown preAuthRole");
        }
        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String code(@RequestParam(value = "error", required = false) String error, Model model) throws SQLException
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        LibAppUser libAppUser = LibAppUsersDao.read(username);
        if (error == null && !Application.DEBUG)
        {
            new Thread(() ->
            {
                Random random = new Random();
                int min = 10000;
                int max = 99999;
                verificationNumber = random.nextInt(max - min + 1) + min;
                String subject = "Verification code";
                String message = String.format("Your verification code is: %d", verificationNumber);
                EmailSender.sendEmail(libAppUser.getEmail(), subject, message);
            }).start();
        }
        model.addAttribute("email", libAppUser.getEmail());
        return "code";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String code(String code)
    {
        String view = "redirect:/code?error";
        String expectedCode = Application.DEBUG ? "555" : String.valueOf(verificationNumber);
        if (code.equals(expectedCode))
        {
            grantAuthority();
            view = "redirect:/home";
        }
        return view;
    }
}
