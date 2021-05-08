package org.fis.project.exceptions;

public class CompleteLoginDataException extends Exception {
    public CompleteLoginDataException()
    {
        super(String.format("Please complete all log in fields!"));
    }
}
