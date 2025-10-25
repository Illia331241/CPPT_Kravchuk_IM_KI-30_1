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
 * Lab 5 package for variant 12: FunctionCalculator application.
 */
package ki301.kravchuk.lab5;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class <code>FunctionCalculatorApp</code> implements main method for 
 * testing FunctionCalculator class abilities.
 */
public class FunctionCalculatorApp {

    /**
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        FunctionCalculator calculator = new FunctionCalculator();
        double x = Math.PI / 8; // Test value where tan(4x) != 0

        try {
            // Calculate and write to files
            double y = calculator.calculate(x);
            System.out.println("Calculated: For x = " + x + ", y = " + y);

            calculator.writeToTextFile("Result.txt", x, y);
            System.out.println("Written to Result.txt successfully.");

            calculator.writeToBinaryFile("Result.bin", x, y);
            System.out.println("Written to Result.bin successfully.");

            // Read from files and verify
            double[] textResult = calculator.readFromTextFile("Result.txt");
            System.out.println("Read from Result.txt: x = " + textResult[0] + ", y = " + textResult[1]);

            double[] binaryResult = calculator.readFromBinaryFile("Result.bin");
            System.out.println("Read from Result.bin: x = " + binaryResult[0] + ", y = " + binaryResult[1]);

            // Verify correctness
            if (Math.abs(y - textResult[1]) < 1e-10 && Math.abs(y - binaryResult[1]) < 1e-10) {
                System.out.println("Test passed: Results match the calculated value.");
            } else {
                System.out.println("Test failed: Results do not match.");
            }

        } catch (ArithmeticException e) {
            System.err.println("Error: Division by zero - tan(4x) = 0");
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found for reading/writing");
        } catch (IOException e) {
            System.err.println("Error: I/O error occurred - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}