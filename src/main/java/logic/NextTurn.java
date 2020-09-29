package main.java.logic;


import main.java.field.Field;

/**
 * Класс для обработки хода
 */
public class NextTurn {


    /**
     * Метод для обработки хода. На вход принимает:
     * @param field объект типа Field
     * @param start номер строки с которой надо начать вычисление(входит в интервал)
     * @param stop номер строки до которой происходит вычисление (не входит в интервал)
     */
    public void move(Field field, int start, int stop){
        //получаем ссылки на массивы текущего и следующего поколения поля Field
        int[][] fieldCurrent = field.getCurrentStep();
        int [][] fieldNext = field.getNextStep();
        //проходим массив в заданном диапазоне
        for (int i = start; i < stop; i ++) {
            for (int j = 0; j < fieldCurrent[i].length; j++) {
                //проверяем будет ли существовать клетка в следующем поколении
                //записываем результат в массив со следующим поколением
                if (check(i,j, fieldCurrent)) {
                    fieldNext[i][j] = 1;
                } else {
                    fieldNext[i][j] = 0;
                }
            }
        }
    }

    /**
     * Метод для проверки состояния клетки в следующем поколении. Принимает
     * @param y - номер строки клетки
     * @param x - номер столбца клетки
     * @param field - массив с текущим состоянием
     * @return - возвращает boolean значение
     */
     boolean check (int y, int x, int[][] field) {
         //создаем счетчик живых клеток в массиве 3х3, где наша текущая клетка занимает позицию по центру
        int count = 0;
        //относительные координаты, которые будут использованы при работе с массивом
        int indexX;
        int indexY;
        //проход по нашему массиву 3х3
        for (int i = - 1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                //вычисление относительных координат
                indexY = (y + i + field.length) % field.length;
                indexX = (x + j + field[y].length) % field[y].length;
                count += field[indexY][indexX];
            }
        }
        //вычитание из счетчика текущую клетку
        int currentState = field[y][x];
        count -= currentState;

        //проверка на жизнь
        if (currentState == 0 && count == 3) {
            return true;
        } else return currentState == 1 && (count == 3 || count == 2);

    }


}
