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

import java.util.List;

/**
 * The node type used in the homogeneous abstract syntax tree.
 */
public interface MathAstNode {
    /**
     * Get the parent node of this node.
     *
     * @return the parent of this node
     */
    MathAstNode getParent();

    /**
     * Set the parent node of this node.
     *
     * @param parent the MathAstNode to set
     */
    void setParent(MathAstNode parent);

    /**
     * Adds one child to the child list of this MathAstNode.
     *
     * @param node the MathAstNode to add
     */
    void addChild(MathAstNode node);

    /**
     * Get the text of this node.
     *
     * @return string of text
     */
    String getText();

    /**
     * Set the text of this MathAstNode.
     *
     * @param text to be set
     */
    void setText(String text);

    /**
     * Gets type of this MathAstNode.
     *
     * @return type of node
     */
    int getTokenType();

    /**
     * Sets token type of this node.
     *
     * @param tokenType the type of token to set
     */
    void setTokenType(int tokenType);

    /**
     * Get an unmodifiable list of children from this MathAstNode.
     *
     * @return unmodifiable list
     */
    List<MathAstNode> getChildren();

    /**
     * Sets the children of this MathAstNode.
     *
     * @param children list of children
     */
    void setChildren(List<MathAstNode> children);

    /**
     * Gets position of this token.
     *
     * @return index of token in line
     */
    int getColumnNumber();

    /**
     * Sets the column number of this token.
     *
     * @param columnNumber sets postion of token.
     */
    void setColumnNumber(int columnNumber);
}
