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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.tinylog.Logger;

/**
 * This is a utility class for parseva-math.
 */
public final class ParsevaUtils {

    /**
     * Capacity for stringbuilders.
     */
    public static final int CAPACITY = 1024;

    /**
     * Prevent instantiation.
     */
    private ParsevaUtils() {
    }

    /**
     * Extract the value from a numerical expression or negation node.
     *
     * @param node the ExpressionNode to extract value from
     * @return value of numerical expression
     */
    public static Double extractValue(ExpressionNode node) {
        final Double value;
        if (node instanceof NegateNode negateNode) {
            final NumberNode innerNode = (NumberNode) negateNode.getInnerNode();
            value = innerNode.getValue();
        }
        else {
            final NumberNode numberNode = (NumberNode) node;
            value = numberNode.getValue();
        }
        return value;
    }

    /**
     * Gets the version number from properties file.
     *
     * @return version number
     */
    public static String getParsevaVersion() {
        String versionString;
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final InputStream stream = loader.getResourceAsStream("project.properties");
        try {
            final Properties properties = new Properties();
            properties.load(stream);
            versionString = properties.getProperty("version");
        }
        catch (IOException ignore) {
            versionString = "Version information not available";
        }

        try {
            stream.close();
        }
        catch (IOException exception) {
            Logger.warn("Could not close input stream!", exception);
        }

        return versionString;
    }

    /**
     * Builds a string representation of the ast.
     *
     * @param astNode the ast node to build string of
     * @return string representation of ast
     */
    public static String toStringTree(MathAstNode astNode) {
        MathAstNode ast = astNode;
        final List<MathAstNode> firstStack = new ArrayList<>();
        firstStack.add(ast);

        final List<List<MathAstNode>> childListStack = new ArrayList<>();
        childListStack.add(firstStack);

        final StringBuilder builder = new StringBuilder(CAPACITY);
        while (!childListStack.isEmpty()) {

            final List<MathAstNode> childStack = childListStack.get(childListStack.size() - 1);

            if (childStack.isEmpty()) {
                childListStack.remove(childListStack.size() - 1);
            }
            else {
                ast = childStack.remove(0);

                final String caption = String.format("%s -> %s",
                    TokenUtil.getTokenName(ast.getTokenType()), ast.getText());

                final StringBuilder indent = new StringBuilder(CAPACITY);

                for (int i = 0; i < childListStack.size() - 1; i++) {
                    if (childListStack.get(i).isEmpty()) {
                        indent.append("   ");
                    }
                    else {
                        indent.append("|  ");
                    }
                }

                if (childStack.isEmpty()) {
                    builder.append(indent)
                        .append("'- ")
                        .append(caption)
                        .append(System.lineSeparator());
                }
                else {
                    builder.append(indent)
                        .append("|- ")
                        .append(caption)
                        .append(System.lineSeparator());
                }

                if (ast.getChildren().isEmpty()) {
                    continue;
                }
                final List<MathAstNode> toStringChildren = new ArrayList<>(ast.getChildren());
                childListStack.add(toStringChildren);
            }
        }
        return builder.toString();
    }

}
