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
package jakarta.data.expression.function;

import jakarta.data.expression.ComparableExpression;
import jakarta.data.expression.TextExpression;
import jakarta.data.expression.literal.NumericLiteral;
import jakarta.data.expression.literal.StringLiteral;

import java.util.List;
import java.util.Objects;

public interface TextFunctionExpression<T>
        extends FunctionExpression<T, String>, TextExpression<T> {

    String CONCAT = "concat";
    String UPPER = "upper";
    String LOWER = "lower";
    String LEFT = "left";
    String RIGHT = "right";

    static <T> TextFunctionExpression<T> of(String name, TextExpression<? super T> argument) {
        Objects.requireNonNull(argument, "The argument is required");
        return new TextFunctionExpressionRecord<>(name, List.of(argument));
    }

    static <T> TextFunctionExpression<T> of(String name, TextExpression<? super T> left, String right) {
        Objects.requireNonNull(left, "The left expression is required");
        Objects.requireNonNull(right, "The right value is required");
        return new TextFunctionExpressionRecord<>(name, List.of(left, StringLiteral.of(right)));
    }

    static <T> TextFunctionExpression<T> of(String name, String left, TextExpression<? super T> right) {
        Objects.requireNonNull(right, "The left value is required");
        Objects.requireNonNull(left, "The right expression is required");
        return new TextFunctionExpressionRecord<>(name, List.of(StringLiteral.of(left), right));
    }

    static <T> TextFunctionExpression<T> of(String name, TextExpression<? super T> left,
                                            TextExpression<? super T> right) {
        Objects.requireNonNull(left, "The left expression is required");
        Objects.requireNonNull(left, "The right expression is required");
        return new TextFunctionExpressionRecord<>(name, List.of(left, right));
    }

    static <T> TextFunctionExpression<T> of(String name, TextExpression<? super T> left, int literal) {
        Objects.requireNonNull(left, "The left expression is required");
        Objects.requireNonNull(literal, "The literal value is required");
        return new TextFunctionExpressionRecord<>(name, List.of(left, NumericLiteral.of(literal)));
    }

    @Override
    List<? extends ComparableExpression<? super T, ?>> arguments();
}
