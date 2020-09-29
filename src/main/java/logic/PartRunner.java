package main.java.logic;

import main.java.field.Field;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Класс, который отвечает за обработку части массива в потоке.
 */

public class PartRunner implements Runnable {

    int start;
    int stop;
    NextTurn nextTurn;
    Field field;
    CyclicBarrier cb;

    PartRunner(Field field, int start, int stop, NextTurn nextTurn, CyclicBarrier cb){
        this.start = start;
        this.stop = stop;
        this.field = field;
        this.nextTurn = nextTurn;
        this.cb = cb;
    }


    @Override
    public void run() {
        //запускаем поток и ждем возле барьера, когда его достигнут остальные потоки.
        try{
            cb.await();
            //обрабатываем заданное участок массива
            nextTurn.move(field, start, stop);
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }


}
