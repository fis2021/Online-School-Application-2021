package org.fis.project.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.project.exceptions.*;
import org.fis.project.model.Catalog;

import static org.fis.project.services.FileSystemService.getPathToFile;

public class CatalogService {

    private static ObjectRepository<Catalog> catalogRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("Catalog.db").toFile())
                .openOrCreate("test", "test");

        catalogRepository = database.getRepository(Catalog.class);
    }

    public static void addCatalog(String teacherId, String studentId, String subjectId){
        catalogRepository.insert(new Catalog(teacherId,studentId,subjectId));
    }

    public static void test(){
        for(Catalog catalog:catalogRepository.find()) {
            System.out.println(catalog.getStudentId());
        }
    }

}
