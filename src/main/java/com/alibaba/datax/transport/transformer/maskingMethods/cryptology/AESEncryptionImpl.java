package com.alibaba.datax.transport.transformer.maskingMethods.cryptology;

import com.alibaba.datax.transport.transformer.maskingMethods.utils.AESCipher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ggh
 */
public class AESEncryptionImpl extends CryptologyMasking{

    @Override
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
        return AESCipher.encrypt(originData);
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
            String cipherStr = AESCipher.encrypt(str);
            cipherData.add(cipherStr);
        }
        return cipherData;
    }


}
