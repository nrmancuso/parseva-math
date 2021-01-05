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

import org.antlr.v4.runtime.Token;

import java.lang.reflect.Method;

public class MathAstBuilder extends MathBaseVisitor<ExpressionNode>{

    /**
     * {@inheritDoc}
     *
     * This implementation returns the node resulting from calling visit on
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
     * This implementation creates a new ExpressionNode, determines the correct
     * operation to assign to this node, and sets it's operands.
     *
     * @param ctx rule context
     * @return new infix ExpressionNode
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
     * This implementation determines the type of unary expression used on
     * an inner node (essentially handling negation) and creates a new
     * ExpressionNode.
     *
     * @param ctx rule context
     * @return new unary ExpressionNode
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
     * This implementation uses reflection to get the specified method
     * from java.lang.Math and creates a new MethodNode.
     *
     * @param ctx rule context
     * @return new method ExpressionNode
     */
    @Override
    public ExpressionNode visitFuncExpr(MathParser.FuncExprContext ctx) {
        String methodName = ctx.func.getText();

        // Node to return
        MethodNode methodNode = new MethodNode();
        methodNode.setArgument(visit(ctx.expr()));

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
     * This implementation instantiates and initializes a new NumberNode.
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
     * This implementation simply allows for the evaluation of its expression
     * in the appropriate order by calling visit().
     *
     * @param ctx rule context
     * @return new ExpressionNode
     */
    @Override
    public ExpressionNode visitParensExpr(MathParser.ParensExprContext ctx) {
        return visit(ctx.expr());
    }
}
