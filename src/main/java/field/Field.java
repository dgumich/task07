package main.java.field;

import java.util.List;

/**
 * Класс, отвечающий за поле и работу с ним
 */

public class Field {

    //поле с текущим состоянием
    int[][] currentStep;

    //поле со следующим поколением
    int[][] nextStep;

    /**
     * В конструктор передаем List со строками из файла, которые используем для создания массива с текущим поколением.
     * Массив следующего поколения инициализируется пустым массивом
     * @param lines - строки считанные с файла
     */
    public Field(List<String> lines) {

        int height = lines.size();
        int length = lines.get(0).length();
        currentStep = new int[height][length];
        String line;
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);
            for (int j = 0; j < lines.size(); j++) {
                 currentStep[i][j] = (line.charAt(j) == '1') ? 1 : 0;
            }
        }

        this.nextStep = new int[currentStep.length][currentStep[0].length] ;
    }


    /**
     * Метод распечатывает массив с текущим поколением
     */
    public void printField() {

        for (int[] array : this.currentStep) {
            for (int b : array) {
                if (b == 1) {
                    System.out.print(" 0 ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.println("=================================");
    }

    /**
     * @return - метод возвращает строковое представление массива текущего поколения.
     */

    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int[] line : currentStep) {
            for (int number : line) {
                sb.append(number);
            }
            sb.append("\n");

        }
        return sb.toString();
    }

    public int getSize() {
        return currentStep.length;
    }

    public int[][] getCurrentStep() {
        return currentStep;
    }

    public int[][] getNextStep() {
        return nextStep;
    }

    /**
     * Метод обновляет массив с текущим поколением на следующее, а следующее инициализирует пустым массивом
     */
    public void update(){
        this.currentStep = nextStep;
        this.nextStep = new int[currentStep.length][currentStep[0].length];
    }
}
