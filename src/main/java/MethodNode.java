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

import java.lang.reflect.Method;

/**
 * This class represents a mathematical function node in the ast.
 */
non-sealed class MethodNode implements ExpressionNode {
    
    /** Mathmatical function that this node represents */
    private Method function;
    
    /** Argument to supply to function */
    private ExpressionNode argument;

    /**
     * Get the function that this node uses.
     *
     * @return method from java.lang.Math
     */
    public Method getFunction() {
        return function;
    }

    /**
     * Sets the function that this node uses.
     *
     * @param function the method from java.lang.Math to set
     */
    public void setFunction(Method function) {
        this.function = function;
    }

    /**
     * Gets the argument supplied to this node to evaluate.
     *
     * @return numerical expression
     */
    public ExpressionNode getArgument() {
        return argument;
    }

    /**
     * Sets the numerical argument for this node to evaluate.
     *
     * @param argument numerical expression
     */
    public void setArgument(ExpressionNode argument) {
        this.argument = argument;
    }
}
