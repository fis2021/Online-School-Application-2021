package org.fis.project.exceptions;

public class AbsenceNotAccepted extends Exception {
    public AbsenceNotAccepted() {
        super(String.format("Presence must be positive integer!"));
    }
}
