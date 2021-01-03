import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static final int CAPACITY = 1024;

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
