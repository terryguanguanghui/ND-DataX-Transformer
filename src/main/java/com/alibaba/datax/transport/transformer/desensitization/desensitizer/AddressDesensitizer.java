package com.alibaba.datax.transport.transformer.desensitization.desensitizer;

import com.alibaba.datax.transport.transformer.desensitization.annotation.AddressSensitive;

/**
 * 地址脱敏器
 *
 *
 */
public class AddressDesensitizer extends AbstractCharSequenceDesensitizer<String, AddressSensitive> {

    @Override
    public String desensitize(String target, AddressSensitive annotation) {
        return required(target, annotation.condition()) ? String.valueOf(desensitize(target, annotation.regexp(), annotation.startOffset(), annotation.endOffset(), annotation.placeholder())) : target;
    }

}
