package org.fis.project.exceptions;

public class ConfirmPasswordException extends Exception
{
    private String password;
    private String confirmpassword;

    public ConfirmPasswordException(String password, String confirmpassword)
    {
        super(String.format("Please enter same password twice!"));
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    public String getPassword()
    {
        return password;
    }

    public String getConfirmPassword()
    {
        return confirmpassword;
    }
}
