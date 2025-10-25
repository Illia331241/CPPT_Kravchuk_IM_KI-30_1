package KI301.Kravchuk.Lab6;

import java.util.ArrayList;
import java.util.List;

/**
 * Параметризований клас, що реалізує предметну область "Грузова машина".
 * Цей клас є контейнером для вантажів (cargo), де вантажі можуть бути будь-якого типу,
 * що імплементує Comparable для порівняння (наприклад, за вагою).
 * Клас містить методи для додавання, видалення, пошуку мінімального елементу та отримання всіх вантажів.
 *
 * @author Kravchuk Illia
 * @version 1.0
 * @param <T> Тип вантажу, що імплементує Comparable<T>.
 */
public class Truck<T extends Comparable<T>> {
    private List<T> cargos = new ArrayList<>();

    /**
     * Додає вантаж до контейнера (грузової машини).
     *
     * @param cargo Вантаж для додавання.
     */
    public void addCargo(T cargo) {
        cargos.add(cargo);
    }

    /**
     * Видаляє вантаж з контейнера (грузової машини).
     *
     * @param cargo Вантаж для видалення.
     * @return true, якщо вантаж видалено; false, якщо не знайдено.
     */
    public boolean removeCargo(T cargo) {
        return cargos.remove(cargo);
    }

    /**
     * Знаходить мінімальний вантаж у контейнері (за критерієм порівняння Comparable).
     *
     * @return Мінімальний вантаж або null, якщо контейнер порожній.
     */
    public T findMinCargo() {
        if (cargos.isEmpty()) {
            return null;
        }
        T min = cargos.get(0);
        for (T cargo : cargos) {
            if (cargo.compareTo(min) < 0) {
                min = cargo;
            }
        }
        return min;
    }

    /**
     * Повертає список усіх вантажів у контейнері.
     *
     * @return Список вантажів.
     */
    public List<T> getAllCargos() {
        return new ArrayList<>(cargos);
    }
}