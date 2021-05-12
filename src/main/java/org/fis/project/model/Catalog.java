package org.fis.project.model;

import org.dizitart.no2.objects.Id;

import javax.print.DocFlavor;

public class Catalog {

    private static int nr;

    @Id
    private Integer nrId;
    private String teacherId;
    private String studentId;
    private String subjectId;

    public Catalog(String teacherId) {
        this.nrId=new Integer(nr);
        nr=nr+1;
        this.teacherId=teacherId;
    }

    public Catalog(){

    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

}
