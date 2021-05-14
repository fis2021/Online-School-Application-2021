package org.fis.project.model;

import org.dizitart.no2.objects.Id;

import javax.print.DocFlavor;

public class Catalog {

    private static int nr;

    @Id
    private String nrId;
    private String teacherId;
    private String studentId;
    private String subjectId;
    private String homeworkRequirements;
    private String courseMaterials;

    public Catalog(String teacherId) {
        nrId="ID"+nr;
        nr=nr+1;
        this.teacherId=teacherId;
    }

    public Catalog(String teacherId,String studentId,String subjectId){
        this.teacherId=teacherId;
        this.studentId=studentId;
        this.subjectId=subjectId;
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

    public String getHomeworkRequirements() {
        return homeworkRequirements;
    }

    public void setHomeworkRequirements(String homeworkRequirements) {
        this.homeworkRequirements = homeworkRequirements;
    }

    public String getCourseMaterials() {
        return courseMaterials;
    }

    public void setCourseMaterials(String courseMaterials) {
        this.courseMaterials = courseMaterials;
    }
}
