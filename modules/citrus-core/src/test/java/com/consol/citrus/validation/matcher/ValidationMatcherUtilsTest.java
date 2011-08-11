/*
 * Copyright 2006-2011 the original author or authors.
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
 */

package com.consol.citrus.validation.matcher;

import static org.easymock.EasyMock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.consol.citrus.testng.AbstractTestNGUnitTest;

/**
 * @author Christoph Deppisch
 */
public class ValidationMatcherUtilsTest extends AbstractTestNGUnitTest {

    @Autowired
    private ValidationMatcher validationMatcher;
    
    @Test
    public void testResolveDefaultValidationMatcher() {
        ValidationMatcherUtils.resolveValidationMatcher("field", "value", "@equalsIgnoreCase('value')@", context);
        ValidationMatcherUtils.resolveValidationMatcher("field", "value", "@${equalsIgnoreCase('value')}@", context);
    }
    
    @Test
    public void testResolveCustomValidationMatcher() {
        reset(validationMatcher);
        
        validationMatcher.validate("field", "value", "value");
        expectLastCall().times(3);
        
        replay(validationMatcher);
        
        ValidationMatcherUtils.resolveValidationMatcher("field", "value", "@foo:customMatcher('value')@", context);
        ValidationMatcherUtils.resolveValidationMatcher("field", "value", "@foo:customMatcher(value)@", context);
        ValidationMatcherUtils.resolveValidationMatcher("field", "value", "@${foo:customMatcher('value')}@", context);
        
        verify(validationMatcher);
    }
}
