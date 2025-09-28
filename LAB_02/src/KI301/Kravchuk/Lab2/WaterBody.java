package KI301.Kravchuk.Lab2;

/**
 * Клас WaterBody моделює водойму (озеро/ставок).
 * Містить компоненти: розташування, якість води, популяцію риб.
 * 
 * @author Кравчук Ілля Миколайович
 * @version 1.0
 * @since 2025
 */
import java.io.*;

public class WaterBody {
    private Location location;      // Розташування
    private WaterQuality quality;   // Якість води
    private FishPopulation fishPop; // Популяція риб
    private PrintWriter logWriter;  // Логер

    /**
     * Конструктор за замовчуванням: ініціалізує базові значення.
     * @throws FileNotFoundException якщо не вдається створити лог-файл
     */
    public WaterBody() throws FileNotFoundException {
        this.location = new Location(0.0, 0.0);  // Центр координат
        this.quality = new WaterQuality(100);   // Чиста вода (pH=7, але спрощено)
        this.fishPop = new FishPopulation(100); // 100 риб
        this.logWriter = new PrintWriter(new File("waterbody_log.txt"));
        logAction("Створено водойму за замовчуванням");
    }

    /**
     * Конструктор з параметрами: широта, довгота, рівень забруднення, кількість риб.
     * @param lat широта
     * @param lon довгота
     * @param pollution рівень забруднення (0-100)
     * @param fishCount кількість риб
     * @throws FileNotFoundException якщо не вдається створити лог-файл
     */
    public WaterBody(double lat, double lon, int pollution, int fishCount) throws FileNotFoundException {
        this.location = new Location(lat, lon);
        this.quality = new WaterQuality(pollution);
        this.fishPop = new FishPopulation(fishCount);
        this.logWriter = new PrintWriter(new File("waterbody_log.txt"));
        logAction("Створено водойму з параметрами: lat=" + lat + ", lon=" + lon);
    }

    /**
     * Обчислює об'єм водойми (спрощено: глибина * площа).
     * @return об'єм у куб. м
     */
    public double calculateVolume() {
        double area = 10000; // Спрощена площа
        double depth = quality.getPH() * 10; // Залежить від pH
        double volume = area * depth;
        logAction("Обчислено об'єм: " + volume);
        return volume;
    }

    /**
     * Додає риб до популяції.
     * @param count кількість риб
     */
    public void addFish(int count) {
        fishPop.addFish(count);
        logAction("Додано " + count + " риб");
    }

    /**
     * Вимірює рівень забруднення.
     * @return рівень забруднення (0-100)
     */
    public int measurePollution() {
        int level = quality.getPollutionLevel();
        logAction("Виміряно забруднення: " + level);
        return level;
    }

    /**
     * Змінює розташування.
     * @param lat нова широта
     * @param lon нова довгота
     */
    public void changeLocation(double lat, double lon) {
        location.setLatitude(lat);
        location.setLongitude(lon);
        logAction("Змінено розташування: " + lat + ", " + lon);
    }

    /**
     * Очищує воду (зменшує забруднення на 20%).
     */
    public void cleanWater() {
        quality.reducePollution(20);
        logAction("Очищено воду");
    }

    /**
     * Перевіряє екологічний стан (true якщо забруднення <50).
     * @return true якщо стан добрий
     */
    public boolean isEcoFriendly() {
        boolean status = quality.getPollutionLevel() < 50;
        logAction("Екологічний стан: " + (status ? "добрий" : "поганий"));
        return status;
    }

    /**
     * Геттер для розташування.
     * @return Location об'єкт
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Геттер для якості води.
     * @return WaterQuality об'єкт
     */
    public WaterQuality getQuality() {
        return quality;
    }

    /**
     * Геттер для популяції риб.
     * @return FishPopulation об'єкт
     */
    public FishPopulation getFishPopulation() {
        return fishPop;
    }

    /**
     * Логує дію в файл.
     * @param action опис дії
     */
    private void logAction(String action) {
        logWriter.println(new java.util.Date() + ": " + action);
        logWriter.flush();
    }

    /**
     * Закриває ресурси (лог-файл).
     */
    public void dispose() {
        logWriter.close();
        logAction("Ресурси закрито");
    }
}

// Допоміжні класи
/**
 * Клас Location для координат.
 */
class Location {
    private double latitude, longitude;

    public Location(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public void setLatitude(double lat) { this.latitude = lat; }
    public void setLongitude(double lon) { this.longitude = lon; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

/**
 * Клас WaterQuality для якості води.
 */
class WaterQuality {
    private int pollutionLevel; // 0-100

    public WaterQuality(int level) {
        this.pollutionLevel = Math.max(0, Math.min(100, level));
    }

    public void reducePollution(int amount) {
        pollutionLevel = Math.max(0, pollutionLevel - amount);
    }

    public int getPollutionLevel() { return pollutionLevel; }
    public double getPH() { return 14 - (pollutionLevel / 100.0 * 7); } // Спрощено
}

/**
 * Клас FishPopulation для популяції риб.
 */
class FishPopulation {
    private int fishCount;

    public FishPopulation(int count) {
        this.fishCount = Math.max(0, count);
    }

    public void addFish(int count) {
        fishCount += Math.max(0, count);
    }

    public int getFishCount() { return fishCount; }
}