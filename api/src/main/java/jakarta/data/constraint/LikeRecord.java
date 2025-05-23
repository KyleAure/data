/*
 * Copyright (c) 2025 Contributors to the Eclipse Foundation
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
package jakarta.data.constraint;

import jakarta.data.expression.TextExpression;

record LikeRecord(TextExpression<?> pattern, Character escape)
        implements Like {

    static final char CHAR_WILDCARD = '_';
    static final char STRING_WILDCARD = '%';
    static final char ESCAPE = '\\';

    @Override
    public NotLike negate() {
        return new NotLikeRecord(pattern, escape);
    }

    @Override
    public String toString() {
        return "LIKE " + pattern +
                (escape == null ? "" : " ESCAPE '" + escape + "'");
    }

    static String escape(String literal) {
        final var result = new StringBuilder();
        for (int i = 0; i < literal.length(); i++) {
            final char ch = literal.charAt(i);
            if (ch == STRING_WILDCARD || ch == CHAR_WILDCARD || ch == ESCAPE) {
                result.append(ESCAPE);
            }
            result.append(ch);
        }
        return result.toString();
    }

    static String translate(String pattern, char charWildcard, char stringWildcard, char escape) {
        if (charWildcard == stringWildcard) {
            throw new IllegalArgumentException(
                    "Cannot use the same character (" + charWildcard +
                            ") for both wildcards");
        }
        if (charWildcard == escape || stringWildcard == escape) {
            throw new IllegalArgumentException(
                    "Cannot use the same character (" + escape +
                            ") for both a wildcard and escape character");
        }
        final var result = new StringBuilder();
        for (int i = 0; i < pattern.length(); i++) {
            final char ch = pattern.charAt(i);
            if (ch == charWildcard) {
                result.append(CHAR_WILDCARD);
            } else if (ch == stringWildcard) {
                result.append(STRING_WILDCARD);
            } else {
                if (ch == STRING_WILDCARD || ch == CHAR_WILDCARD || ch == escape) {
                    result.append(escape);
                }
                result.append(ch);
            }
        }
        return result.toString();
    }
}
