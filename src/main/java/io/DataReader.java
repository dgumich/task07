package main.java.io;

import java.util.Scanner;

/**
 * Класс для считывания данных с консоли
 */
public class DataReader {

    /**
     * Метод считывает с консоли количество потоков, которые будут обрабатывать поле и возвращает int
     * @return
     */

    public static int readThreadCount() {

        System.out.println("Введите количество потоков: ");

        try(Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        }
    }
}
