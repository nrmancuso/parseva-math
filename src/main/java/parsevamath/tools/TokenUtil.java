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

/**
 * This utility class handles token-related tasks.
 */
public final class TokenUtil {

    /**
     * Maps from a token value to name.
     */
    private static final String[] TOKEN_VALUE_TO_NAME;

    static {

        // These tokens MUST be in the same order as the declared fields of
        // TokenTypes.
        TOKEN_VALUE_TO_NAME = new String[] {
            "OP_ADD", "OP_SUB", "OP_MUL", "OP_DIV",
            "NUM", "ID", "WS", "NEGATE", "FUNCTION",
            "CONSTANT", "FACTORIAL", "LPAREN",
            "RPAREN",
        };
    }

    /**
     * Prevent instantiation.
     */
    private TokenUtil() {
    }

    /**
     * Returns the name of a token for a given ID.
     *
     * @param id the ID of the token name to get
     * @return a token name
     * @throws IllegalArgumentException when id is not valid
     */
    public static String getTokenName(int id) {
        if (id > TOKEN_VALUE_TO_NAME.length - 1) {
            throw new IllegalArgumentException(
                "Token value is greater than number of tokens!");
        }
        final String name = TOKEN_VALUE_TO_NAME[id];
        if (name == null) {
            throw new IllegalArgumentException("Token name is not assigned!");
        }
        return name;
    }

    /**
     * Gets the number of named tokens in
     * 'TOKEN_VALUE_TO_NAME' array.
     *
     * @return number of token names
     */
    public static int getNumberOfTokenNames() {
        return TOKEN_VALUE_TO_NAME.length;
    }

    /**
     * Gets entire contents of 'TOKEN_VALUE_TO_NAME'.
     *
     * @return all token names
     */
    public static String[] getAllTokenNames() {
        return TOKEN_VALUE_TO_NAME;
    }
}
