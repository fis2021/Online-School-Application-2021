package org.fis.project.exceptions;

public class AddStudentNotTyped extends Exception {
    public AddStudentNotTyped() {
        super(String.format("Please add student username you want to add!"));
    }
}
