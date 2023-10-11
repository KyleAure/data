/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.jakarta.tck.data.web.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import ee.jakarta.tck.data.framework.junit.anno.Assertion;
import ee.jakarta.tck.data.framework.servlet.TestServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(ComplexServlet.URL_PATTERN)
public class ComplexServlet extends TestServlet {

    public static final String URL_PATTERN = "/ComplexServlet";
    
    public static final String EXPECTED_RESPONSE = "asdf123";

//    @Assertion(id = "26", strategy = "Verify assertions defined on a servlet are automatically run.")
    public void testServletSideSuccess() {
        assertTrue(true);
    }
    
    @Assertion(id = "26", strategy = "Verify assertions defined on a servlet are automatically run, and failures are reported correctly.")
    public void testServletSideFailure() {
        assertTrue(false);
        throw new RuntimeException("FAILURE");
    }
    
    //This method is not annotated with @Assertion and thus should not be executed
    public void testServerSideNoExecuted() {
        assertTrue(false, "This test should not have been exectuted.");
    }
    
    //This test method needs to provide a specific response to the test client
    public void testServletSideCustomResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Append a custom response
        response.getWriter().append(EXPECTED_RESPONSE);
    }

}
