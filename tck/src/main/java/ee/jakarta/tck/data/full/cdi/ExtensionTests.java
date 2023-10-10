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
package ee.jakarta.tck.data.full.cdi;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import ee.jakarta.tck.data.framework.junit.anno.AnyEntity;
import ee.jakarta.tck.data.framework.junit.anno.CDI;
import ee.jakarta.tck.data.framework.junit.anno.Full;
import ee.jakarta.tck.data.framework.junit.anno.RunAsServletClient;
import ee.jakarta.tck.data.framework.servlet.TestClient;
import ee.jakarta.tck.data.full.cdi.provider.PersonExtension;
import jakarta.enterprise.inject.spi.Extension;

@Full
@AnyEntity
@CDI
@RunAsServletClient(ExtensionServlet.class)
public class ExtensionTests extends TestClient {
    
    @Deployment
    public static WebArchive createDeployment() {
        JavaArchive provider = ShrinkWrap.create(JavaArchive.class)
                .addPackage(PersonExtension.class.getPackage())
                .addAsServiceProvider(Extension.class, PersonExtension.class);
                
        
        return ShrinkWrap.create(WebArchive.class)
                .addPackage(ExtensionTests.class.getPackage())
                .addAsLibraries(provider);

    }
    
    @ArquillianResource
    URL baseURL;
    
    @Override
    public URL getBaseURL() {
        return baseURL;
    }
}
