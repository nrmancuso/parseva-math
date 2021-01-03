import org.antlr.v4.runtime.Token;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MathAstBuilder extends MathBaseVisitor<ExpressionNode>{

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public ExpressionNode visitCompilationUnit(MathParser.CompilationUnitContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public ExpressionNode visitInfixExpr(MathParser.InfixExprContext ctx) {
        Token token = ctx.op;
        InfixExpressionNode node = switch (token.getType()) {
            case MathLexer.OP_ADD -> new AdditionNode();
            case MathLexer.OP_SUB -> new SubtractionNode();
            case MathLexer.OP_MUL -> new MultiplicationNode();
            case MathLexer.OP_DIV -> new DivisionNode();
            default -> throw new IllegalStateException("Unexpected value: " + token.getType());
        };

        node.setLeft(visit(ctx.left));
        node.setRight(visit(ctx.right));
        return node;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public ExpressionNode visitUnaryExpr(MathParser.UnaryExprContext ctx) {
        Token token = ctx.op;
        return switch (token.getType()) {
            case MathLexer.OP_ADD -> visit(ctx.expr());
            case MathLexer.OP_SUB -> {
                NegateNode negateNode = new NegateNode();
                negateNode.setInnerNode(visit(ctx.expr()));
                yield negateNode;
            }
            default -> throw new IllegalStateException("Unexpected value: " + token.getType());
        };
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public ExpressionNode visitFuncExpr(MathParser.FuncExprContext ctx) {
        String methodName = ctx.func.getText();

        // Node to return
        MethodNode methodNode = new MethodNode();
        methodNode.setArguement(visit(ctx.expr()));

        try {
            Method method = Math.class.getMethod(methodName, double.class);
            methodNode.setFunction(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return methodNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public ExpressionNode visitNumberExpr(MathParser.NumberExprContext ctx) {
        return new NumberNode(Double.parseDouble(ctx.value.getText()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     *
     * @param ctx
     */
    @Override
    public ExpressionNode visitParensExpr(MathParser.ParensExprContext ctx) {
        return visit(ctx.expr());
    }
}
