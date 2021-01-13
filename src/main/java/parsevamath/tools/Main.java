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
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;

/**
 * Main driver class for parseva-math.
 */
public final class Main {

    private Main() {
        // prevent instantiation
    }

    /**
     * Depending on the arguments supplied on command line,
     * runs parseva-math in interactive or evaluation
     * mode.
     *
     * @param args command line args
     * @noinspection UseOfSystemOutOrSystemErr
     */
    public static void main(String... args) {

        final CliOptions cliOptions = new CliOptions();
        final CommandLine commandLine = new CommandLine(cliOptions);
        commandLine.setUsageHelpWidth(CliOptions.HELP_WIDTH);
        commandLine.setCaseInsensitiveEnumValuesAllowed(true);
        commandLine.parseArgs(args);

        try {
            if (cliOptions.interactiveMode) {
                runInteractive();
            }
            else if (cliOptions.evaluationMode) {
                final double value = evaluate(cliOptions.expression);
                printOutput(value);
            }
            else if (commandLine.isVersionHelpRequested()) {
                System.out.println(ParsevaUtils.getParsevaVersion());
            }
            else {
                commandLine.usage(System.out);
            }
        }
        catch (ParameterException parameterException) {
            commandLine.usage(System.out);
        }
    }

    /**
     * This method handles the "interactive mode" loop where
     * the user is prompted to enter an expression to be evaluated.
     *
     * @noinspection UseOfSystemOutOrSystemErr
     */
    private static void runInteractive() {
        final Scanner scan = new Scanner(System.in,
            StandardCharsets.UTF_8);
        while (true) {
            System.out.print("> ");
            final String exprInput = scan.nextLine();
            if (exprInput.isBlank()) {
                break;
            }
            final double value = evaluate(exprInput);
            printOutput(value);
        }
        scan.close();
    }

    /**
     * This method handles the actual evaluation of the expression ast.
     *
     * @param exprInput the expression to evaluate
     * @return the value of the expression
     */
    public static Double evaluate(String exprInput) {
        final CharStream codePointCharStream = CharStreams.fromString(exprInput);
        final MathLexer lexer = new MathLexer(codePointCharStream);
        final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        final MathParser parser = new MathParser(tokenStream);

        final MathParser.CompilationUnitContext mathTree = parser.compilationUnit();
        final ExpressionNode ast = new MathAstBuilder().visitCompilationUnit(mathTree);

        return new EvaluateExpressionVisitor().visit(ast);
    }

    /**
     * Handles printing of output.
     *
     * @param value the double to print
     * @noinspection UseOfSystemOutOrSystemErr
     */
    private static void printOutput(Double value) {
        final String output = String.format("= %f\n", value);
        System.out.println(output);
    }

    /**
     * Command line options.
     *
     * @noinspection unused, FieldMayBeFinal, CanBeFinal,
     *     MismatchedQueryAndUpdateOfCollection, LocalCanBeFinal
     */
    @Command(name = "parseva-math", description = "parses mathematical expressions "
        + "and evaluates them by traversing the ast of the expression.",
        mixinStandardHelpOptions = true)
    private static class CliOptions {

        /**
         * Width of CLI help option.
         */
        private static final int HELP_WIDTH = 100;

        /**
         * This boolean value determines whether user is in interactive mode or not.
         */
        @Option(names = {
            "-i",
            "--interact"
        },
            description = "Interactive mode. This option runs the parseva-math interpreter.")
        private boolean interactiveMode;

        /**
         * This boolean value determines whether user is in evalution mode or not.
         */
        @Option(names = {
            "-e",
            "--evaluate"
        },
            description = "Evaluation mode. This option evaluates the given expression,"
                + " and returns the result.",
            defaultValue = "false")
        private boolean evaluationMode;

        /**
         * The actual expression we are evaluating.
         */
        @Parameters(arity = "0..1", description = "Expression to evaluate")
        private String expression;
    }
}
