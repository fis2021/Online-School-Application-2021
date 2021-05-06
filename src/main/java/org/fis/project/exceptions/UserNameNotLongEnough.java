package org.fis.project.exceptions;

public class UserNameNotLongEnough extends Exception
{
    public UserNameNotLongEnough()
    {
        super(String.format("Username must contain at least 6 charachters!"));
    }
}
