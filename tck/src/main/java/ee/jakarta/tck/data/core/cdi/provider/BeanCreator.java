/*
 * Copyright (c) 2023, 2024 Contributors to the Eclipse Foundation
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
package ee.jakarta.tck.data.core.cdi.provider;

import java.util.logging.Logger;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.build.compatible.spi.Parameters;
import jakarta.enterprise.inject.build.compatible.spi.SyntheticBeanCreator;

/**
 * Creates beans for repositories for which the entity class has the PersonEntity annotation.
 */
public class BeanCreator implements SyntheticBeanCreator<Object> {
    
    private static final Logger log = Logger.getLogger(BeanCreator.class.getCanonicalName());
    
    @Override
    public Object create(Instance<Object> instance, Parameters parameters) {
        Class<?> provider = parameters.get("impl", Class.class);
        
        try {
            return provider.getConstructor().newInstance();
        } catch (Exception e) {
            log.warning("Error while constructing implementation of repository: " + e.getMessage());
            return null;
        }
    }
}
