package org.fis.project.exceptions;

public class PresenceNotAccepted extends  Exception {
    public PresenceNotAccepted() {
        super(String.format("Presence must be positive integer!"));
    }
}
