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

public class HomogeneousAstTest {

    @Test
    void testBasicAst() {
        final String expression = "2 + 2 * 4";
        final String expected = """
            '- OP_ADD -> +
               |- NUM -> 2
               '- OP_MUL -> *
                  |- NUM -> 2
                  '- NUM -> 4
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testConstExpressionAst() {
        final String expression = "2 * e";
        final String expected = """
            '- OP_MUL -> *
               |- NUM -> 2
               '- CONSTANT -> e
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testFunctionExpressionAst() {
        final String expression = "2 + sqrt(4)";
        final String expected = """
            '- OP_ADD -> +
               |- NUM -> 2
               '- FUNCTION -> sqrt
                  '- NUM -> 4
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testFactorialExpressionAst() {
        final String expression = "5!";
        final String expected = """
            '- OP_FACT -> !
               '- NUM -> 5
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testNumber() {
        final String expression = "22";
        final String expected = """
            '- NUM -> 22
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testTwoArgFunction() {
        final String expression = "pow(2.0,2.0)";
        final String expected = """
            '- FUNCTION -> pow
               |- NUM -> 2.0
               '- NUM -> 2.0
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testParenFactorial() {
        final String expression = "(5 + 1)!";
        final String expected = """
            '- OP_FACT -> !
               '- LPAREN -> (
                  |- OP_ADD -> +
                  |  |- NUM -> 5
                  |  '- NUM -> 1
                  '- RPAREN -> )
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    void testParenExpression() {
        final String expression = "(2 + 5) + sin(2.5)";
        final String expected = """
            '- OP_ADD -> +
               |- LPAREN -> (
               |  |- OP_ADD -> +
               |  |  |- NUM -> 2
               |  |  '- NUM -> 5
               |  '- RPAREN -> )
               '- FUNCTION -> sin
                  '- NUM -> 2.5
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testComplicated() {
        final String expression = "2 + sin(1.24) + sqrt(5) + "
            + "pow(4,4) * 22 + sqrt(9)";
        final String expected = """
            '- OP_ADD -> +
               |- OP_ADD -> +
               |  |- OP_ADD -> +
               |  |  |- OP_ADD -> +
               |  |  |  |- NUM -> 2
               |  |  |  '- FUNCTION -> sin
               |  |  |     '- NUM -> 1.24
               |  |  '- FUNCTION -> sqrt
               |  |     '- NUM -> 5
               |  '- OP_MUL -> *
               |     |- FUNCTION -> pow
               |     |  |- NUM -> 4
               |     |  '- NUM -> 4
               |     '- NUM -> 22
               '- FUNCTION -> sqrt
                  '- NUM -> 9
            """;
        final String actual =
            ParsevaUtils.toStringTree(Main.buildMathAstNodeTree(expression));
        assertThat(actual).isEqualTo(expected);
    }


}
