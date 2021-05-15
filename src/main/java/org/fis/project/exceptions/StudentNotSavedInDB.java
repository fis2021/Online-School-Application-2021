package org.fis.project.exceptions;

public class StudentNotSavedInDB extends Exception {
    public StudentNotSavedInDB() {
        super(String.format("Student not stored in database!"));
    }
}
