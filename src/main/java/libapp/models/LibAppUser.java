package libapp.models;

public class LibAppUser
{
    private String username;
    private String credentials;
    private String role;
    private String email;

    public LibAppUser(String username, String credentials, String role, String email)
    {
        this.username = username;
        this.credentials = credentials;
        this.role = role;
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getCredentials()
    {
        return credentials;
    }

    public void setCredentials(String credentials)
    {
        this.credentials = credentials;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
