package KI301.Kravchuk.Lab3;

import java.io.FileNotFoundException;

/**
 * Драйвер для тестування класу Sea.
 *
 * @author Кравчук Ілля Миколайович
 */
public class SeaApp {
    /**
     * Головний метод.
     */
    public static void main(String[] args) {
        try {
            Sea blackSea = new Sea(45.0, 35.0, 40, 10000, 18.0); // Чорне море
            System.out.println("Об'єм моря: " + blackSea.calculateVolume() + " м³");
            System.out.println("Солоність: " + blackSea.getSalinity() + "‰");
            System.out.println("Риб: " + blackSea.getFishPopulation().getFishCount());

            blackSea.addSaltwaterFish(500);
            blackSea.cleanWater();
            System.out.println("Еко-стан: " + (blackSea.isEcoFriendly() ? "Добрий" : "Поганий"));

            blackSea.setSalinity(35.0); // як Середземне море
            blackSea.addSaltwaterFish(300);

            blackSea.changeLocation(40.0, 25.0);
            blackSea.dispose();

        } catch (FileNotFoundException e) {
            System.err.println("Помилка логування: " + e.getMessage());
        }
    }
}