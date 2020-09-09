package com.alibaba.datax.transport.transformer.maskingMethods.cryptology;

import com.alibaba.datax.transport.transformer.maskingMethods.utils.AESCipher;
import com.alibaba.datax.transport.transformer.maskingMethods.utils.FPEncryptionUtils;

import java.util.ArrayList;
import java.util.List;

public class FPEEncryptionImpl {

    public double execute(double d) throws Exception {
        return 0;
    }

    /**
     * 对参数String进行对称加密
     * @param originData
     * @return
     * @throws Exception
     */
    public String execute(String originData) throws Exception {
        return FPEncryptionUtils.encrypt(originData);
    }

    /**
     * 对 List<String> originData 进行对称加密
     * @param originData
     * @return
     * @throws Exception
     */
    public List<String> execute(List<String> originData) throws Exception {
        List<String> cipherData = new ArrayList<String>();
        for (String str : originData) {
            String cipherStr = FPEncryptionUtils.encrypt(str);
            cipherData.add(cipherStr);
        }
        return cipherData;
    }

}
