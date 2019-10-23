package libapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        //return new BCryptPasswordEncoder(); //TODO!
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
            .antMatchers("/signup", "/static/**").permitAll()
            .antMatchers("/code").hasAnyRole("PRE_AUTH_USER", "PRE_AUTH_LIBRARIAN", "PRE_AUTH_ADMIN")
            .antMatchers("/home").hasAnyRole("USER", "LIBRARIAN", "ADMIN")
            .anyRequest().authenticated();

        http.formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/code", true)
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception
    {
        builder.jdbcAuthentication().dataSource(Application.getDataSource())
                //.passwordEncoder(passwordEncoder()) //TODO!
                .usersByUsernameQuery("select principal,credentials,true from users where principal = ?")
                .authoritiesByUsernameQuery("select principal,role from users where principal = ?")
                .rolePrefix("ROLE_");
    }
}
