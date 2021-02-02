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

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.junit.jupiter.api.Test;

import parsevamath.tools.MathUtils;
import parsevamath.tools.grammar.MathLexer;
import parsevamath.tools.grammar.MathParser;

class MathParserTest {

    /**
     * Gets a new MathParser for use in testing.
     *
     * @return new instance of MathParser
     */
    private static MathParser getMathParser(String expression) {
        final CharStream codePointCharStream = CharStreams.fromString(expression);
        final MathLexer lexer = new MathLexer(codePointCharStream);
        final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        return new MathParser(tokenStream);
    }

    @Test
    void testGetTokenNames() {
        final MathParser parser = getMathParser("8 * 8");
        final String[] actual = parser.getTokenNames();
        final String[] expected = {
            "<INVALID>",
            "'e'", "'E'", "'pi'", "'PI'",
            "'+'", "'-'", "'*'", "'/'",
            "'!'", "'('", "')'", "NUM",
            "ID", "WS", "','", "NEGATE",
            "FUNCTION", "CONSTANT"
        };

        assertWithMessage("Token names from MathParser do not match.")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void testGetVocabulary() {
        final MathParser parser = getMathParser("8 * 8");
        final Vocabulary actual = parser.getVocabulary();
        final Vocabulary expected =
            new VocabularyImpl(MathUtils.getLiteralNames(), MathUtils.getSymbolicNames());

        assertWithMessage("Parser vocabulary and vocabulary"
            + " in MathUtils max token value does not match.")
            .that(actual.getMaxTokenType())
            .isEqualTo(expected.getMaxTokenType());

        assertWithMessage("Parser vocabulary and vocabulary in MathUtils "
            + "symbolic names do not match.")
            .that(actual.getSymbolicName(5))
            .isEqualTo(expected.getSymbolicName(5));
    }

    @Test
    void testGetGrammarFileName() {
        final MathParser parser = getMathParser("8 * 8");
        final String actual = parser.getGrammarFileName();
        final String expected = "Math.g4";

        assertWithMessage("Unexpected grammar file name!")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void testGetRuleNames() {
        final MathParser parser = getMathParser("8 * 8");
        final String[] actual = parser.getRuleNames();
        final String[] expected = {
            "compilationUnit",
            "expr",
            "constant"
        };

        assertWithMessage("Rule names for expression do not match.")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    void testGetSerializedATN() {
        final MathParser parser = getMathParser("8 * 8");
        final String actual = parser.getSerializedATN();
        final String expected = "";

        assertWithMessage("Serialized ATNs do not match.")
            .that(actual)
            .contains(expected);
    }

    @Test
    void testCompilationUnit() {
        final MathParser parser = getMathParser("8 * 8");
        final MathParser.CompilationUnitContext actual =
            parser.compilationUnit();
        final MathParser.CompilationUnitContext expected =
            new MathParser.CompilationUnitContext(new ParserRuleContext(), -1);

        assertThat(actual)
            .isInstanceOf(expected.getClass());
        assertWithMessage("")
            .that(actual.exception)
            .isEqualTo(expected.exception);
    }

    @Test
    void testExpr() {
        final MathParser parser = getMathParser("8 * 8");
        final MathParser.ExprContext actual = parser.expr();
        final Class<? extends MathParser.InfixExprContext> expected =
            MathParser.InfixExprContext.class;

        assertWithMessage("")
            .that(actual)
            .isInstanceOf(expected);
    }

    @Test
    void testConstant() {
        final MathParser parser = getMathParser("8 * 8");
        final MathParser.ConstantContext ctx = parser.constant();
        assertWithMessage("")
            .that(ctx.exception.getClass())
            .isEqualTo(InputMismatchException.class);
    }

    @Test
    void testSempred() {
        final MathParser parser = getMathParser("8 * 8");
        final MathParser.InfixExprContext ruleContext =
            new MathParser.InfixExprContext(new MathParser.ExprContext());
        final boolean sempred0 = parser.sempred(ruleContext, 1, 0);
        assertWithMessage("")
            .that(sempred0)
            .isTrue();

        final boolean sempred1 = parser.sempred(ruleContext, 1, 1);
        assertWithMessage("")
            .that(sempred1)
            .isTrue();

        final boolean sempred2 = parser.sempred(ruleContext, 1, 2);
        assertWithMessage("")
            .that(sempred2)
            .isTrue();
    }
}
