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

import java.util.Objects;

import jakarta.data.expression.Expression;
import jakarta.data.expression.literal.Literal;

public interface EqualTo<V> extends Constraint<V> {

    static <V> EqualTo<V> expression(Expression<?, V> expression) {
        Objects.requireNonNull(expression, "The expression is required");
        return new EqualToRecord<>(expression);
    }

    static <V> EqualTo<V> value(V value) {
        Objects.requireNonNull(value, "The value is required");
        return new EqualToRecord<>(Literal.of(value));
    }

    Expression<?, V> expression();
}
