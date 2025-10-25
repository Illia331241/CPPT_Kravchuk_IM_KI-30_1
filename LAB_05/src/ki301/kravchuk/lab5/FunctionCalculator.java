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
 * Lab 5 package for variant 12: FunctionCalculator with file I/O.
 */
package ki301.kravchuk.lab5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class <code>FunctionCalculator</code> calculates the expression y = sin(x) / tan(4x)
 * and provides methods for reading/writing results in text and binary formats.
 */
public class FunctionCalculator {

    /**
     * Calculates the value of y = sin(x) / tan(4x).
     * @param x The input value in radians.
     * @return The calculated y value.
     * @throws ArithmeticException If tan(4x) is zero (division by zero).
     */
    public double calculate(double x) throws ArithmeticException {
        double tan4x = Math.tan(4 * x);
        if (tan4x == 0) {
            throw new ArithmeticException("Division by zero: tan(4x) = 0");
        }
        return Math.sin(x) / tan4x;
    }

    /**
     * Writes the calculation result to a text file.
     * @param fileName The name of the output text file.
     * @param x The input value.
     * @param y The calculated value.
     * @throws FileNotFoundException If the file cannot be created or written.
     */
    public void writeToTextFile(String fileName, double x, double y) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("x = " + x);
            writer.println("y = " + y);
        }
    }

    /**
     * Writes the calculation result to a binary file.
     * @param fileName The name of the output binary file.
     * @param x The input value.
     * @param y The calculated value.
     * @throws IOException If an I/O error occurs.
     */
    public void writeToBinaryFile(String fileName, double x, double y) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            dos.writeDouble(x);
            dos.writeDouble(y);
        }
    }

    /**
     * Reads the calculation result from a text file.
     * @param fileName The name of the input text file.
     * @return An array [x, y] of doubles read from the file.
     * @throws FileNotFoundException If the file is not found.
     * @throws IOException If an I/O error occurs.
     */
    public double[] readFromTextFile(String fileName) throws FileNotFoundException, IOException {
        double[] result = new double[2];
        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(fileName))) {
            scanner.nextLine(); // Skip "x = "
            result[0] = Double.parseDouble(scanner.nextLine().replace("y = ", "").trim());
            result[1] = Double.parseDouble(scanner.nextLine().trim());
        }
        return result;
    }

    /**
     * Reads the calculation result from a binary file.
     * @param fileName The name of the input binary file.
     * @return An array [x, y] of doubles read from the file.
     * @throws IOException If an I/O error occurs.
     */
    public double[] readFromBinaryFile(String fileName) throws IOException {
        double[] result = new double[2];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            result[0] = dis.readDouble();
            result[1] = dis.readDouble();
        }
        return result;
    }
}