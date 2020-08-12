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
package com.alibaba.datax.transport.transformer.desensitization.annotation;


import java.lang.annotation.*;

/**
 * 标记注解，表明当前注解是一个敏感注解。用户可以基于此注解扩展自己的敏感注解，
 * 有关如何定义自己的敏感注解可以参考{@link com.alibaba.datax.transport.transformer.desensitization.annotation}包下已存在的敏感注解定义。
 *
 *
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SensitiveAnnotation {
}
