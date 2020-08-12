package com.alibaba.datax.transport.transformer.maskingMethods.anonymity;

import java.util.Date;

public interface AnonyMasker {
    String mask(String origin) throws Exception;
    int mask(int origin) throws Exception;
    long mask(long origin) throws Exception;
    double mask(double origin) throws Exception;
    boolean mask(boolean origin) throws Exception;
    Date mask(Date origin) throws Exception;

}
