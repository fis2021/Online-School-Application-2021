package org.fis.project.exceptions;

public class GradeNotAccepted extends Exception {
    public GradeNotAccepted() {
        super(String.format("Grade must be in [1; 10] interval!"));
    }
}
