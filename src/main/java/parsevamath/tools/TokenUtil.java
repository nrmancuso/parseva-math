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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This utility class handles token-related tasks.
 */
public final class TokenUtil {

    /**
     * Maps from a token value to name.
     */
    private static final Map<Integer, String> TOKEN_VALUE_TO_NAME;

    static {

        // All TokenTypes must be included here.
        TOKEN_VALUE_TO_NAME = new HashMap<>();
        TOKEN_VALUE_TO_NAME.put(TokenTypes.OP_ADD, "OP_ADD");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.OP_SUB, "OP_SUB");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.OP_MUL, "OP_MUL");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.OP_DIV, "OP_DIV");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.NUM, "NUM");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.ID, "ID");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.WS, "WS");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.NEGATE, "NEGATE");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.FUNCTION, "FUNCTION");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.CONSTANT, "CONSTANT");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.OP_FACT, "OP_FACT");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.LPAREN, "LPAREN");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.RPAREN, "RPAREN");
        TOKEN_VALUE_TO_NAME.put(TokenTypes.COMMA, "COMMA");
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
        final String name = TOKEN_VALUE_TO_NAME.get(id);
        if (name == null) {
            throw new IllegalArgumentException("Token name is not assigned!");
        }
        return name;
    }

    /**
     * Gets the numerical value (token ID) of a given token name.
     *
     * @param name the token name to look up
     * @return token ID
     */
    public static Integer getTokenID(String name) {
        final Map.Entry<Integer, String> tokenEntry =
            TOKEN_VALUE_TO_NAME.entrySet()
            .stream()
            .filter(entry -> name.equals(entry.getValue()))
            .findFirst().orElse(null);

        final Integer tokenID;
        if (tokenEntry != null) {
            tokenID = tokenEntry.getKey();
        }
        else {
            throw new IllegalArgumentException("Token name does not exist "
                + "in 'TOKEN_VALUE_TO_NAME'");
        }

        return tokenID;
    }

    /**
     * Gets the number of named tokens in
     * 'TOKEN_VALUE_TO_NAME' map.
     *
     * @return number of token names
     */
    public static int getNumberOfTokenNames() {
        return TOKEN_VALUE_TO_NAME.size();
    }

    /**
     * Gets entire contents of 'TOKEN_VALUE_TO_NAME'.
     *
     * @return all token names
     */
    public static Collection<String> getAllTokenNames() {
        return TOKEN_VALUE_TO_NAME.values();
    }
}
