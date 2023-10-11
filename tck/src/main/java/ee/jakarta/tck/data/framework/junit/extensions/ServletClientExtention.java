/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation
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
package ee.jakarta.tck.data.framework.junit.extensions;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.spi.TestClass;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import ee.jakarta.tck.data.framework.servlet.TestClient;

/**
 * Verify that when the {@literal @}RunAsServletClient annotation is placed on a class that:
 * 1. The test class extends TestClient
 * 2. The test class has a {@literal @}Deployment method
 * 3. The {@literal @}Deployment method is set to testable=false
 */
public class ServletClientExtention implements BeforeAllCallback  {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        
        Class<?> testClazz = context.getTestClass().orElseThrow();
        
        if(! TestClient.class.isAssignableFrom(testClazz)) {
            throw new IllegalStateException(TestClass.class.getCanonicalName()
                    + " was annotated with @RunAsServletClient but did not extend " + TestClient.class.getCanonicalName());
        }
        
        Method deployment = Arrays.asList(testClazz.getMethods()).stream()
                .filter(m -> m.isAnnotationPresent(Deployment.class))
                .findFirst()
                .orElse(null);
        
        if(deployment == null) {
            throw new IllegalStateException(TestClass.class.getCanonicalName()
                    + " was annotated with @RunAsServletClient, but did not have a @Deployment method");
        }
        
        if(deployment.getDeclaredAnnotation(Deployment.class).testable()) {
            throw new IllegalStateException(TestClass.class.getCanonicalName()
                    + " was annotated with @RunAsServletClient, but the @Deployment method had testable=true");
        }
        
    }

}
