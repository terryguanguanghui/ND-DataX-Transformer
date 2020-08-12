package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.DoubleColumn;
import com.alibaba.datax.common.element.LongColumn;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.maskingMethods.anonymity.EnumerateMasker;
import java.util.Arrays;

public class EnumerateTransformer extends Transformer {

    public EnumerateTransformer() {
        setTransformerName("dx_enum");
        System.out.println("整形数据");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        String key;
        int columnIndex;
        try {
            if (paras.length < 2) {
                throw new RuntimeException("dx_enum transformer缺少参数");
            }
            columnIndex = (Integer) paras[0];
            key = String.valueOf(paras[1]);
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }
        Column column = record.getColumn(columnIndex);
        try {
            String oriValue = column.asString();
            if(oriValue == null){
                return  record;
            }
            int offset = Integer.parseInt(key);
            long newValue = EnumerateMasker.mask(column.asLong(), offset);
            record.setColumn(columnIndex, new LongColumn(newValue));
        } catch (Exception e){
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(),e);
        }
        return record;
    }
}
