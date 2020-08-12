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

package model;

import com.alibaba.datax.transport.transformer.desensitization.annotation.ChineseNameSensitive;
import com.alibaba.datax.transport.transformer.desensitization.annotation.IdCardNumberSensitive;
import com.alibaba.datax.transport.transformer.desensitization.annotation.PhoneNumberSensitive;

/**
 *
 */
public class Mother extends Parent {

    @ChineseNameSensitive(regexp = "婷")
    private String name = "张婷婷";

    @PhoneNumberSensitive
    private String phoneNumber = "12345678912";

    @IdCardNumberSensitive
    private String idCardNumber = "321181199301096002";

    @Override
    public String toString() {
        return "Mother{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", idCardNumber='" + idCardNumber + '\'' +
                '}';
    }
}
