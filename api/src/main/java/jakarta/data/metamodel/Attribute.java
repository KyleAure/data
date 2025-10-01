/*
 * Copyright (c) 2023,2025 Contributors to the Eclipse Foundation
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

import jakarta.data.messages.Messages;

/**
 * <p>Supertype for {@link StaticMetamodel} fields representing entity
 * attributes.</p>
 *
 * <p>The following subtypes are provided:</p>
 * <ul>
 * <li>{@link TextAttribute} for entity attributes that represent text,
 *     typically of type {@link String}.</li>
 * <li>{@link NumericAttribute} for entity attributes that represent numeric
 *     values, such as {@code int}, {@link Long}, and {@link java.math.BigDecimal}.
 *     </li>
 * <li>{@link TemporalAttribute} for entity attributes that represent temporal
 *     values, such as {@link java.time.LocalDateTime} and
 *     {@link java.time.Instant}.</li>
 * <li>{@link ComparableAttribute} for entity attributes that represent other
 *     sortable and comparable values, such as {@code boolean} and enumerations.</li>
 * <li>{@link SortableAttribute} for entity types that are sortable, but
 *     incapable of order-based comparison. Generally this subtype is unused
 *     but is applicable for databases that allow sorting on attributes of type
 *     {@code byte[]}.</li>
 * <li>{@link NavigableAttribute} for entity attributes that have attributes
 *     of their own. This is used for embeddables and associations to other
 *     entities.</li>
 * <li>{@link BasicAttribute} for other types of entity attributes, such as
 *     collections, embeddables, and other relation attributes.</li>
 * </ul>
 *
 * <p>To represent an entity, a static metamodel class defines a field
 * corresponding to each entity attribute. The type of the field should be the
 * most precise subtype of {@code Attribute} that describes the entity
 * attribute type.</p>
 *
 * <p>For example, given the following entity class,</p>
 *
 * <pre>
 * &#64;Entity
 * public class Car {
 *     &#64;Id
 *     public String vin;
 *     public Color color;
 *     public double discountRate;
 *     public int firstModelYear;
 *     public LocalDate listed;
 *     public String make;
 *     public String model;
 *     public int price;
 *     public int year;
 * }
 *
 * public enum Color { BLACK, BLUE, GRAY, RED, WHITE }
 * </pre>
 *
 * <p>The static metamodel class (typically generated from the entity class)
 * would be,</p>
 *
 * <pre>
 * &#64;StaticMetamodel
 * public interface _Car {
 *     String COLOR = "color";
 *     String DISCOUNTRATE = "discountRate";
 *     String FIRSTMODELYEAR = "firstModelYear";
 *     String LISTED = "listed";
 *     String MAKE = "make";
 *     String MODEL = "model";
 *     String PRICE = "price";
 *     String VIN = "vin";
 *     String YEAR = "year";
 *
 *     ComparableAttribute&lt;Car,Color&gt; color = ComparableAttribute.of(
 *             Car.class, COLOR, Color.class);
 *     NumericAttribute&lt;Car,Double&gt; discountRate = NumericAttribute.of(
 *             Car.class, DISCOUNTRATE, double.class);
 *     NumericAttribute&lt;Car,Integer&gt; firstModelYear = NumericAttribute.of(
 *             Car.class, FIRSTMODELYEAR, int.class);
 *     TemporalAttribute&lt;Car,LocalDate&gt; listed = TemporalAttribute.of(
 *             Car.class, LISTED, LocalDate.class);
 *     TextAttribute&lt;Car&gt; make = TextAttribute.of(
 *             Car.class, MAKE);
 *     TextAttribute&lt;Car&gt; model = TextAttribute.of(
 *             Car.class, MODEL);
 *     NumericAttribute&lt;Car,Integer&gt; price = NumericAttribute.of(
 *             Car.class, PRICE, int.class);
 *     TextAttribute&lt;Car&gt; vin = TextAttribute.of(
 *             Car.class, VIN);
 *     NumericAttribute&lt;Car,Integer&gt; year = NumericAttribute.of(
 *             Car.class, YEAR, int.class);
 * }
 * </pre>
 *
 * @param <T> entity class of the static metamodel.
 */
public interface Attribute<T> {

    /**
     * Obtain the entity attribute name, suitable for use wherever the
     * specification requires an entity attribute name. For example, as the
     * parameter to {@link jakarta.data.Sort#asc(String)}.
     *
     * @return the entity attribute name.
     */
    String name();

    /**
     * Obtain the Java class which declares this entity attribute.
     *
     * @return the declaring class
     * @throws UnsupportedOperationException if the declaring type is not
     *                                       known.
     * apiNote This is only guaranteed to be known if a static <code>of</code>
     *          method, such as {@link BasicAttribute#of(Class, String, Class)},
     *          was used to obtain the instance.
     * @since 1.1
     */
    default Class<T> declaringType() {
        throw new UnsupportedOperationException(
                Messages.get("012.unknown.decl.type", getClass().getName()));
    }

    /**
     * Obtain the Java class of the entity attribute.
     *
     * @return the type of the entity attribute.
     * @throws UnsupportedOperationException if the entity attribute type is
     *                                       not known.
     * apiNote This is only guaranteed to be known if a static <code>of</code>
     *          method, such as {@link BasicAttribute#of(Class, String, Class)},
     *          was used to obtain the instance.
     * @since 1.1
     */
    default Class<?> attributeType() {
        throw new UnsupportedOperationException(
                Messages.get("011.unknown.attr.type", getClass().getName()));
    }
}
