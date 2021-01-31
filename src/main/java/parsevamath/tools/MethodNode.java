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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a mathematical function node in the ast.
 */
class MethodNode implements ExpressionNode {

    /**
     * The arguments to supply to this function.
     */
    private final List<ExpressionNode> arguments;

    /**
     * Unchecked name of mathematical function.
     */
    private String functionName;

    /**
     * Default constructor.
     */
    MethodNode() {
        arguments = new ArrayList<>();
    }

    /**
     * Gets the given function name.
     *
     * @return name of function
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Sets the function name for this node.
     *
     * @param functionName name to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * Gets the arguments supplied to this node to evaluate.
     *
     * @return list of numerical expressions
     */
    public List<ExpressionNode> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    /**
     * Sets the numerical arguments for this node to evaluate.
     *
     * @param arguments numerical expressions
     */
    public void setArguments(List<ExpressionNode> arguments) {
        this.arguments.addAll(arguments);
    }

    /**
     * Add argument (ExpressionNode) to list of arguments.
     *
     * @param node ExpressioNode to add
     */
    public void addArgument(ExpressionNode node) {
        arguments.add(node);

    }

}
