package org.fis.project.model;

import javafx.beans.property.SimpleStringProperty;

public class SubjectInformation {
    private SimpleStringProperty studentName;
    public SubjectInformation(String subjectName){
        this.studentName=new SimpleStringProperty(subjectName);
    }
    public SubjectInformation(){}

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String subjectName) {
        this.studentName=new SimpleStringProperty(subjectName);
    }
}
