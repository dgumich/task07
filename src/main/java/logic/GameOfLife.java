package main.java.logic;

import main.java.field.Field;
import main.java.io.DataReader;
import main.java.io.FieldReader;
import main.java.io.FieldWriter;


import java.util.List;

/**
 * Игра с логикой программы
 */
public class GameOfLife {

    String pathStart;
    String pathEnd;
    int moveCount;

    //В конструктор передаем пути к файлам для считывания и записи поля, а также количество поколений
    public GameOfLife(String pathStart, String pathEnd, String moveCount) {
        this.pathStart = pathStart;
        this.pathEnd = pathEnd;
        this.moveCount = Integer.parseInt(moveCount);
    }

    /**
     * Метод запускает логику игры
     */
    public void start() {

        //засекает время начала
        Long timeStart = System.nanoTime();

        //считывает количество потоков, которые будут работать с полем
        int threadCount = DataReader.readThreadCount();

        //создает поле, считывая его из файла
        List<String> array = FieldReader.getField(this.pathStart);
        Field field = new Field(array);

        //создает объект с логикой потока/потоков и запускает работу
        ThreadLogic threadLogic = new ThreadLogic();

        if (threadCount > field.getSize()) {
            System.out.println("Число потоков больше чем число строк, поэтому на кажду строку будет запущено по потоку");
            threadLogic.startGame(field, moveCount, field.getSize());
        } else {
            threadLogic.startGame(field, moveCount, threadCount);
        }

        //записывает итоговый результат в файл
        FieldWriter.writeField(pathEnd, field.toString());


        //фиксирует время окончания работы и выводит информацию в консоль
        Long timeFinish = System.nanoTime();
        System.out.println("Время работы программы с " + threadCount + " потоком/потоками составило " + (timeFinish - timeStart) / 1_000_000 + " мс.");

    }


}
