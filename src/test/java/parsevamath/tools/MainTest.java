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

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.itsallcode.io.Capturable;
import org.itsallcode.junit.sysextensions.ExitGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard;
import org.itsallcode.junit.sysextensions.SystemErrGuard.SysErr;
import org.itsallcode.junit.sysextensions.SystemOutGuard;
import org.itsallcode.junit.sysextensions.SystemOutGuard.SysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ExitGuard.class, SystemErrGuard.class, SystemOutGuard.class})
public class MainTest {

    /**
     * <p>Configures the environment for each test.</p>
     * <ul>
     * <li>Restore original logging level and HANDLERS to prevent bleeding into other tests;</li>
     * <li>Start output capture for {@link System#err} and {@link System#out}</li>
     * </ul>
     *
     * @param systemErr wrapper for {@code System.err}
     * @param systemOut wrapper for {@code System.out}
     */
    @BeforeEach
    public void setUp(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        systemErr.captureMuted();
        systemOut.captureMuted();
    }

    @Test
    public void testVersionPrint(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        Main.main("-V");
        assertWithMessage("Ouput should contain version information.")
            .that(systemOut.getCapturedData())
            .contains("SNAPSHOT");
        assertWithMessage("Unexpected error message")
            .that(systemErr.getCapturedData())
            .isEmpty();

    }

    @Test
    public void testUsageHelpPrint(@SysErr Capturable systemErr, @SysOut Capturable systemOut) {
        Main.main("-h");
        assertWithMessage("Help output should contain usage information.")
            .that(systemOut.getCapturedData())
            .contains("Usage: parseva-math");
        assertWithMessage("Unexpected error message")
            .that(systemErr.getCapturedData())
            .isEmpty();
    }

    @Test
    public void testInteractiveMode(@SysErr Capturable systemErr, @SysOut Capturable systemOut)
        throws IOException {

    }

}
