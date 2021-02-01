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

import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import parsevamath.tools.grammar.MathBaseVisitor;
import parsevamath.tools.grammar.MathLexer;
import parsevamath.tools.grammar.MathParser;

/**
 * This visitor traverses the antlr-parsed rule contexts and builds
 * an AST.
 */
public class HomogeneousAstVisitor
    extends MathBaseVisitor<MathAstNode> implements HomogeneousAstVisitorInterface {

    /**
     * This string is used when throwing IllegalStateException on an
     * unknown token.
     */
    private static final String UNEXPECTED_TOKEN = "Unexpected token: ";

    /**
     * Start the tree, root node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitCompilationUnit(MathParser.CompilationUnitContext ctx) {
        return visit(ctx.expr());
    }

    /**
     * Build infix expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitInfixExpr(MathParser.InfixExprContext ctx) {
        final Token token = ctx.op;
        final MathAstNode astNode = new MathAstNode();
        astNode.setText(ctx.op.getText());
        final int tokenType = switch (token.getType()) {
            case MathLexer.OP_ADD -> TokenTypes.OP_ADD;
            case MathLexer.OP_SUB -> TokenTypes.OP_SUB;
            case MathLexer.OP_MUL -> TokenTypes.OP_MUL;
            case MathLexer.OP_DIV -> TokenTypes.OP_DIV;
            default -> throw new IllegalStateException(UNEXPECTED_TOKEN
                + TokenUtil.getTokenName(token.getType()));
        };
        astNode.setTokenType(tokenType);
        astNode.addChild(visit(ctx.left));
        astNode.addChild(visit(ctx.right));

        astNode.getChildren()
            .forEach(child -> child.setParent(astNode));
        return astNode;
    }

    /**
     * Build unary expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitUnaryExpr(MathParser.UnaryExprContext ctx) {
        final Token token = ctx.op;
        final MathAstNode astNode = new MathAstNode();
        astNode.setText(ctx.op.getText());

        final int tokenType = switch (token.getType()) {
            case MathLexer.OP_ADD -> TokenTypes.OP_ADD;
            case MathLexer.OP_SUB -> TokenTypes.NEGATE;
            default ->
                throw new IllegalStateException(UNEXPECTED_TOKEN
                + TokenUtil.getTokenName(token.getType()));
        };

        astNode.setTokenType(tokenType);
        astNode.addChild(visit(ctx.expr()));
        astNode.getChildren()
            .forEach(child -> child.setParent(astNode));
        return astNode;
    }

    /**
     * Non-terminal node for tree structure.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitFuncExpr(MathParser.FuncExprContext ctx) {
        final MathAstNode astNode = new MathAstNode();
        astNode.setText(ctx.func.getText());
        astNode.setTokenType(TokenTypes.FUNCTION);

        final MathAstNode leftParen = new MathAstNode();
        leftParen.setText(ctx.lparen.getText());
        leftParen.setTokenType(TokenTypes.LPAREN);
        astNode.addChild(leftParen);

        final List<MathParser.ExprContext> exprContexts = ctx.expr();
        final Iterator<TerminalNode> commaNodes = ctx.COMMA().iterator();
        exprContexts.forEach(exprContext -> {
            astNode.addChild(visit(exprContext));

            if (commaNodes.hasNext()) {
                final MathAstNode commaNode = new MathAstNode();
                commaNode.setText(commaNodes.next().getText());
                commaNode.setTokenType(TokenTypes.COMMA);
                astNode.addChild(commaNode);
            }
        });

        final MathAstNode rightParen = new MathAstNode();
        rightParen.setText(ctx.rparen.getText());
        rightParen.setTokenType(TokenTypes.RPAREN);
        astNode.addChild(rightParen);
        astNode.getChildren().forEach(child -> child.setParent(astNode));

        return astNode;
    }

    /**
     * Build numerical expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitNumberExpr(MathParser.NumberExprContext ctx) {
        final MathAstNode astNode = new MathAstNode();
        astNode.setText(ctx.getText());
        astNode.setTokenType(TokenTypes.NUM);
        return astNode;
    }

    /**
     * Build the root of a parenthesis expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitParensExpr(MathParser.ParensExprContext ctx) {
        final MathAstNode leftParen = new MathAstNode();
        leftParen.setText(ctx.lparen.getText());
        leftParen.setTokenType(TokenTypes.LPAREN);
        leftParen.addChild(visit(ctx.expr()));

        leftParen.getChildren().forEach(child -> {
            child.setParent(leftParen);
        });

        final MathAstNode rightParen = new MathAstNode();
        rightParen.setText(ctx.rparen.getText());
        rightParen.setTokenType(TokenTypes.RPAREN);
        rightParen.setParent(leftParen);
        leftParen.addChild(rightParen);

        return leftParen;
    }

    /**
     * Build a factorial expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitFactorialExpr(MathParser.FactorialExprContext ctx) {
        final MathAstNode astNode = new MathAstNode();
        astNode.setText(ctx.fact.getText());
        astNode.setTokenType(TokenTypes.OP_FACT);
        astNode.addChild(visit(ctx.expr()));
        return astNode;
    }

    /**
     * Help build a constant expression node, this is non-terminal.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitConstExpr(MathParser.ConstExprContext ctx) {
        return visit(ctx.constant());
    }

    /**
     * Build the concrete constant expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    @Override
    public MathAstNode visitConstant(MathParser.ConstantContext ctx) {
        final MathAstNode astNode = new MathAstNode();
        astNode.setText(ctx.getText());
        astNode.setTokenType(TokenTypes.CONSTANT);
        return astNode;
    }
}
