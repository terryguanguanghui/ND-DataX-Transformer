package com.alibaba.datax.transport.transformer.desensitization.desensitizer;

import com.alibaba.datax.transport.transformer.desensitization.annotation.EmailSensitive;

/**
 * 邮箱脱敏器
 *
 *
 */
public class EmailDesensitizer extends AbstractCharSequenceDesensitizer<String, EmailSensitive> {

    @Override
    public String desensitize(String target, EmailSensitive annotation) {
        return required(target, annotation.condition()) ? String.valueOf(desensitize(target, annotation.regexp(), annotation.startOffset(), annotation.endOffset(), annotation.placeholder())) : target;
    }

}
