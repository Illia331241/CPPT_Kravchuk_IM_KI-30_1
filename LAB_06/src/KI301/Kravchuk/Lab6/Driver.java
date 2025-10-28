package KI301.Kravchuk.Lab6;

import java.util.List;

/**
 * Програма-драйвер для тестування класу Truck.
 * Створює екземпляри двох різних класів вантажів (Box і Pallet) і додає їх до контейнера Truck.
 *
 * @author Kravchuk Illia
 * @version 1.0
 */
public class Driver {
    public static void main(String[] args) {
        // Створення контейнера для вантажів типу Box (параметризований)
        Truck<Box> boxTruck = new Truck<>();

        // Створення екземплярів класу Box
        Box box1 = new Box(10.5);
        Box box2 = new Box(5.2);

        // Додавання до контейнера
        boxTruck.addCargo(box1);
        boxTruck.addCargo(box2);

        // Вивід усіх вантажів
        List<Box> allBoxes = boxTruck.getAllCargos();
        System.out.println("All boxes: " + allBoxes);

        // Пошук мінімального вантажу
        Box minBox = boxTruck.findMinCargo();
        System.out.println("Min box: " + minBox);

        // Видалення вантажу
        boxTruck.removeCargo(box1);
        System.out.println("After removal: " + boxTruck.getAllCargos());

        // Створення контейнера для вантажів типу Pallet (параметризований)
        Truck<Pallet> palletTruck = new Truck<>();

        // Створення екземплярів класу Pallet
        Pallet pallet1 = new Pallet(20.0);
        Pallet pallet2 = new Pallet(15.3);

        // Додавання до контейнера
        palletTruck.addCargo(pallet1);
        palletTruck.addCargo(pallet2);

        // Вивід усіх вантажів
        List<Pallet> allPallets = palletTruck.getAllCargos();
        System.out.println("All pallets: " + allPallets);

        // Пошук мінімального вантажу
        Pallet minPallet = palletTruck.findMinCargo();
        System.out.println("Min pallet: " + minPallet);

        // Видалення вантажу
        palletTruck.removeCargo(pallet1);
        System.out.println("After removal: " + palletTruck.getAllCargos());
    }
}