/*
 * Copyright 2019 the original author or authors.
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
package com.alibaba.datax.transport.transformer.desensitization.resolver;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.WildcardType;

/**
 * {@link WildcardType}类型值解析器
 *
 *
 */
public class WildcardTypeResolver implements TypeResolver<Object, AnnotatedWildcardType> {

    @Override
    public Object resolve(Object value, AnnotatedWildcardType annotatedWildcardType) {
        AnnotatedType[] annotatedUpperBounds = annotatedWildcardType.getAnnotatedUpperBounds();
        AnnotatedType[] annotatedBounds = annotatedUpperBounds.length == 0 ? annotatedWildcardType.getAnnotatedLowerBounds() : annotatedUpperBounds;
        for (AnnotatedType annotatedBound : annotatedBounds) {
            value = TypeResolvers.resolve(value, annotatedBound);
        }
        return value;
    }

    @Override
    public boolean support(Object value, AnnotatedType annotatedType) {
        return value != null && annotatedType instanceof AnnotatedWildcardType;
    }

    @Override
    public int order() {
        return HIGHEST_PRIORITY + 1;
    }
}
