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

package com.alibaba.datax.transport.transformer.desensitization.desensitizer;

import com.alibaba.datax.transport.transformer.desensitization.annotation.CharSequenceSensitive;

/**
 * {@link StringBuffer}类型对象脱敏器
 *
 *
 */
public class StringBufferDesensitizer extends AbstractCharSequenceDesensitizer<StringBuffer, CharSequenceSensitive> {

    @Override
    public StringBuffer desensitize(StringBuffer target, CharSequenceSensitive annotation) {
        return required(target, annotation.condition()) ? new StringBuffer().append(desensitize(target, annotation.regexp(), annotation.startOffset(), annotation.endOffset(), annotation.placeholder())) : target;
    }

}
