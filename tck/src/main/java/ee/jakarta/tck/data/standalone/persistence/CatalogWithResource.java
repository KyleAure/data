/*
 * Copyright (c) 2023,2024 Contributors to the Eclipse Foundation
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
package ee.jakarta.tck.data.standalone.persistence;

import jakarta.data.repository.DataRepository;
import jakarta.data.repository.Repository;
import jakarta.data.repository.Save;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ee.jakarta.tck.data.standalone.persistence.Product.Department;

@Repository
public interface CatalogWithResource extends DataRepository<Product, String> {
    
    @Save
    void save(Product product);
    
    long deleteByProductNumLike(String pattern);

    EntityManager getEntityManager();

    default double sumPrices(Department... departments) {
        StringBuilder jpql = new StringBuilder("SELECT SUM(o.price) FROM Product o");
        for (int d = 1; d <= departments.length; d++) {
            jpql.append(d == 1 ? " WHERE " : " OR ");
            jpql.append('?').append(d).append(" MEMBER OF o.departments");
        }

        EntityManager em = getEntityManager();
        TypedQuery<Double> query = em.createQuery(jpql.toString(), Double.class);
        for (int d = 1; d <= departments.length; d++) {
            query.setParameter(d, departments[d - 1]);
        }
        return query.getSingleResult();
    }

}
