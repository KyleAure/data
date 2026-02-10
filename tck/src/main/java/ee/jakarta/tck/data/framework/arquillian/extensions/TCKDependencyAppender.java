/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation
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
package ee.jakarta.tck.data.framework.arquillian.extensions;

import org.jboss.arquillian.container.test.spi.client.deployment.CachedAuxilliaryArchiveAppender;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * This extension will intercept all archives before they are deployed to the
 * container and append the following dependencies:
 *
 * <p>org.assertj:assertj-core</p>
 *
 */
public class TCKDependencyAppender extends CachedAuxilliaryArchiveAppender {

    @Override
    protected Archive<?> buildArchive() {
        JavaArchive dependencies = ShrinkWrap.create(JavaArchive.class, "tck-dependencies.jar");
        
        appendAssertJ(dependencies);
        
        return dependencies;
    }
    
    private void appendAssertJ(JavaArchive dependencies) {
        dependencies.addPackages(true, "org.assertj.core");
    }

}
