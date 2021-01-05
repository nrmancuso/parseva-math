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

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class Main {

    private Main() {
        // Prevent instantiation of Main
    }

    public static void main(String... args) {
        Scanner scan = new Scanner(System.in, StandardCharsets.UTF_8);
        while (true) {

            System.out.print("> ");
            String exprInput = scan.nextLine();

            if (exprInput.isBlank()) {
                break;
            }

            //String exprInput = "sqrt(4)";

            CharStream codePointCharStream = CharStreams.fromString(exprInput);
            MathLexer lexer = new MathLexer(codePointCharStream);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            MathParser parser = new MathParser(tokenStream);

            try {
                MathParser.CompilationUnitContext mathTree = parser.compilationUnit();
                ExpressionNode ast = new MathAstBuilder().visitCompilationUnit(mathTree);

                // Figure out good way to print ast, maybe make new visitor, such as pretty-print visitor
                Double value = new EvaluateExpressionVisitor().visit(ast);

                String output = String.format("= %f\n", value);
                System.out.print(output);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }
}
