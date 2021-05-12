package org.fis.project.model;

import javafx.beans.property.SimpleStringProperty;

public class TeacherSubjects {

    private SimpleStringProperty subjectName;

    public TeacherSubjects(String subjectName){
        this.subjectName=new SimpleStringProperty(subjectName);
    }
    public TeacherSubjects(){}

    public String getSubjectName() {
        return subjectName.get();
    }

    public void setSubjectName(String subjectName) {
        this.subjectName=new SimpleStringProperty(subjectName);
    }
}
