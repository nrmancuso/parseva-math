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
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.Vocabulary;
import org.junit.jupiter.api.Test;

import parsevamath.tools.grammar.MathParser;

public class TokenUtilTest {

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
        final List<String> mathTokenTypesNames =
            stream.map(Field::getName)
                .collect(Collectors.toList());
        final List<String> tokenUtilTokenNames =
            new ArrayList<>(TokenUtil.getAllTokenNames());
        final String message = """
            Names of token types in MathTokenTypes.java and 
            token names in MathTokenUtil.java are not in the same
            order.
            """;

        assertWithMessage(message)
            .that(mathTokenTypesNames)
            .containsExactlyElementsIn(tokenUtilTokenNames);
    }

    @Test
    void testAllTokensFromParserAreInTokenTypes() {
        final Vocabulary parserTokenTypes = MathParser.VOCABULARY;
        final List<String> tokenUtilTokenNames =
            new ArrayList<>(TokenUtil.getAllTokenNames());

        // Start at 5, since first five tokens are null
        for (int i = 5; i < parserTokenTypes.getMaxTokenType(); i++) {
            final String symbolicName = parserTokenTypes.getSymbolicName(i);
            final String message = "'TOKEN_VALUE_TO_NAME' does not contain "
                + parserTokenTypes.getSymbolicName(i);
            assertWithMessage(message)
                .that(tokenUtilTokenNames.contains(symbolicName))
                .isTrue();
        }
    }

    @Test
    void testAllTokensFromTokenTypesAreInParser() {
        final Vocabulary parserTokenTypes = MathParser.VOCABULARY;
        final List<String> tokenUtilTokenNames =
            new ArrayList<>(TokenUtil.getAllTokenNames());

        tokenUtilTokenNames.forEach(name -> {
            final int tokenType = TokenUtil.getTokenID(name);
            assertWithMessage("")
                .that(parserTokenTypes.getSymbolicName(tokenType))
                .isNotNull();
        });
    }

    @Test
    void testUndefinedTokenName() {
        final int undefinedTokenID = Integer.MAX_VALUE;
        final String expected = "Token name is not defined for ID: " + undefinedTokenID;

        final IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
            () -> TokenUtil.getTokenName(undefinedTokenID));

        assertThat(actual)
            .hasMessageThat()
            .isEqualTo(expected);
    }

    @Test
    void testUndefinedTokenID() {
        final String undefinedTokenName = "UndefinedTokenName";
        final String expected = "Token ID is not defined for name: " + undefinedTokenName;

        final IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
            () -> TokenUtil.getTokenID(undefinedTokenName));

        assertThat(actual)
            .hasMessageThat()
            .isEqualTo(expected);
    }
}
