package KI301.Kravchuk.Lab2;

/**
 * Драйвер для тестування класу WaterBody.
 * 
 * @author Кравчук Ілля Миколайович
 * @version 1.0
 * @since 2025
 */
import java.io.FileNotFoundException;

public class WaterBodyApp {
    public static void main(String[] args) {
        try {
            WaterBody lake = new WaterBody(50.45, 30.52, 30, 500); // Київське озеро
            System.out.println("Об'єм: " + lake.calculateVolume());
            lake.addFish(100);
            System.out.println("Риб: " + lake.getFishPopulation().getFishCount());
            System.out.println("Забруднення: " + lake.measurePollution());
            lake.cleanWater();
            System.out.println("Еко-стан: " + lake.isEcoFriendly());
            lake.changeLocation(48.85, 24.03); // Львів
            lake.dispose();
        } catch (FileNotFoundException e) {
            System.err.println("Помилка файлу: " + e.getMessage());
        }
    }
}