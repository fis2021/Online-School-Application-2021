package org.fis.project.exceptions;

public class SubjectAlreadyAdded extends  Exception {
    public SubjectAlreadyAdded() {
        super(String.format("Subject already added to dashboard!"));
    }
}
