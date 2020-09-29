package main.java.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FieldWriter {


    public static void writeField(String path, String field) {


        try {
            Files.write(Paths.get(path), field.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
