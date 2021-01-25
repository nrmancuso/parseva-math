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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.antlr.v4.runtime.Token;
import org.tinylog.Logger;

import parsevamath.tools.grammar.MathBaseVisitor;
import parsevamath.tools.grammar.MathLexer;
import parsevamath.tools.grammar.MathParser;

/**
 * This class builds an ast for parseva-math grammar using the visitor pattern.
 */
public class MathAstBuilder extends MathBaseVisitor<ExpressionNode> {

    /**
     * This string is used when throwing IllegalStateException on an
     * unknown token.
     */
    private static final String UNEXPECTED_TOKEN = "Unexpected token: ";

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the node resulting from calling visit on
     * this context rule.
     *
     * @param ctx rule context
     * @return new compilation unit ExpressionNode
     */
    @Override
    public ExpressionNode visitCompilationUnit(MathParser.CompilationUnitContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation creates a new ExpressionNode, determines the correct
     * operation to assign to this node, and sets it's operands.
     *
     * @param ctx rule context
     * @return new infix ExpressionNode
     */
    @Override
    public ExpressionNode visitInfixExpr(MathParser.InfixExprContext ctx) {
        final Token token = ctx.op;
        final InfixExpressionNode node = switch (token.getType()) {
            case MathLexer.OP_ADD -> new AdditionNode();
            case MathLexer.OP_SUB -> new SubtractionNode();
            case MathLexer.OP_MUL -> new MultiplicationNode();
            case MathLexer.OP_DIV -> new DivisionNode();
            default -> throw new IllegalStateException(UNEXPECTED_TOKEN + token.getType());
        };

        node.setLeft(visit(ctx.left));
        node.setRight(visit(ctx.right));
        return node;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation determines the type of unary expression used on
     * an inner node (essentially handling negation) and creates a new
     * ExpressionNode.
     *
     * @param ctx rule context
     * @return new unary ExpressionNode
     */
    @Override
    public ExpressionNode visitUnaryExpr(MathParser.UnaryExprContext ctx) {
        final Token token = ctx.op;
        return switch (token.getType()) {
            case MathLexer.OP_ADD -> visit(ctx.expr());
            case MathLexer.OP_SUB -> {
                final NegateNode negateNode = new NegateNode();
                negateNode.setInnerNode(visit(ctx.expr()));
                yield negateNode;
            }
            default -> throw new IllegalStateException(UNEXPECTED_TOKEN + token.getType());
        };
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation uses reflection to get the specified method
     * from java.lang.Math and creates a new MethodNode.
     *
     * @param ctx rule context
     * @return new method ExpressionNode
     */
    @Override
    public ExpressionNode visitFuncExpr(MathParser.FuncExprContext ctx) {
        final String methodName = ctx.func.getText();

        // Node to return
        final MethodNode methodNode = new MethodNode();
        final List<Double> arguments = new ArrayList<>();
        final List<MathParser.ExprContext> exprContexts = ctx.expr();

        exprContexts.forEach(exprContext -> {
            arguments.add(ParsevaUtils.extractValue(visit(exprContext)));
        });

        methodNode.setArguments(arguments);

        try {
            // Here we fill array with double.class, since this is
            // what all Math methods accept as parameters. The size
            // of this array is the number of arguments to the function.
            final Class<?>[] paramTypes = new Class[arguments.size()];
            Arrays.fill(paramTypes, double.class);

            // Use reflection to get Math method
            final Method method = Math.class.getMethod(methodName, paramTypes);
            methodNode.setFunction(method);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            final String infoString = "Failed to get 'java.lang.Math' method '"
                + methodName + "'";
            Logger.info(infoString, noSuchMethodException);
        }

        return methodNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation instantiates and initializes a new NumberNode.
     *
     * @param ctx rule context
     * @return new NumberNode
     */
    @Override
    public ExpressionNode visitNumberExpr(MathParser.NumberExprContext ctx) {
        return new NumberNode(Double.parseDouble(ctx.value.getText()));
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation instantiates and initializes a new ConstantNode.
     *
     * @param ctx rule context
     * @return new ConstantNode
     */
    @Override
    public ExpressionNode visitConstExpr(MathParser.ConstExprContext ctx) {
        final Double value = switch (ctx.getText().toUpperCase(Locale.ROOT)) {
            case "E" -> Math.E;
            case "PI" -> Math.PI;
            default -> throw new IllegalStateException("Unexpected value: "
                + ctx.getText().toUpperCase(Locale.ROOT));
        };
        return new ConstantNode(value);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation simply allows for the evaluation of its expression
     * in the appropriate order by calling visit().
     *
     * @param ctx rule context
     * @return new ExpressionNode
     */
    @Override
    public ExpressionNode visitParensExpr(MathParser.ParensExprContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation allows for evaluation of factorials.
     */
    @Override
    public ExpressionNode visitFactorialExpr(MathParser.FactorialExprContext ctx) {
        final FactorialNode node = new FactorialNode();
        node.setInnerNode(visit(ctx.expr()));
        return node;
    }
}
