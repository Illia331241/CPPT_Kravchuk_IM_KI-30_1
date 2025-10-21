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
import java.io.PrintWriter;

/**
 * Class <code>FunctionCalculator</code> calculates the expression y = sin(x) / tan(4x).
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
     * Writes the calculation result to a file.
     * @param fileName The name of the output file.
     * @param x The input value.
     * @param y The calculated value.
     * @throws FileNotFoundException If the file cannot be created or written.
     */
    public void writeResultToFile(String fileName, double x, double y) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("x = " + x);
            writer.println("y = " + y);
        }
    }
}