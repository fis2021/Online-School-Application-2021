package org.fis.project.exceptions;

public class CompleteAllFieldsException extends Exception {

    public CompleteAllFieldsException()
    {
        super(String.format("Please complete all fields!"));
    }
}
