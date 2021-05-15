package org.fis.project.exceptions;

public class StudentAlreadyAdded extends Exception {
    public StudentAlreadyAdded() {
        super(String.format("Student already added to class!"));
    }
}
