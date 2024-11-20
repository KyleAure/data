/*
 * Copyright (c) 2023,2024 Contributors to the Eclipse Foundation
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
package jakarta.data.metamodel;

import jakarta.data.Restrict;
import jakarta.data.Sort;
import jakarta.data.TextRestriction;

/**
 * Represents an textual entity attribute in the {@link StaticMetamodel}.
 *
 * @param <T> entity class of the static metamodel.
 */
public interface TextAttribute<T> extends SortableAttribute<T> {

    /**
     * Obtain a request for an ascending, case insensitive {@link Sort} based on the entity attribute.
     *
     * @return a request for an ascending, case insensitive sort on the entity attribute.
     */
    Sort<T> ascIgnoreCase();

    default TextRestriction<T> contains(String substring) {
        return Restrict.contains(substring, name());
    }

    /**
     * Obtain a request for a descending, case insensitive {@link Sort} based on the entity attribute.
     *
     * @return a request for a descending, case insensitive sort on the entity attribute.
     */
    Sort<T> descIgnoreCase();

    default TextRestriction<T> endsWith(String suffix) {
        return Restrict.endsWith(suffix, name());
    }

    default TextRestriction<T> equalTo(String value) {
        return Restrict.equalTo(value, name());
    }

    default TextRestriction<T> greaterThan(String value) {
        return Restrict.greaterThan(value, name());
    }

    default TextRestriction<T> greaterThanEqual(String value) {
        return Restrict.greaterThanEqual(value, name());
    }

    default TextRestriction<T> lessThan(String value) {
        return Restrict.lessThan(value, name());
    }

    default TextRestriction<T> lessThanEqual(String value) {
        return Restrict.lessThanEqual(value, name());
    }

    // TODO once we have Pattern:
    //default TextRestriction<T> like(Pattern pattern) {
    //    return Restrict.like(pattern, name());
    //}

    default TextRestriction<T> like(String pattern) {
        return Restrict.like(pattern, name());
    }

    default TextRestriction<T> like(String pattern,
                                    char charWildcard,
                                    char stringWildcard) {
        return Restrict.like(pattern, charWildcard, stringWildcard, name());
    }

    default TextRestriction<T> not(String value) {
        return Restrict.not(value, name());
    }

    default TextRestriction<T> notContains(String substring) {
        return Restrict.notContains(substring, name());
    }

    default TextRestriction<T> notEndsWith(String suffix) {
        return Restrict.notEndsWith(suffix, name());
    }

    default TextRestriction<T> notLike(String pattern) {
        return Restrict.notLike(pattern, name());
    }

    default TextRestriction<T> notLike(String pattern,
                                       char charWildcard,
                                       char stringWildcard) {
        return Restrict.notLike(pattern, charWildcard, stringWildcard, name());
    }

    default TextRestriction<T> notStartsWith(String prefix) {
        return Restrict.notStartsWith(prefix, name());
    }

    default TextRestriction<T> startsWith(String prefix) {
        return Restrict.startsWith(prefix, name());
    }

}
