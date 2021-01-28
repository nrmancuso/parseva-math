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
 * This class is used to decouple the token types from the generated ANTLR
 * code.
 */
public final class TokenTypes {

    /**
     * This token is the addition operator.
     */
    public static final int OP_ADD = 0;

    /**
     * This token is the subtraction operator.
     */
    public static final int OP_SUB = 1;

    /**
     * This token is the multiplication operator.
     */
    public static final int OP_MUL = 2;

    /**
     * This token is the division operator.
     */
    public static final int OP_DIV = 3;

    /**
     * This token is a numerical expression.
     */
    public static final int NUM = 4;

    /**
     * This token is an identifier.
     */
    public static final int ID = 5;

    /**
     * This token is for whitespace.
     */
    public static final int WS = 6;

    /**
     * This is the negation node token.
     */
    public static final int NEGATE = 7;

    /**
     * This is the method (function) node token.
     */
    public static final int FUNCTION = 8;

    /**
     * This is the constant node token.
     */
    public static final int CONSTANT = 9;

    /**
     * This is the factorial node token.
     */
    public static final int FACTORIAL = 10;

    /**
     * This is the factorial node token.
     */
    public static final int LPAREN = 11;

    /**
     * This is the factorial node token.
     */
    public static final int RPAREN = 12;

    private TokenTypes() {
    }
}
