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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class handles the evaluation of all expressions by visiting each
 * node of our math AST and performing operations as it traverses the tree.
 */
public class EvaluateExpressionVisitor extends MathAstVisitor<Double>{

    /**
     * Visit an addtion node.
     *
     * @param node addition node
     * @return the sum of the node's descendants
     */
    @Override
    Double visit(AdditionNode node) {
        return visit(node.getLeft()) + visit(node.getRight());
    }

    /**
     * Visit a subtraction node.
     *
     * @param node subtraction node
     * @return the difference of the node's descendants
     */
    @Override
    Double visit(SubtractionNode node) {
        return visit(node.getLeft()) - visit(node.getRight());
    }

    /**
     * Visit a multiplication node.
     *
     * @param node multiplication node
     * @return the product of the node's descendants
     */
    @Override
    Double visit(MultiplicationNode node) {
        return visit(node.getLeft()) * visit(node.getRight());
    }

    /**
     * Visit a division node.
     *
     * @param node division node
     * @return the quotient of the node's descendants
     */
    @Override
    Double visit(DivisionNode node) {
        return visit(node.getLeft()) / visit(node.getRight());
    }

    /**
     * Visit a negation node.
     *
     * @param node negate node
     * @return the value of the node's inner node, negated
     */
    @Override
    Double visit(NegateNode node) {
        return - visit(node.getInnerNode());
    }

    /**
     * This method extracts a method from a MethodNode and applies it
     * to a specified argument.
     *
     * @param node the Math function (method) to use in our evaluation.
     * @return the result of the expression evaluated by the function.
     */
    @Override
    Double visit(MethodNode node) {
        Method mathMethod = node.getFunction();
        Double returnVal = Double.NaN;
        try {
            returnVal = (Double) mathMethod.invoke(mathMethod.getClass(), visit(node.getArguement()));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     * Visit a number (numerical expression) node.
     *
     * @param node number node
     * @return the value of the numerical expression
     */
    @Override
    Double visit(NumberNode node) {
        return node.getValue();
    }
}
