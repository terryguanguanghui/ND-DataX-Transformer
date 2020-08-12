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

import com.alibaba.datax.transport.transformer.desensitization.desensitizer.BankCardNumberDesensitizer;
import com.alibaba.datax.transport.transformer.desensitization.desensitizer.Condition;
import com.alibaba.datax.transport.transformer.desensitization.desensitizer.Desensitizer;

import java.lang.annotation.*;

/**
 * 银行卡号码敏感标记注解，默认的脱敏规则：擦除目标对象中除了最后四位字符以外的所有字符。
 * <p><strong>注意：默认的脱敏器只会处理{@link String}类型的对象，
 * 并且脱敏时不会校验目标对象的合法性，请确保目标对象是合法的银行卡号码，
 * 否则会抛出任何可能的 {@link RuntimeException}。</strong></p>
 *
 *
 */
@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SensitiveAnnotation
public @interface BankCardNumberSensitive {

    /**
     * @return 处理被 {@link BankCardNumberSensitive}标记的对象脱敏器，注意银行卡号码类型可能为数字类型，
     * 所以此处的脱敏器支持的类型并没有作限制，可以自定义子类重写默认的处理逻辑。
     */
    Class<? extends Desensitizer<?, BankCardNumberSensitive>> desensitizer() default BankCardNumberDesensitizer.class;

    /**
     * @return 敏感信息在原字符序列中的起始偏移
     */
    int startOffset() default 0;

    /**
     * @return 敏感信息在原字符序列中的结束偏移
     */
    int endOffset() default 4;

    /**
     * @return 正则表达式匹配的敏感信息，如果regexp不为{@code ""}的话则会
     * 忽略{@link BankCardNumberSensitive#startOffset()}和{@link BankCardNumberSensitive#endOffset()}的值
     */
    String regexp() default "";

    /**
     * @return 敏感信息替换后的占位符
     */
    char placeholder() default '*';

    /**
     * @return 是否需要对目标对象进行脱敏的条件
     */
    Class<? extends Condition<?>> condition() default AlwaysTrue.class;

    class AlwaysTrue implements Condition<Object> {

        @Override
        public boolean required(Object target) {
            return true;
        }
    }
}
