package com.alibaba.datax.transport.transformer.maskingMethods;
import java.util.Map;

/**
 * Abstract masking methods.
 *
 */

public abstract class AbstractMasking implements Masking{

    /**
     * LOG
     */

    /**
     * Parameter
     */
    protected double d;

    /**
     * Execute model.
     *
     * @throws Exception if error occurs during executing model.
     */
    public abstract double execute(double d) throws Exception;



    /**
     * Mask.
     *
     * @throws Exception if error occurs during masking
     */
    public void mask(Map<String, Object> map) throws Exception {
        // TODO: extract other contexts.
        execute(d);
        System.out.println("Job Execute completed.");
        evaluate();
        System.out.println("Job Evaluate completed.");
        cleanup();
    }

    /**
     * Evaluate.
     *
     * @return
     * @throws Exception
     */
    public double evaluate() throws Exception {
        // TODO: extract other contexts.
        return 1;
    }

    /**
     * Cleanup.
     *
     * @throws Exception if error occurs during cleanup
     */
    protected void cleanup() throws Exception {
        // TODO: cleanup.
    }
}