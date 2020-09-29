package main.java.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс для считывания поля из файлы
 */
public class FieldReader {


    /**
     * Метод получает на вход путь к файлу, считывает из него строки и возвращает List из строк.
     * @param path - путь к файлу, типа String
     * @return - List<String> - лист со строками из файла.
     */
    public static List<String> getField (String path)  {

        List<String> result = null;
        try {
            result = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
