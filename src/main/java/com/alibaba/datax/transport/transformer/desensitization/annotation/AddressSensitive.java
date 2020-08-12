package com.alibaba.datax.transport.transformer.desensitization.annotation;
import com.alibaba.datax.transport.transformer.desensitization.desensitizer.AddressDesensitizer;
import com.alibaba.datax.transport.transformer.desensitization.desensitizer.Condition;
import com.alibaba.datax.transport.transformer.desensitization.desensitizer.Desensitizer;

import java.lang.annotation.*;

/**
 * 地址敏感标记注解。默认的脱敏规则：隐藏具体地址信息，保留省市区等维度信息。
 * <p><strong>注意：默认的脱敏器是{@link AddressDesensitizer}，该脱敏器只会处理{@link String}
 * 类型的对象，并且脱敏时不会校验目标对象的合法性，请确保目标对象是合法的地址，
 * 否则会抛出任何可能的 {@link RuntimeException}。</strong></p>
 *
 * @author ND
 */
@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SensitiveAnnotation
public @interface AddressSensitive {
    /**
     * @return 处理被 {@link AddressSensitive}标记的对象脱敏器，可以自定义子类重写默认的处理逻辑。
     */
    Class<? extends Desensitizer<? extends CharSequence, AddressSensitive>> desensitizer() default AddressDesensitizer.class;

    /**
     * @return 敏感信息在原字符序列中的起始偏移
     */
    int startOffset() default 8;

    /**
     * @return 敏感信息在原字符序列中的结束偏移
     */
    int endOffset() default 0;

    /**
     * @return 正则表达式匹配的敏感信息，如果regexp不为{@code ""}的话则会
     * 忽略{@link AddressSensitive#startOffset()}和{@link AddressSensitive#endOffset()}的值
     */
    String regexp() default "";

    /**
     * @return 敏感信息替换后的占位符
     */
    char placeholder() default '*';

    /**
     * @return 是否需要对目标对象进行脱敏的条件
     */
    Class<? extends Condition<? extends CharSequence>> condition() default AddressSensitive.AlwaysTrue.class;

    class AlwaysTrue implements Condition<CharSequence> {

        @Override
        public boolean required(CharSequence target) {
            return true;
        }
    }
}
