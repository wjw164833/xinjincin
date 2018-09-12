package com.fsy.task.util;

import com.fsy.task.APIController;
import com.fsy.task.domain.ImportUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchoolCodeImportUtil {
    public static void importSchoolCodeMap(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        String line = null;
        while((line = br.readLine()) != null){
            APIController.schoolCodeMap.put(line.split(",")[0] , line.split(",")[1]) ;
        }
    }
}
