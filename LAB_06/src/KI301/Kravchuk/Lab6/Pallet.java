package KI301.Kravchuk.Lab6;

/**
 * Клас, що представляє вантаж типу "Палета" (Pallet).
 * Імплементує Comparable для порівняння за вагою.
 *
 * @author Kravchuk Illia
 * @version 1.0
 */
public class Pallet implements Comparable<Pallet> {
    private double weight;

    /**
     * Конструктор для створення палети з вказаною вагою.
     *
     * @param weight Вага палети.
     */
    public Pallet(double weight) {
        this.weight = weight;
    }

    /**
     * Повертає вагу палети.
     *
     * @return Вага.
     */
    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Pallet other) {
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "Pallet [weight=" + weight + "]";
    }
}