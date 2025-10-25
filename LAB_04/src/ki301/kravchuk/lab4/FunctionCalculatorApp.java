/****************************************************************************
 * Copyright (c) 2025 Kravchuk I.M. All Rights Reserved.
 * 
 * This program and the accompanying materials are made available under the terms
 * of the Academic Free License v. 3.0 which accompanies this distribution, and is
 * available at https://opensource.org/license/afl-3-0-php/
 * 
 * SPDX-License-Identifier: AFL-3.0
 ****************************************************************************/

/**
 * Lab 4 package for variant 12: FunctionCalculator.
 */
package ki301.kravchuk.lab4;

import java.io.FileNotFoundException;

/**
 * Class <code>FunctionCalculatorApp</code> implements main method for 
 * FunctionCalculator class abilities demonstration.
 */
public class FunctionCalculatorApp {

    /**
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            FunctionCalculator calculator = new FunctionCalculator();
            double x = Math.PI / 8;  // Example x where tan(4x) != 0
            double y = calculator.calculate(x);
            System.out.println("For x = " + x + ", y = " + y);
            calculator.writeResultToFile("Result.txt", x, y);
        } catch (ArithmeticException e) {
            System.err.println("Error: Division by zero - tan(4x) = 0");
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found for writing");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}