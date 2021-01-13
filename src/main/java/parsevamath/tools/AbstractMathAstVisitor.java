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

/**
 * This class defines abstract visit methods for each node type.
 *
 * @param <T> the return type of the visit method.
 */
public abstract class AbstractMathAstVisitor<T> {

    /**
     * Visit an addition node.
     *
     * @param node addition node
     * @return the result of calling visit on node
     */
    abstract T visit(AdditionNode node);

    /**
     * Visit a subtraction node.
     *
     * @param node subtraction node
     * @return the result of calling visit on node
     */
    abstract T visit(SubtractionNode node);

    /**
     * Visit a multiplication node.
     *
     * @param node multiplication node
     * @return the result of calling visit on node
     */
    abstract T visit(MultiplicationNode node);

    /**
     * Visit a division node.
     *
     * @param node division node
     * @return the result of calling visit on node
     */
    abstract T visit(DivisionNode node);

    /**
     * Visit a negation node.
     *
     * @param node negate node
     * @return the result of calling visit on node
     */
    abstract T visit(NegateNode node);

    /**
     * This method extracts a method from a MethodNode and applies it
     * to a specified argument.
     *
     * @param node the Math function (method) to use in our evaluation.
     * @return the result of calling visit on node
     */
    abstract T visit(MethodNode node);

    /**
     * Visit a number node.
     *
     * @param node number node to visit
     * @return the result of calling visit on node
     */
    abstract T visit(NumberNode node);

    /**
     * Visit a constant node.
     *
     * @param node constant node to visit
     * @return the result of calling visit on node
     */
    abstract T visit(ConstantNode node);

    /**
     * This method handles the double dispatch of the visit method for
     * each concrete node type.
     *
     * @param node ExpressionNode to visit
     * @return the result of calling visit on node
     * @noinspection OverloadedMethodsWithSameNumberOfParameters
     */
    abstract T visit(ExpressionNode node);
}
