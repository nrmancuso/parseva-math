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
 * The node type used in the homogeneous abstract syntax tree.
 */
public class MathAstNodeImpl implements MathAstNode {

    /**
     * Token type of this MathAstNode.
     */
    private int tokenType;

    /**
     * Children of this MathAstNode.
     */
    private List<MathAstNode> children;

    /**
     * Position of this MathAstNode in expression.
     */
    private int columnNumber;

    /**
     * The parent MathAstNode of this MathAstNode.
     */
    private MathAstNode parent;

    /**
     * The text of this MathAstNode.
     */
    private String text;

    /**
     * Default constructor.
     */
    public MathAstNodeImpl() {
        children = new ArrayList<>();
    }

    @Override
    public final MathAstNode getParent() {
        return parent;
    }

    @Override
    public void setParent(MathAstNode parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(MathAstNode node) {
        children.add(node);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getTokenType() {
        return tokenType;
    }

    @Override
    public void setTokenType(int tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public List<MathAstNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void setChildren(List<MathAstNode> children) {
        this.children = Collections.unmodifiableList(children);
    }

    @Override
    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Override
    public String toString() {
        return "MathAstNode{" + "tokenType=" + tokenType
            + ", children=" + children
            + ", columnNumber=" + columnNumber
            + ", parent=" + parent
            + ", text='" + text + '\''
            + '}';
    }
}
