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

import static com.google.common.truth.Truth.assertWithMessage;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class TokenTest {

    @Test
    void testTokenNumber() {
        final int numberOfTokenNames =
            TokenUtil.getNumberOfTokenNames();
        final int numberOfTokenTypes =
            TokenTypes.class.getDeclaredFields().length;
        final String message = """
        Number of token types in MathTokenTypes.java and 
        token names in MathTokenUtil.java do not match.
        """;
        assertWithMessage(message)
            .that(numberOfTokenNames)
            .isEqualTo(numberOfTokenTypes);
    }

    @Test
    void testTokenNamesOrder() {
        final Field[] mathTokenTypesFields = TokenTypes.class.getDeclaredFields();
        Stream<Field> stream = Arrays.stream(mathTokenTypesFields);
        final String[] mathTokenTypesNames = stream
            .map(Field::getName)
            .toArray(String[]::new);
        final String [] mathTokenUtilNames = TokenUtil.getAllTokenNames();
        final String message = """
            Names of token types in MathTokenTypes.java and 
            token names in MathTokenUtil.java are not in the same
            order.
            """;

        assertWithMessage(message)
            .that(mathTokenTypesNames)
            .isEqualTo(mathTokenUtilNames);
    }
}
