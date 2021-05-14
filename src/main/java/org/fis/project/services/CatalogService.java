package org.fis.project.services;

import javafx.util.Pair;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.project.exceptions.*;
import org.fis.project.model.Catalog;

import javax.print.DocFlavor;
import java.util.LinkedList;

import static org.fis.project.services.FileSystemService.getPathToFile;

public class CatalogService {

    private static ObjectRepository<Catalog> catalogRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("Catalog.db").toFile())
                .openOrCreate("test", "test");

        catalogRepository = database.getRepository(Catalog.class);
    }

    public static void addTeacher_Subject(String teacherUsername,String teacherSubject){
        Catalog c = new Catalog(teacherUsername);
        c.setSubjectId(teacherSubject);
        catalogRepository.insert(c);
    }

    public static void addTeacher_Student_Subject(String teacherUsername,String studentUsername,String subjectName){
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
}
