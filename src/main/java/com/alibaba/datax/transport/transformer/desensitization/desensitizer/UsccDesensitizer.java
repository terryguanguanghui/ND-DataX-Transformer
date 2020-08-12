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

import com.alibaba.datax.transport.transformer.desensitization.annotation.UsccSensitive;

/**
 * 统一社会信用代码脱敏器
 *
 *
 */
public class UsccDesensitizer extends AbstractCharSequenceDesensitizer<String, UsccSensitive> {

    @Override
    public String desensitize(String target, UsccSensitive annotation) {
        return required(target, annotation.condition()) ? String.valueOf(desensitize(target, annotation.regexp(), annotation.startOffset(), annotation.endOffset(), annotation.placeholder())) : target;
    }

}
