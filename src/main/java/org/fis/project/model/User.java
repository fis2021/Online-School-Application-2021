package org.fis.project.model;

import org.dizitart.no2.objects.Id;

public class User {
    @Id
    private String username;
    private String password;
    private String confirmpassword;
    private String role;
    private String firstname;
    private String lastname;

    public User(String username, String password, String confirmpassword, String role, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConfirmPassword()
    {
        return confirmpassword;
    }

    public void setConfirmPassword(String confirmpassword)
    {
        this.confirmpassword = confirmpassword;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getFirstName()
    {
        return firstname;
    }

    public void setFirstName(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastName()
    {
        return lastname;
    }

    public void setLastName(String lastname)
    {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return role != null ? role.equals(user.role) : user.role == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
