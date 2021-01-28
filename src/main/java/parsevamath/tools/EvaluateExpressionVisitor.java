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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.tinylog.Logger;

/**
 * This class handles the evaluation of all expressions by visiting each
 * node of the math AST and performing operations as it traverses the tree.
 */
public class EvaluateExpressionVisitor extends AbstractMathAstVisitor<Double> {

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the sum of this node's two descendants.
     */
    @Override
    Double visit(AdditionNode node) {
        return visit(node.getLeft()) + visit(node.getRight());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the difference of this node's two descendants.
     */
    @Override
    Double visit(SubtractionNode node) {
        return visit(node.getLeft()) - visit(node.getRight());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the product of this node's two descendants.
     */
    @Override
    Double visit(MultiplicationNode node) {
        return visit(node.getLeft()) * visit(node.getRight());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the quotient of two descendants.
     */
    @Override
    Double visit(DivisionNode node) {
        return visit(node.getLeft()) / visit(node.getRight());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the value of the node's inner node, negated.
     */
    @Override
    Double visit(NegateNode node) {
        return -visit(node.getInnerNode());
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the factorial of the inner node.
     */
    @Override
    Double visit(FactorialNode node) {
        final ExpressionNode innerNode = node.getInnerNode();
        return MathUtils.getFactorial(visit(innerNode));
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation extracts a method from a MethodNode and applies it
     * to a specified argument, returning the result.
     */
    @Override
    Double visit(MethodNode node) {
        final Method mathMethod = node.getFunction();
        Double returnVal = Double.NaN;
        if (mathMethod != null) {
            try {
                returnVal =
                    (Double) mathMethod.invoke(mathMethod.getClass(),
                        node.getArguments().toArray());
            }
            catch (IllegalAccessException | InvocationTargetException exception) {
                final String infoString = "Failed to invoke method '"
                    + mathMethod.getName()
                    + "' on arguement '"
                    + node.getArguments();
                Logger.info(infoString, exception);
            }
        }
        return returnVal;
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the value of the numerical expression.
     */
    @Override
    Double visit(NumberNode node) {
        return node.getValue();
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns the value of this constant.
     */
    @Override
    Double visit(ConstantNode node) {
        return node.getValue();
    }

}
