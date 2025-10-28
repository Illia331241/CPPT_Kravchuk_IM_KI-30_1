package KI301.Kravchuk.Lab3;

import java.io.FileNotFoundException;

/**
 * Клас Sea — похідний від WaterBody, моделює море.
 * Реалізує інтерфейс Salty.
 *
 * @author Кравчук Ілля Миколайович
 * @version 1.0
 */
public class Sea extends WaterBody implements Salty {
    private double salinity; // у проміле (‰)

    /**
     * Конструктор моря.
     */
    public Sea(double lat, double lon, int pollution, int fishCount, double salinity) throws FileNotFoundException {
        super(lat, lon, pollution, fishCount);
        this.salinity = Math.max(0, Math.min(50, salinity));
        logAction("Створено море з солоністю: " + this.salinity + "‰");
    }

    /**
     * Обчислює об'єм моря (спрощено: площа * середня глибина).
     */
    @Override
    public double calculateVolume() {
        double area = 1000000; // 1000 км²
        double avgDepth = 100 + (salinity * 10); // солоніші — глибші
        double volume = area * avgDepth;
        logAction("Обчислено об'єм моря: " + volume + " м³");
        return volume;
    }

    @Override
    public double getSalinity() {
        return salinity;
    }

    @Override
    public void setSalinity(double salinity) {
        this.salinity = Math.max(0, Math.min(50, salinity));
        logAction("Встановлено солоність: " + this.salinity + "‰");
    }

    /**
     * Додає солону рибу (наприклад, оселедець).
     */
    public void addSaltwaterFish(int count) {
        if (salinity > 20) {
            fishPop.addFish(count);
            logAction("Додано " + count + " солоних риб");
        } else {
            logAction("Недостатньо солоності для солоних риб!");
        }
    }
}