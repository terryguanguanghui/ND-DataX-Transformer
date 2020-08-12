package com.alibaba.datax.transport.transformer.maskingMethods;

/**
 * General masking.
 *
 */
public interface Masking {

    // TODO: Extract other methods.

    /**
     * Mask.
     *
     * @throws Exception if error occurs during masking
     */
    void mask() throws Exception;
}