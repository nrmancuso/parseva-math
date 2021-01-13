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
import java.util.Properties;

import org.tinylog.Logger;

/**
 * This is a utility class for parseva-math.
 */
public final class ParsevaUtils {

    private ParsevaUtils() {
        // Prevent instantiation of parsevamath.tools.MathUtils
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
}
