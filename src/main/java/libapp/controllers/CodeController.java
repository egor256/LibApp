package libapp.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/code")
public class CodeController
{
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
    public String code()
    {
        return "code";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String code(String code)
    {
        String view = "redirect:/code?error";
        if (code.equals("555"))
        {
            grantAuthority();
            view = "redirect:/home";
        }
        return view;
    }
}
