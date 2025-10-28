package KI301.Kravchuk.Lab3;

/**
 * Інтерфейс для солоних водойм (море, океан).
 *
 * @author Кравчук Ілля Миколайович
 */
public interface Salty {
    /**
     * Повертає рівень солоності у проміле (‰).
     * @return солоність
     */
    double getSalinity();

    /**
     * Встановлює рівень солоності.
     * @param salinity нове значення (0-50‰)
     */
    void setSalinity(double salinity);
}