package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.maskingMethods.cryptology.AESEncryptionImpl;
import com.alibaba.datax.transport.transformer.maskingMethods.cryptology.FPEEncryptionImpl;
import com.alibaba.datax.transport.transformer.maskingMethods.cryptology.RSAEncryptionImpl;

import java.util.Arrays;

/**
 * Mask data to protect privacy.
 */

public class MaskTransformer extends Transformer {

    public  MaskTransformer(){ setTransformerName("dx_cryp");}

    @Override
    public Record evaluate(Record record, Object... paras) {
        String maskMethodId = "";
        String key = null;
        int columnIndex;

        try {
            if (paras.length < 2) {
                throw new RuntimeException("加密脱敏 缺少参数！");
            }

            maskMethodId = String.valueOf((String) paras[1]);
            columnIndex = (Integer) paras[0];
            if(paras.length > 2){
                key = String.valueOf((String) paras[2]);
            }

        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }
        Column column = record.getColumn(columnIndex);
        try {
            String oriValue = column.asString();
            if(oriValue == null){
                return  record;
            }
            if(maskMethodId.equals("AES")){
                String newValue;
                AESEncryptionImpl masker = new AESEncryptionImpl();
                newValue = masker.execute(oriValue);
                record.setColumn(columnIndex, new StringColumn(newValue));
            }
            else if(maskMethodId.equals("FPE")){
                FPEEncryptionImpl fpeEncryption = new FPEEncryptionImpl();
                String newValue = fpeEncryption.execute(oriValue);
                record.setColumn(columnIndex, new StringColumn(newValue));
            }
            else if(maskMethodId.equals("RSA")){
                RSAEncryptionImpl masker = new RSAEncryptionImpl();
                String newValue = "";
                if (key.equals("private_decrypt")){
                    newValue = masker.executeWithPrivateDecrypt(oriValue);
                }
                else if(key.equals("private_encrypt")){
                    newValue = masker.executeWithPrivateEncrypt(oriValue);
                }
                else if(key.equals("public_decrypt")){
                    newValue = masker.executeWithPublicDecrypt(oriValue);
                }
                else if(key.equals("public_encrypt")){
                    newValue = masker.executeWithPublicEncrypt(oriValue);
                }
                record.setColumn(columnIndex, new StringColumn(newValue));
            }
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(),e);
        }
        return record;
    }

}
