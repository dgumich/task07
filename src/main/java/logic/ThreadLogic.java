package main.java.logic;

import main.java.field.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Класс с логикой запуска потока/потоков
 */
public class ThreadLogic {


    /**
     * Метод получает на вход
     * @param field - поле
     * @param moveCount - количество поколений
     * @param threadCount - количество потоков
     * И запускает заданное количество потоков на данном поле.
     */
    public void startGame(Field field, int moveCount, int threadCount){

        //создается объект с логикой обработки поля
        NextTurn nextTurn = new NextTurn();

        //создается барьер
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadCount);

        //создаются объекты, которые будут обрабатывает поле в потоках
        ArrayList<PartRunner> runnable = doRunners(field, threadCount, nextTurn, cyclicBarrier);

        //создается пул потоков
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        //запускается выполнение
        run(field, moveCount, runnable,executorService);


    }

    /**
     * Метод получает на вход
     * @param fieldLength длину поля
     * @param threadCount количество потоков
     * @return и возвращает массив с int, в котором указывает шаг обработки поля
     */
    public int[] intervals(int fieldLength, int threadCount) {

        int[] result = new int[threadCount];
        //находим целую часть и остаток от деления длины поля на количество потоков
        int intPart = fieldLength / threadCount;
        int fracPart = fieldLength % threadCount;
        //помещаем целую часть как основной шаг в массив с реузльтатами
        Arrays.fill(result, intPart);
        //добавляем +1 к шагу на протяжении остатка от деления
        Arrays.fill(result,0, fracPart, (intPart + 1));
        return result;
    }

    /**
     * Метод создает заданное количество обработчиков полей.
     * @param field поле, с которым будут работать обработчики
     * @param threadCount - количество обработчиков, равное количеству потоков
     * @param nextTurn - логика хода
     * @param cyclicBarrier - барьер
     * @return возвращает ArrayList с обработчиками
     */
    public ArrayList<PartRunner> doRunners(Field field, int threadCount, NextTurn nextTurn, CyclicBarrier cyclicBarrier) {

        ArrayList<PartRunner> result = new ArrayList<>();
        //первый обработчик начинает с начала массива - 0 поля
        int start = 0;
        //получаем массив с шагами
        int[] steps = intervals(field.getSize(), threadCount);
        int stop;
        for (int i = 0; i < threadCount; i++) {
            //конец интервала находим как сумма начала и шага
            stop = start + steps[i];
            //создаем обработчик и помещаем в ArrayList
            PartRunner partRunner = new PartRunner(field, start, stop, nextTurn, cyclicBarrier);
            start = stop;
            result.add(partRunner);
        }
        return result;

    }

    /**
     * Метод запускает потоки на выполнение. На вход получает
     * @param field поле на котором будет произведена операция
     * @paпram moveCount - количество поколений
     * @param runnable - ArrayList с обработчиками полей
     * @param executorService - пул с потоками
     */
    public void run(Field field, int moveCount, ArrayList<PartRunner> runnable, ExecutorService executorService) {

        //цикл, который повторяется количество поколений
        for (int i = 0; i < moveCount; i++) {

            //создается массив с результатом запуска потока
            ArrayList<Future> futures = new ArrayList<>();
            for (PartRunner partRunner : runnable) {
                //запускается поток, передачей в него обработчика поля
                futures.add(executorService.submit(partRunner));
            }

            //проверяется, что потоки завершили работу
            for (Future future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            //массив с текущим состоянием обновляется и выводится в консоль
            field.update();
            field.printField();
        }

        executorService.shutdown();

    }

}
