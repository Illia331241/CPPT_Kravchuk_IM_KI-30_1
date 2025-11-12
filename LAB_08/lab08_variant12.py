
import os
import struct
import sys
import math

def writeResTxt(fName, result):
    """
    Записує результат у текстовий файл.
    :param fName: ім'я файлу
    :param result: значення для запису (float)
    """
    try:
        with open(fName, 'w', encoding='utf-8') as f:
            f.write(str(result))
    except Exception as e:
        print(f"Помилка запису у текстовий файл {fName}: {e}")
        sys.exit(1)

def readResTxt(fName):
    """
    Читає результат з текстового файлу.
    :param fName: ім'я файлу
    :return: значення (float) або 0.0 у разі помилки
    """
    result = 0.0
    try:
        if os.path.exists(fName):
            with open(fName, 'r', encoding='utf-8') as f:
                content = f.read().strip()
                result = float(content)
        else:
            raise FileNotFoundError(f"Файл {fName} не знайдено.")
    except FileNotFoundError as e:
        print(e)
    except ValueError:
        print(f"Помилка: у файлі {fName} некоректні дані.")
    except Exception as e:
        print(f"Невідома помилка при читанні {fName}: {e}")
    return result

def writeResBin(fName, result):
    """
    Записує результат у бінарний файл (float32).
    :param fName: ім'я файлу
    :param result: значення (float)
    """
    try:
        with open(fName, 'wb') as f:
            f.write(struct.pack('f', result))
    except Exception as e:
        print(f"Помилка запису у бінарний файл {fName}: {e}")
        sys.exit(1)

def readResBin(fName):
    """
    Читає результат з бінарного файлу.
    :param fName: ім'я файлу
    :return: значення (float) або 0.0 у разі помилки
    """
    result = 0.0
    try:
        if os.path.exists(fName):
            with open(fName, 'rb') as f:
                data = f.read(4)  # float = 4 байти
                if len(data) == 4:
                    result = struct.unpack('f', data)[0]
                else:
                    raise ValueError("Недостатньо даних у бінарному файлі.")
        else:
            raise FileNotFoundError(f"Файл {fName} не знайдено.")
    except FileNotFoundError as e:
        print(e)
    except struct.error:
        print(f"Помилка розпакування бінарних даних з {fName}.")
    except Exception as e:
        print(f"Невідома помилка при читанні {fName}: {e}")
    return result

def calculate(x):
    """
    Обчислює y = sin(x) / tg(4x)
    :param x: вхідне значення (float)
    :return: результат обчислення
    :raises ZeroDivisionError: якщо tg(4x) == 0
    :raises ValueError: якщо x призводить до невизначеності
    """
    if x == 0:
        raise ValueError("x не може дорівнювати 0 (tg(4x) невизначена при 4x = k*pi)")
    
    tan_4x = math.tan(4 * x)
    if abs(tan_4x) < 1e-10:  # близьке до нуля
        raise ZeroDivisionError("Ділення на нуль: tg(4x) ≈ 0")
    
    return math.sin(x) / tan_4x

if __name__ == "__main__":
    try:
        # Ввід даних
        data = float(input("Введіть значення x: "))
        
        # Обчислення
        result = calculate(data)
        print(f"Результат обчислення y = sin(x)/tg(4x) при x = {data}: {result}")

        # Запис у файли
        writeResTxt("textRes.txt", result)
        writeResBin("binRes.bin", result)

        # Читання та перевірка
        print(f"Прочитано з textRes.txt: {readResTxt('textRes.txt')}")
        print(f"Прочитано з binRes.bin: {readResBin('binRes.bin')}")

    except ValueError as e:
        if "invalid literal" in str(e):
            print("Помилка: введено нечислове значення.")
        else:
            print(f"Помилка обчислення: {e}")
        sys.exit(1)
    except ZeroDivisionError as e:
        print(f"Математична помилка: {e}")
        sys.exit(1)
    except Exception as e:
        print(f"Невідома помилка: {e}")
        sys.exit(1)