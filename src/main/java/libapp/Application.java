package libapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class Application
{
    public static final boolean DEBUG = true;

    @Bean
    public static DataSource getDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:file:./LibAppDb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        return dataSource;
    }

    public static Connection getDatabaseConnection() throws SQLException
    {
        return getDataSource().getConnection();
    }

    public static void main(String[] args) throws Throwable
    {
        if (!Application.DEBUG && EmailSender.getSender().getPassword() == null)
        {
            throw new RuntimeException("Please enter mail sender password in EmailSender.java!");
        }
        SpringApplication.run(Application.class, args);
    }
}
