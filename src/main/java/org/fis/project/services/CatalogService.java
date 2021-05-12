package org.fis.project.services;

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
        catalogRepository.insert(c);;
    }

    public static void test(){
        for(Catalog catalog:catalogRepository.find()) {
            System.out.println(catalog.getSubjectId());
        }
    }

    public static LinkedList<String> teacherSubjects(String teacherUsername){

        LinkedList<String> teacherSubject =new LinkedList<String>();

        for(Catalog catalog:catalogRepository.find()){
            if(catalog.getTeacherId().equals(teacherUsername)){
                teacherSubject.add(catalog.getSubjectId());
            }
        }

        return teacherSubject;
    }

}
