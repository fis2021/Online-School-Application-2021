package org.fis.project.exceptions;

public class PasswordNotLongEnough extends  Exception {
    public PasswordNotLongEnough()
    {
        super(String.format("Password must contain at least 6 characters"));
    }
}
