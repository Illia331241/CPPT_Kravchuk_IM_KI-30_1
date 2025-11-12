
import os
import struct
import sys

def write_text(filename: str, value: float) -> None:
    try:
        with open(filename, 'w', encoding='utf-8') as f:
            f.write(str(value))
    except Exception as e:
        print(f"Помилка запису (текст): {e}")
        sys.exit(1)

def write_binary(filename: str, value: float) -> None:
    try:
        with open(filename, 'wb') as f:
            f.write(struct.pack('f', value))
    except Exception as e:
        print(f"Помилка запису (бінарний): {e}")
        sys.exit(1)

def read_text(filename: str) -> float:
    if not os.path.exists(filename):
        print(f"Файл {filename} не знайдено.")
        return 0.0
    try:
        with open(filename, 'r', encoding='utf-8') as f:
            return float(f.read().strip())
    except Exception as e:
        print(f"Помилка читання (текст): {e}")
        return 0.0

def read_binary(filename: str) -> float:
    if not os.path.exists(filename):
        print(f"Файл {filename} не знайдено.")
        return 0.0
    try:
        with open(filename, 'rb') as f:
            data = f.read(4)
            if len(data) != 4:
                raise ValueError("Неправильний розмір даних")
            return struct.unpack('f', data)[0]
    except Exception as e:
        print(f"Помилка читання (бінарний): {e}")
        return 0.0