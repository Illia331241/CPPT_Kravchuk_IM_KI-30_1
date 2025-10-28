package KI301.Kravchuk.Lab3;

import java.io.*;

/**
 * Абстрактний клас WaterBody моделює водойму (озеро, море тощо).
 * Містить базові компоненти: розташування, якість води, популяцію риб.
 *
 * @author Кравчук Ілля Миколайович
 * @version 1.0
 * @since 2025
 */
public abstract class WaterBody {
    protected Location location;
    protected WaterQuality quality;
    protected FishPopulation fishPop;
    protected PrintWriter logWriter;

    /**
     * Конструктор за замовчуванням.
     * @throws FileNotFoundException якщо не вдається створити лог-файл
     */
    public WaterBody() throws FileNotFoundException {
        this.location = new Location(0.0, 0.0);
        this.quality = new WaterQuality(100);
        this.fishPop = new FishPopulation(100);
        this.logWriter = new PrintWriter(new File("waterbody_log.txt"));
        logAction("Створено абстрактну водойму за замовчуванням");
    }

    /**
     * Конструктор з параметрами.
     */
    public WaterBody(double lat, double lon, int pollution, int fishCount) throws FileNotFoundException {
        this.location = new Location(lat, lon);
        this.quality = new WaterQuality(pollution);
        this.fishPop = new FishPopulation(fishCount);
        this.logWriter = new PrintWriter(new File("waterbody_log.txt"));
        logAction("Створено водойму: lat=" + lat + ", lon=" + lon);
    }

    /**
     * Абстрактний метод — має бути реалізований у підкласах.
     */
    public abstract double calculateVolume();

    public void addFish(int count) {
        fishPop.addFish(count);
        logAction("Додано " + count + " риб");
    }

    public int measurePollution() {
        int level = quality.getPollutionLevel();
        logAction("Виміряно забруднення: " + level);
        return level;
    }

    public void changeLocation(double lat, double lon) {
        location.setLatitude(lat);
        location.setLongitude(lon);
        logAction("Змінено розташування: " + lat + ", " + lon);
    }

    public void cleanWater() {
        quality.reducePollution(20);
        logAction("Очищено воду");
    }

    public boolean isEcoFriendly() {
        boolean status = quality.getPollutionLevel() < 50;
        logAction("Екологічний стан: " + (status ? "добрий" : "поганий"));
        return status;
    }

    // Геттери
    public Location getLocation() { return location; }
    public WaterQuality getQuality() { return quality; }
    public FishPopulation getFishPopulation() { return fishPop; }

    /**
     * Логує дію в файл.
     */
    protected void logAction(String action) {
        logWriter.println(new java.util.Date() + ": " + action);
        logWriter.flush();
    }

    /**
     * Закриває ресурси.
     */
    public void dispose() {
        logWriter.close();
        logAction("Ресурси закрито");
    }
}

// Допоміжні класи
class Location {
    private double latitude, longitude;
    public Location(double lat, double lon) { this.latitude = lat; this.longitude = lon; }
    public void setLatitude(double lat) { this.latitude = lat; }
    public void setLongitude(double lon) { this.longitude = lon; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}

class WaterQuality {
    private int pollutionLevel;
    public WaterQuality(int level) { this.pollutionLevel = Math.max(0, Math.min(100, level)); }
    public void reducePollution(int amount) { pollutionLevel = Math.max(0, pollutionLevel - amount); }
    public int getPollutionLevel() { return pollutionLevel; }
    public double getPH() { return 14 - (pollutionLevel / 100.0 * 7); }
}

class FishPopulation {
    private int fishCount;
    public FishPopulation(int count) { this.fishCount = Math.max(0, count); }
    public void addFish(int count) { fishCount += Math.max(0, count); }
    public int getFishCount() { return fishCount; }
}