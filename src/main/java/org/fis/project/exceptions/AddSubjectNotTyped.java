package org.fis.project.exceptions;

public class AddSubjectNotTyped extends Exception {
    public AddSubjectNotTyped() {
        super(String.format("Please add subject you want to add!"));
    }
}
