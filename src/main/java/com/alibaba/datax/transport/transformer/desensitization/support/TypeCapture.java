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

package com.alibaba.datax.transport.transformer.desensitization.support;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 捕获{@link T}运行时被注解的类型
 *
 * @param <T> 需要捕获的对象类型
 *
 */
abstract class TypeCapture<T> {

    /**
     * @return {@link T}运行时被注解的类型
     */
    AnnotatedType capture() {
        Class<?> clazz = getClass();
        Type superclass = clazz.getGenericSuperclass();
        if (!(superclass instanceof ParameterizedType)) {
            throw new IllegalArgumentException(String.format("%s必须是参数化类型", superclass));
        }
        return ((AnnotatedParameterizedType) clazz.getAnnotatedSuperclass()).getAnnotatedActualTypeArguments()[0];
    }

}
