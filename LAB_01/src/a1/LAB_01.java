package a1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class LAB_01 {

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // розмір матриці
        System.out.print("Введіть розмір квадратної матриці: ");
        int size = scanner.nextInt();
        
        // символ-заповнювача
        System.out.print("Введіть символ-заповнювач: ");
        String input = scanner.next();
        
        // Перевірка символу-заповнювача
        if (input.length() != 1) {
            System.out.println("Помилка: потрібно ввести рівно один символ!");
            scanner.close();
            return;
        }
        
        char fillChar = input.charAt(0);
        scanner.close();
        
        // Генерація зубчастого масиву
        char[][] jaggedArray = generateJaggedArray(size, fillChar);
        
        // Виведення масиву на екран
        System.out.println("\nЗгенерований зубчатий масив:");
        printArray(jaggedArray);
        
        // Запис масиву у файл
        writeArrayToFile(jaggedArray, "output.txt");
        
        System.out.println("Масив також записано у файл output.txt");
    }
    
    
    private static char[][] generateJaggedArray(int size, char fillChar) {
        char[][] array = new char[size][size]; // Тепер це квадратна матриця
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // чи поточна позиція належить до заштрихованої області
                boolean isShaded;
                
                if (i < size / 2) {
                    // Верхня половина матриці
                    if (j < size / 2) {
                        // Ліва верхня частина - заштрихована
                        isShaded = true;
                    } else {
                        // Права верхня частина - не заштрихована
                        isShaded = false;
                    }
                } else {
                    // Нижня половина матриці
                    if (j < size / 2) {
                        // Ліва нижня частина - не заштрихована
                        isShaded = false;
                    } else {
                        // Права нижня частина - заштрихована
                        isShaded = true;
                    }
                }
                
                // Заповнюємо символом або пробілом
                array[i][j] = isShaded ? fillChar : ' ';
            }
        }
        
        return array;
    }
    
    
    private static void printArray(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
    
    
    private static void writeArrayToFile(char[][] array, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    writer.write(array[i][j]);
                }
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Помилка при записі у файл: " + e.getMessage());
        }
    }
}