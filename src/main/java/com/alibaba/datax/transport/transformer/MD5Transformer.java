package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.maskingMethods.irreversibleInterference.MD5EncryptionImpl;
import java.util.Arrays;

public class MD5Transformer extends Transformer {

    public MD5Transformer(){
        setTransformerName("dx_md5");
        System.out.println("不可逆的md5hash摘要脱敏");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        int columnIndex;

        try {
            if (paras.length < 1) {
                throw new RuntimeException("dx_md5 transformer缺少参数");
            }
            columnIndex = (Integer) paras[0];
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }
        Column column = record.getColumn(columnIndex);
        try {
            String oriValue = column.asString();
            if (oriValue == null) {
                return record;
            }
            if(column.getType() == Column.Type.STRING) {
                MD5EncryptionImpl masker = new MD5EncryptionImpl();
                String newValue = masker.execute(oriValue);
                record.setColumn(columnIndex, new StringColumn(newValue));
            }
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(), e);
        }
        return record;
    }
}
