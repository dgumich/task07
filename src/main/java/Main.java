package main.java;


import main.java.logic.GameOfLife;

public class Main {
    public static void main(String[] args) {


        // создаем экземпляр игры и запускаем ее
        GameOfLife gameOfLife = new GameOfLife(args[0], args[1], args[2]);
        gameOfLife.start();

    }

}
