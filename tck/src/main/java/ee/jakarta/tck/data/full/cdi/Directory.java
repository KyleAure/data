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

import java.util.List;

import jakarta.data.repository.DataRepository;
import jakarta.data.repository.Repository;

import ee.jakarta.tck.data.full.cdi.provider.PersonExtension;

/**
 * A Directory repository for testing.
 * 
 * @see ee.jakarta.tck.data.full.cdi.provider.DirectoryRepository
 */
@Repository(provider = PersonExtension.PERSON_PROVIDER)
public interface Directory extends DataRepository<Person, Long> {    
    List<String> findFirstNameByIdInOrderByAgeDesc(List<Long> ids);
}
