package com.fsy.task;

import java.io.File;
import java.net.URL;
import java.util.regex.Pattern;

public class LoadResourceTests {
    public static void main(String[] args) {
        URL url = ClassLoader.getSystemResource("zgtda");
        File file = new File(url.getFile());
        if(file.exists() && file.isDirectory()){
            String [] files = file.list( (File tempFile ,String name) -> {
                return Pattern.compile("[a-z]{2}\\.txt").matcher(name).find();
            });
            return;
        }
        return;


    }
}
