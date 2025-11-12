0
from functions import SinOverTan4x, write_text, write_binary, read_text, read_binary

def main():
    print("=== Обчислення y = sin(x) / tg(4x) ===")
    
    try:
        x = float(input("Введіть x: ").strip())
    except ValueError:
        print("Помилка: введіть число!")
        return

    func = SinOverTan4x()

    try:
        result = func.calculate(x)
        print(f"{func} при x = {x}: {result}")
        
        write_text("textRes.txt", result)
        write_binary("binRes.bin", result)
        
        print(f"З textRes.txt: {read_text('textRes.txt')}")
        print(f"З binRes.bin: {read_binary('binRes.bin')}")

    except (ValueError, ZeroDivisionError) as e:
        print(f"Помилка обчислення: {e}")

if __name__ == "__main__":
    main()