package KI301.Kravchuk.Lab6;

/**
 * Клас, що представляє вантаж типу "Ящик" (Box).
 * Імплементує Comparable для порівняння за вагою.
 *
 * @author Kravchuk Illia
 * @version 1.0
 */
public class Box implements Comparable<Box> {
    private double weight;

    /**
     * Конструктор для створення ящика з вказаною вагою.
     *
     * @param weight Вага ящика.
     */
    public Box(double weight) {
        this.weight = weight;
    }

    /**
     * Повертає вагу ящика.
     *
     * @return Вага.
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Box other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "Box [weight=" + weight + "]";
    }
}