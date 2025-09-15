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
 *  SPDX-License-Identifier: Apache-2.0
 */
package jakarta.data.constraint;

import jakarta.data.spi.expression.literal.StringLiteral;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Like constraint, including patterns, wildcards, and
 * escape characters.
 */
class LikeTest {

    @Test
    void likeLiteral() {
        Like like = Like.literal("100% first_item\\second_item\\third_item");

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.escape())
                .isEqualTo('\\'));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like.pattern()).value())
                .isEqualTo("100\\% first\\_item\\\\second\\_item\\\\third\\_item"));
    }

    @Test
    void likePattern() {
        // Usage is from Javadoc example
        Like like = Like.pattern("JHM___E%");

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.escape())
                .isNull());

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like.pattern()).value())
                .isEqualTo("JHM___E%"));
    }

    @Test
    void likePatternWithCustomWildcards() {
        // Usage is from Javadoc example
        Like like = Like.pattern("JHM???F*", '?', '*');

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.escape())
                .isEqualTo('\\'));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like.pattern()).value())
                .isEqualTo("JHM___F%"));
    }

    @Test
    void likePatternWithCustomWildcardsAndCustomEscape() {
        // Usage is from Javadoc example
        Like like1 = Like.pattern("JHM---^CC", '-', 'C', '^');

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like1.escape())
                .isEqualTo('^'));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like1.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like1.pattern()).value())
                .isEqualTo("JHM___C%"));

        // Usage is from Javadoc example
        Like like2 = Like.pattern("is --.-*% of", '-', '*', '^');

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like2.escape())
                .isEqualTo('^'));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like2.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like2.pattern()).value())
                .isEqualTo("is __._%^% of"));
    }

    @Test
    void likePatternWithCustomWildcardsAndNormalEscape() {
        Like like = Like.pattern("11% of \\#1# \\\\ \\1", '1', '#', '\\');

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.escape())
                .isEqualTo('\\'));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like.pattern()).value())
                .isEqualTo("__\\% of #_% \\\\ 1"));
    }

    @Test
    void likePatternWithNormalWildcardsAndNormalEscape() {
        Like like = Like.pattern("__\\% of _._% \\\\\\\\ ", '_', '%', '\\');

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.escape())
                .isEqualTo('\\'));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(like.pattern())
                .isInstanceOf(StringLiteral.class));

        SoftAssertions.assertSoftly(soft -> soft.assertThat(
                ((StringLiteral) like.pattern()).value())
                .isEqualTo("__\\% of _._% \\\\\\\\ "));
    }
}
