/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
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

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import jakarta.data.test.util.ReplaceCamelCase;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceCamelCase.class)
class LimitTest {

    @Test
    void shouldReturnErrorWhen_maxResults_IsNegative() {
        assertThatIllegalArgumentException().isThrownBy(() -> Limit.of(-1));
    }

    @Test
    void shouldReturnErrorWhen_maxResults_IsZero() {
        assertThatIllegalArgumentException().isThrownBy(() -> Limit.of(0));
    }

    @Test
    void shouldReturnErrorWhen_startAt_IsGreaterThan_endAt() {
        assertThatIllegalArgumentException().isThrownBy(() -> Limit.range(2, 1));
    }

    @Test
    void shouldReturnErrorWhen_endAt_IsLessThan_startAt() {
        assertThatIllegalArgumentException().isThrownBy(() -> Limit.range(10, 1));
    }

    @Test
    void shouldReturnErrorWhen_startAt_IsNegative() {
        assertThatIllegalArgumentException().isThrownBy(() -> Limit.range(-1, 10));
    }

    @Test
    void shouldReturnErrorWhen_startAt_IsZero() {
        assertThatIllegalArgumentException().isThrownBy(() -> Limit.range(0, 100));
    }

    @Test
    void shouldCreateLimitWithDefault_maxResults() {
        Limit limit = Limit.of(1);

        assertSoftly(soft -> {
            soft.assertThat(limit).isNotNull();
            soft.assertThat(limit.maxResults()).isEqualTo(1L);
            soft.assertThat(limit.startAt()).isEqualTo(1L);
        });
    }

    @Test
    void shouldCreateLimitWithDefault_startAt() {
        Limit limit = Limit.of(10);

        assertSoftly(soft -> {
            soft.assertThat(limit).isNotNull();
            soft.assertThat(limit.maxResults()).isEqualTo(10L);
            soft.assertThat(limit.startAt()).isEqualTo(1L);
        });
    }

    @Test
    void shouldCreateLimitWithEqualsRange() {
        Limit limit = Limit.range(1, 1);

        assertSoftly(soft -> {
            soft.assertThat(limit).isNotNull();
            soft.assertThat(limit.maxResults()).isEqualTo(1L);
            soft.assertThat(limit.startAt()).isEqualTo(1L);
        });
    }

    @Test
    void shouldCreateLimitWithRange() {
        Limit limit = Limit.range(2, 11);

        assertSoftly(soft -> {
            soft.assertThat(limit).isNotNull();
            soft.assertThat(limit.maxResults()).isEqualTo(10L);
            soft.assertThat(limit.startAt()).isEqualTo(2L);
        });
    }
}
