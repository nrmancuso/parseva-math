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

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

import parsevamath.tools.grammar.MathParser;

/**
 * A ast visitor, used for constructing a homogeneous ast.
 */
public interface HomogeneousAstVisitorInterface extends ParseTreeVisitor<MathAstNode> {

    /**
     * Start the tree, root node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitCompilationUnit(MathParser.CompilationUnitContext ctx);

    /**
     * Build infix expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitInfixExpr(MathParser.InfixExprContext ctx);

    /**
     * Build unary expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitUnaryExpr(MathParser.UnaryExprContext ctx);

    /**
     * Non-terminal node for tree structure.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitFuncExpr(MathParser.FuncExprContext ctx);

    /**
     * Build numerical expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitNumberExpr(MathParser.NumberExprContext ctx);

    /**
     * Build the root of a parenthesis expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitParensExpr(MathParser.ParensExprContext ctx);

    /**
     * Build a factorial expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitFactorialExpr(MathParser.FactorialExprContext ctx);

    /**
     * Help build a constant expression node, this is non-terminal.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitConstExpr(MathParser.ConstExprContext ctx);

    /**
     * Build the concrete constant expression node.
     *
     * @param ctx context to analyze
     * @return MathAstNode tree
     */
    MathAstNode visitConstant(MathParser.ConstantContext ctx);
}
