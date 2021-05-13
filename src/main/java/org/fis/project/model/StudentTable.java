package org.fis.project.model;

import javafx.beans.property.SimpleStringProperty;

public class StudentTable {

    private SimpleStringProperty studentSubjects;
    private SimpleStringProperty studentTeachers;
    public StudentTable(String subject,String teacher){
        this.studentSubjects=new SimpleStringProperty(subject);
        this.studentTeachers=new SimpleStringProperty(teacher);
    }
    public StudentTable(){}

    public String getStudentSubjects() {
        return studentSubjects.get();
    }

    public void setStudentSubjects(String teacherName) {
        this.studentSubjects=new SimpleStringProperty(teacherName);
    }

    public String getStudentTeachers() {
        return studentTeachers.get();
    }

    public void setStudentTeachers(String subjectName) {
        this.studentTeachers=new SimpleStringProperty(subjectName);
    }

}
