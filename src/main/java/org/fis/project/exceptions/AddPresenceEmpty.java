package org.fis.project.exceptions;

public class AddPresenceEmpty extends  Exception {
    public AddPresenceEmpty() {
        super(String.format("Please type presence to add!")); }
}
