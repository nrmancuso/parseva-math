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
 * This interface defines the operations on antlr-parsed rule contexts.
 */
public interface MathAstBuilderInterface extends ParseTreeVisitor<ExpressionNode> {

    /**
     * Evaluate the tree.
     *
     * @param ctx rule context to analyze
     * @return evaluated tree
     */
    ExpressionNode visitCompilationUnit(MathParser.CompilationUnitContext ctx);

    /**
     * Evaluate an infix expression.
     *
     * @param ctx rule context to analyze
     * @return evaluated expression
     */
    ExpressionNode visitInfixExpr(MathParser.InfixExprContext ctx);

    /**
     * Evaluate a unary expression.
     *
     * @param ctx rule context to analyze
     * @return evaluated expression
     */
    ExpressionNode visitUnaryExpr(MathParser.UnaryExprContext ctx);

    /**
     * Evaluate a function node with args.
     *
     * @param ctx rule context to analyze
     * @return evaluated function
     */
    ExpressionNode visitFuncExpr(MathParser.FuncExprContext ctx);

    /**
     * Return a numerical value from AST.
     *
     * @param ctx rule context to analyze
     * @return numerical value
     */
    ExpressionNode visitNumberExpr(MathParser.NumberExprContext ctx);

    /**
     * Return a constant expression.
     *
     * @param ctx rule context to analyze
     * @return constant
     */
    ExpressionNode visitConstExpr(MathParser.ConstExprContext ctx);

    /**
     * Evaluate a parenthetical expression.
     *
     * @param ctx rule context to analyze
     * @return evaluated parenthetical expression
     */
    ExpressionNode visitParensExpr(MathParser.ParensExprContext ctx);

    /**
     * Evaluate a factorial node.
     *
     * @param ctx rule context to analyze
     * @return evaluated factorial
     */
    ExpressionNode visitFactorialExpr(MathParser.FactorialExprContext ctx);
}
