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
package jakarta.data.test.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayNameGenerator;

/**
 * This class will automatically replace method names with sentences removing the need
 * to annotate classes with {@Code @DisplayName}
 * 
 * <pre>
 * Rules: 
 * 1. Camel case is replaced with spaced sentences
 * Example: shouldReturnError -> Should return error
 * 
 * 2. Numbers are considered words and will be surrounded by spaces
 * Example: shouldReturn5Errors -> Should return 5 errors
 * 
 * 3. Substrings surrounded with underscores (_) are ignored when modifying camel case
 * Example: shouldReturn_SoMeThInG_or_TheOther -> Should return SoMeThInG or TheOther
 * 
 * 4. Dollar signs ($) are replaced with periods (.) for referencing class methods
 * Example: shouldReturn_Math$abs -> Should return Math.abs
 * </pre>
 */
public class ReplaceCamelCase extends DisplayNameGenerator.Simple implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        String[] nameParts = testMethod.getName().replaceAll("\\$", ".").split("_");
        List<String> replacedNameParts = IntStream.range(0, nameParts.length)
            .mapToObj(index -> index % 2 == 0 ? replaceCamelCase(nameParts[index], index == 0) : nameParts[index])
            .collect(Collectors.toList());
        
        return String.join(" ", replacedNameParts);
    }
    
    private String replaceCamelCase(String input, boolean firstIndex) {       //IN "shouldReturn5Things"
        input = input.replaceAll("([A-Z])", " $1");                           //-> " should Return5 Things"
        input = input.replaceAll("([0-9]+)", " $1");                          //-> " should Return 5 Things"
        input = input.toLowerCase().stripLeading();                           //-> "should return 5 things"
        if(firstIndex) {
            input = input.substring(0, 1).toUpperCase() + input.substring(1); //-> "Should return 5 things"
        }
        return input;
    }
}
