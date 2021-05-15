package org.fis.project.services;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.project.exceptions.*;
import org.fis.project.model.Catalog;
import org.fis.project.model.TeacherSubjects;

import javax.print.DocFlavor;
import java.util.LinkedList;
import java.util.Objects;

import static org.fis.project.services.FileSystemService.getPathToFile;

public class CatalogService {

    private static ObjectRepository<Catalog> catalogRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("Catalog.db").toFile())
                .openOrCreate("test", "test");

        catalogRepository = database.getRepository(Catalog.class);
    }

    public static void addTeacher_Subject(String teacherUsername,String teacherSubject) throws AddSubjectNotTyped,SubjectAlreadyAdded  {

        checkSubjectAddFieldNotEmpty(teacherSubject);
        checkSubjectAlreadyExists(teacherSubject);

        Catalog c = new Catalog(teacherUsername);
        c.setSubjectId(teacherSubject);
        catalogRepository.insert(c);
    }

    public static void addTeacher_Student_Subject(String teacherUsername,String studentUsername,String subjectName) throws AddStudentNotTyped, StudentAlreadyAdded, StudentNotSavedInDB {

        checkStudentAddFieldNotEmpty(studentUsername);
        checkStudentAlreadyExists(studentUsername, subjectName);

        UserService.StudentuserName(studentUsername);


        Catalog c = new Catalog(teacherUsername);
        c.setSubjectId(subjectName);
        c.setStudentId(studentUsername);
        catalogRepository.insert(c);
    }

    public static void test(){
        for(Catalog catalog:catalogRepository.find()) {
            System.out.println(catalog.getTeacherId()+" "+catalog.getSubjectId() + " "+ catalog.getStudentId()+" "+catalog.getHomeworkRequirements());
        }
    }

    public static LinkedList<String> teacherSubjects(String teacherUsername){

        LinkedList<String> teacherSubject =new LinkedList<String>();

        for(Catalog catalog:catalogRepository.find()){
            if(catalog.getTeacherId().equals(teacherUsername) && catalog.getStudentId()==null){
                teacherSubject.add(catalog.getSubjectId());
            }
        }

        return teacherSubject;
    }

    public static LinkedList<String> studentsSubject(String teacherUsername,String subjectName){

        LinkedList<String> students =new LinkedList<String>();

        for(Catalog catalog:catalogRepository.find()){
            if(catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId()!=null){
                students.add(catalog.getStudentId());
            }
        }

        return students;
    }

    public static LinkedList<Pair<String, String>> studentSubjectsTeachers(String studentUsername) {

        LinkedList<Pair<String,String>> subjects_Teachers=new LinkedList<Pair<String,String>>();

        for(Catalog catalog:catalogRepository.find()){
            if(catalog.getStudentId()!=null && catalog.getStudentId().equals(studentUsername)){
                subjects_Teachers.add(new Pair<String,String>(catalog.getSubjectId(), catalog.getTeacherId()));
            }
        }

        return subjects_Teachers;
    }

    public static void clearSubject(String teacher, String subject){
        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getTeacherId().equals(teacher) && catalog.getSubjectId().equals(subject)) {
                catalogRepository.remove(catalog);
                break;
            }
        }
    }

    public static void clearStudent(String teacher, String student,String subject){
        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getStudentId()!=null && catalog.getStudentId().equals(student) && catalog.getTeacherId().equals(teacher) && catalog.getSubjectId().equals(subject)) {
                catalogRepository.remove(catalog);
                break;
            }
        }
    }

    public static void addHomework(String teacherUsername, String subjectName, String requirements) {
        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId()==null) {
                catalog.setHomeworkRequirements(requirements);
                catalogRepository.update(catalog);
                break;
            }
        }
    }

    public static String searchHomeworkRequirements(String teacherUsername, String subjectName) {
        for (Catalog catalog : catalogRepository.find()) {
            if (catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName)) {
                return catalog.getHomeworkRequirements();
            }
        }
        return "";
    }


    public static void addGrade(String teacherUsername, String studentUsername, String subjectName, String grade) throws AddGradeEmpty, GradeNotAccepted {
        checkGradeFieldNotEmpty(grade);
        checkGradeValue(grade);

        for(Catalog catalog:catalogRepository.find()) {
            if (catalog.getStudentId() != null && catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId().equals(studentUsername)) {
                catalog.setGrade(grade);

                catalogRepository.update(catalog);
            }
        }
    }
    public static void addCourseMaterials(String teacherUsername, String subjectName, String materials) {
        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId()==null) {
                catalog.setCourseMaterials(materials);
                catalogRepository.update(catalog);
                break;
            }
        }
    }

    public static String searchGrade(String teacherUsername, String studentUsername , String subjectName) {
        for (Catalog catalog : catalogRepository.find()) {
            if (catalog.getStudentId() != null && catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId().equals(studentUsername)) {
                return catalog.getGrade();
            }
        }
        return "";
    }

    public static void addAbsence(String teacherUsername, String studentUsername, String subjectName, String absence) throws  AddAbsenceEmpty, AbsenceNotAccepted {

        checkAbsenceFieldNotEmpty(absence);
        checkAbsenceValue(absence);

        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getStudentId()!=null && catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId().equals(studentUsername)) {
                catalog.setAbsence(absence);
                catalogRepository.update(catalog);
                break;
            }
        }
    }

    public static String searchAbsence(String teacherUsername, String studentUsername , String subjectName) {
        for (Catalog catalog : catalogRepository.find()) {
            if (catalog.getStudentId() != null && catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId().equals(studentUsername)) {
                return catalog.getAbsence();
            }
        }
        return "";
    }

    public static void addPresence(String teacherUsername, String studentUsername, String subjectName, String presence) throws AddPresenceEmpty, PresenceNotAccepted {

        checkPresenceFieldNotEmpty(presence);
        checkPresenceValue(presence);

        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getStudentId()!=null && catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId().equals(studentUsername)) {
                catalog.setPresence(presence);
                catalogRepository.update(catalog);
                break;
            }
        }
    }

    public static String searchPresence(String teacherUsername, String studentUsername , String subjectName) {
        for (Catalog catalog : catalogRepository.find()) {
            if (catalog.getStudentId() != null && catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName) && catalog.getStudentId().equals(studentUsername)) {
                return catalog.getPresence();
            }
        }
            return "";
    }

    public static String searchCourseMaterials(String teacherUsername, String subjectName) {
        for (Catalog catalog : catalogRepository.find()) {
            if (catalog.getTeacherId().equals(teacherUsername) && catalog.getSubjectId().equals(subjectName)) {
                return catalog.getCourseMaterials();
            }
        }
        return "";
    }

    public static void addHomeworkSolution(String teacher, String student,String subject,String solution){
        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getStudentId()!=null && catalog.getStudentId().equals(student) && catalog.getTeacherId().equals(teacher) && catalog.getSubjectId().equals(subject)) {
                catalog.setHomeworkSolution(solution);
                catalogRepository.update(catalog);
                break;
            }
        }
    }

    public static String searchHomeworkSolution(String teacher, String student,String subject){
        for(Catalog catalog:catalogRepository.find()) {
            if(catalog.getStudentId()!=null && catalog.getStudentId().equals(student) && catalog.getTeacherId().equals(teacher) && catalog.getSubjectId().equals(subject)) {
                return catalog.getHomeworkSolution();
            }
        }
        return "";
    }

    private static void checkSubjectAddFieldNotEmpty (String subjectName) throws AddSubjectNotTyped {
        if(subjectName.equals(new String("")) )
            throw new AddSubjectNotTyped();
    }

    private static void checkSubjectAlreadyExists (String subjectName) throws SubjectAlreadyAdded {
        for (Catalog catalog : catalogRepository.find()) {
            if (Objects.equals(subjectName, catalog.getSubjectId()))
                throw new SubjectAlreadyAdded();
        }
    }

    private static void checkStudentAddFieldNotEmpty (String studentUsername) throws AddStudentNotTyped {
        if(studentUsername.equals(new String("")) )
            throw new AddStudentNotTyped();
    }

    private static void checkStudentAlreadyExists (String studentUsername, String subjectName) throws StudentAlreadyAdded {
        for (Catalog catalog : catalogRepository.find()) {
            if (Objects.equals(studentUsername, catalog.getStudentId()) && Objects.equals(subjectName, catalog.getSubjectId()))
                throw new StudentAlreadyAdded();
        }
    }

    private static void checkGradeFieldNotEmpty (String grade) throws AddGradeEmpty {
        if(grade.equals(new String("")) )
            throw new AddGradeEmpty();
    }

    private static void checkPresenceFieldNotEmpty (String presence) throws AddPresenceEmpty {
        if(presence.equals(new String("")) )
            throw new AddPresenceEmpty();
    }

    private static void checkAbsenceFieldNotEmpty (String absence) throws AddAbsenceEmpty {
        if(absence.equals(new String("")) )
            throw new AddAbsenceEmpty();
    }

    private static void checkGradeValue (String grade) throws GradeNotAccepted {
        if(Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 10)
            throw new GradeNotAccepted();
    }

    private static void checkPresenceValue (String presence) throws PresenceNotAccepted {
           if (Integer.parseInt(presence) < 0)
               throw new PresenceNotAccepted();

    }

    private static void checkAbsenceValue (String absence) throws AbsenceNotAccepted {
        if (Integer.parseInt(absence) < 0)
                throw new AbsenceNotAccepted();

    }

}
