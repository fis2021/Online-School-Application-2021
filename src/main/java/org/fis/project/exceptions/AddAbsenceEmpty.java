package org.fis.project.exceptions;

public class AddAbsenceEmpty extends Exception {
    public AddAbsenceEmpty() {
        super(String.format("Please type absence to add!"));
    }
}
