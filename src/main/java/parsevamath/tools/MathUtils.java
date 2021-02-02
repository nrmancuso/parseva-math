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

import java.util.Arrays;
import java.util.List;

/**
 * Utility methods for mathematical operations.
 */
public final class MathUtils {

    /** Literal names from MathParser. */
    private static final List<String> LITERAL_NAMES;

    /** Symbolic names from MathParser. */
    private static final List<String> SYMBOLIC_NAMES;

    static {
        LITERAL_NAMES =
            Arrays.asList(
                null, "'e'", "'E'", "'pi'", "'PI'",
                "'+'", "'-'", "'*'", "'/'", "'!'",
                "'('", "')'", null, null, null,
                "','"
            );

        SYMBOLIC_NAMES =
            Arrays.asList(
                null, null, null, null, null,
                "OP_ADD", "OP_SUB", "OP_MUL",
                "OP_DIV", "OP_FACT", "LPAREN",
                "RPAREN", "NUM", "ID", "WS",
                "COMMA", "NEGATE", "FUNCTION",
                "CONSTANT"
            );
    }

    /**
     * Prevent instantiation.
     */
    private MathUtils() {
    }

    /**
     * Gets all symbolic names that should be in MathParser.
     *
     * @return names of all symbolic names in parser
     */
    public static String[] getSymbolicNames() {
        return SYMBOLIC_NAMES.toArray(new String[0]);
    }

    /**
     * Gets all literal names that should be in MathParser.
     *
     * @return names of all literal names in parser
     */
    public static String[] getLiteralNames() {
        return LITERAL_NAMES.toArray(new String[0]);
    }

    /**
     * This method calculates the factorial of a given value.
     * Since parseva-math deal exclusively with doubles,
     * this method also accepts and returns a double.
     *
     * @param value value to calculate the factorial of
     * @return factorial of value
     */
    public static Double getFactorial(Double value) {
        double factorial = 1.0;
        for (int i = 1; i <= value; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
