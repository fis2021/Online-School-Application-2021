package org.fis.project.exceptions;

public class AddGradeEmpty extends  Exception {
    public AddGradeEmpty() {
        super(String.format("Please type grade to add!"));
}
}
