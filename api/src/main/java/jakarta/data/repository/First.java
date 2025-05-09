/*
 * Copyright (c) 2022,2025 Contributors to the Eclipse Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package jakarta.data.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Specifies a static limit on the number of results retrieved by a
 * repository method. The results of a single invocation of a repository method
 * may be limited to a given {@linkplain #value maximum number of results}. For
 * example,</p>
 *
 * <pre>
 * &#64;Find &#64;First
 * &#64;OrderBy(value = _Employee.SALARY, descending = true)
 * Employee highestPaid(String jobTitle);
 * </pre>
 *
 * <pre>
 * &#64;First(10)
 * &#64;Query("order by playCount desc")
 * List&lt;Song&gt; topTen();
 * </pre>
 *
 * <p>A repository method may not be declared with:
 * <ul>
 * <li>a {@code @First} annotation and a parameter of type
 *     {@link jakarta.data.page.PageRequest} or
 *     {@link jakarta.data.Limit}, or</li>
 * <li>a {@code @First} annotation in combination with the
 *     {@code First} keyword.
 * </ul>
 *
 * @see Find
 * @see Query
 * @since 1.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface First {
    /**
     * The limit on the number of results returned by the repository method.
     * Must be strictly positive.
     */
    int value() default 1;
}
