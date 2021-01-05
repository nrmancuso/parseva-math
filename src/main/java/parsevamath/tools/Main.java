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

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import parsevamath.tools.grammar.MathLexer;
import parsevamath.tools.grammar.MathParser;

/**
 * Main driver class for parseva-math.
 */
public final class Main {

    private Main() {
        // Prevent instantiation of Main
    }

    /**
     * For now, this method runs only in "interactive" mode.
     *
     * @param args need to implement
     */
    public static void main(String... args) {
        final Scanner scan = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {

            System.out.print("> ");
            final String exprInput = scan.nextLine();

            if (exprInput.isBlank()) {
                break;
            }

            final CharStream codePointCharStream = CharStreams.fromString(exprInput);
            final MathLexer lexer = new MathLexer(codePointCharStream);
            final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            final MathParser parser = new MathParser(tokenStream);

            try {
                final MathParser.CompilationUnitContext mathTree = parser.compilationUnit();
                final ExpressionNode ast = new MathAstBuilder().visitCompilationUnit(mathTree);

                final Double value = new EvaluateExpressionVisitor().visit(ast);

                final String output = String.format("= %f\n", value);
                System.out.print(output);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }
}
