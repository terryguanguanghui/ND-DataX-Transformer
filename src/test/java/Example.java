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

import model.Child;
import org.junit.Test;
import com.alibaba.datax.transport.transformer.desensitization.annotation.ChineseNameSensitive;
import com.alibaba.datax.transport.transformer.desensitization.annotation.EmailSensitive;
import com.alibaba.datax.transport.transformer.desensitization.support.TypeToken;
import com.alibaba.datax.transport.transformer.desensitization.Sensitive;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Example {


    /**
     * 对于单个值类型的脱敏，脱敏处理必须放在静态代码块中执行，不能放在对象的实例方法中执行，
     * 这是由于jdk解析注解的一个bug导致的。
     *
     * @see <a href="http://stackoverflow.com/questions/39952812/why-annotation-on-generic-type-argument-is-not-visible-for-nested-type"></a>
     */
    private static void desensitize() {
        // 单个值
        System.out.println("值脱敏：" + Sensitive.desensitize("123456@qq.com", new TypeToken<@EmailSensitive String>() {
        }));

        // Collection
        System.out.println("集合值脱敏：" + Sensitive.desensitize(Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()),
                new TypeToken<List<@EmailSensitive String>>() {
                }));

        // Array
        System.out.println("数组值脱敏：" + Arrays.toString(Sensitive.desensitize(new String[]{"123456@qq.com", "1234567@qq.com", "12345678@qq.com"},
                new TypeToken<@EmailSensitive String[]>() {
                })));

        // Map
        System.out.println("Map值脱敏：" + Sensitive.desensitize(Stream.of("张三", "李四", "小明").collect(Collectors.toMap(s -> s, s -> "123456@qq.com")),
                new TypeToken<Map<@ChineseNameSensitive String, @EmailSensitive String>>() {
                }));
    }

    /**
     * 这是一个错误的示例，对于单个值脱敏，放在实例方法中是不生效的，必须将脱敏代码放在静态方法中执行。这是由于jdk解析注解的一个bug导致的。
     */
    public void wrongDesensitizeValue() {
        System.out.println("不要在实例方法中脱敏单个值：" + Sensitive.desensitize("123456@qq.com", new TypeToken<@EmailSensitive String>() {
        }));
    }

    /**
     * 对象内部域值脱敏
     */
    @Test
    public void desensitizeObject() {
        Child<?> before = new Child<>();
        System.out.println("脱敏前原对象：" + before);
        Child<?> after = Sensitive.desensitize(before);
        System.out.println("脱敏后的新对象：" + after);
        System.out.println("脱敏后原对象：" + before);
    }

    /**
     * 单个值脱敏，脱敏代码必须放到静态方法中执行。
     */
    @Test
    public void desensitizeValue() {
        desensitize();
    }

    public static void main(String[] args) {
        new Example().wrongDesensitizeValue();
        // 单个值
        System.out.println("值脱敏：" + Sensitive.desensitize("123456@qq.com", new TypeToken<@EmailSensitive String>() {
        }));
    }

}
