/*
 * Copyright (c) parseva-math  2021.
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */

package parsevamath.tools;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class ArithmeticTest {

    @Test
    void testAddition() {
        final String expression = "2 + 2";
        final Double expected = 4.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testSubtraction() {
        final String expression = "2 - 2";
        final Double expected = 0.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testDivision() {
        final String expression = "2 / 2";
        final Double expected = 1.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testMultiplication() {
        final String expression = "2 * 3";
        final Double expected = 6.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testSquareRoot() {
        final String expression = "sqrt(7)";
        final Double expected = 2.6457513110645907;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testSin() {
        final String expression = "sin(7)";
        final Double expected = StrictMath.sin(7.0);
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testCompoundExpression() {
        final String expression = "sqrt(7) + (2*3*6) + sin(32)";
        final Double expected = 2.6457513110645907 + 2*3*6 + StrictMath.sin(32.0);
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testNegate() {
        final String expression = "2 + -1";
        final Double expected = 1.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testDivisionByZero() {
        final String expression = "0/0";
        final Double expected = Double.NaN;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testAbsoluteValue() {
        final String expression = "abs(-30.0)";
        final Double expected = 30.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testNumber() {
        final String expression = "42.0";
        final Double expected = 42.0;
        final Double actual = Main.evaluate(expression);
        assertThat(expected).isEqualTo(actual);
    }
}
