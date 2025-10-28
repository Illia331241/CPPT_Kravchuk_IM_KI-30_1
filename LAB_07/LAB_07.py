##################################################################################
# Copyright (c) 2023 Lviv Polytechnic National University. All Rights Reserved.
#
# This program and the accompanying materials are made available under the terms
# of the Academic Free License v. 3.0 which accompanies this distribution, and is
# available at https://opensource.org/license/afl-3-0-php/
#
# SPDX-License-Identifier: AFL-3.0
##################################################################################

import sys

def main():
    """
    Програма генерує зубчатий список (jagged list), що містить лише заштриховані
    області квадратної матриці за варіантом 12.
    Матриця має вигляд:
        33330000
        33330000
        33330000
        33330000
        00003333
        00003333
        00003333
        00003333
    """
    
    # Ввід розміру матриці
    try:
        n = int(input("Введіть розмір квадратної матриці: "))
        if n <= 0:
            print("Розмір матриці має бути додатнім числом!")
            sys.exit(1)
    except ValueError:
        print("Помилка: введіть ціле додатне число!")
        sys.exit(1)

    # Ввід символу-заповнювача
    filler = input("Введіть символ-заповнювач: ").strip()
    
    if len(filler) == 0:
        print("Не введено символ-заповнювач")
        sys.exit(1)
    elif len(filler) > 1:
        print("Забагато символів-заповнювачів")
        sys.exit(1)
    
    # Ініціалізація зубчатого списку
    jagged_list = []
    
    # Генерація матриці за варіантом 12
    mid = n // 2  # середина матриці
    
    for i in range(n):
        row = []
        for j in range(n):
            # Умова заштрихованості:
            # - верхня половина (i < mid): ліві 4 стовпці (j < 4)
            # - нижня половина (i >= mid): праві 4 стовпці (j >= n-4)
            if (i < mid and j < 4) or (i >= mid and j >= n - 4):
                row.append(filler)
            # Інакше — порожнє місце (не додаємо)
        if row:  # додаємо тільки непорожні рядки
            jagged_list.append(row)
    
    # Виведення сформованого зубчатого списку
    print("\nСформований зубчатий список:")
    for row in jagged_list:
        print(' '.join(row))
    
    # Додатковий вивід у вигляді матриці (для наочності)
    print("\nВізуалізація заштрихованих областей:")
    for i in range(n):
        for j in range(n):
            if (i < mid and j < 4) or (i >= mid and j >= n - 4):
                print(filler, end="")
            else:
                print("0", end="")
        print()

if __name__ == "__main__":
    main()