package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.*;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.maskingMethods.anonymity.FloorMasker;
import java.util.Arrays;
import java.util.Date;

public class FloorTransformer extends Transformer {

    public FloorTransformer() {
        setTransformerName("dx_floor");
        System.out.println("对整数或浮点数或者日期向下取整脱敏");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        String key;
        int columnIndex;

        try {
            if (paras.length < 1) {
                throw new RuntimeException("Floor transformer缺少参数");
            }
            columnIndex = (Integer) paras[0];
            key = String.valueOf(paras[1]);
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }
        Column column = record.getColumn(columnIndex);
        try {
            String oriValue = column.asString();
            if (oriValue == null) {
                return record;
            }
            if (column.getType() == Column.Type.DATE) {
                Date newValue = FloorMasker.mask(column.asDate(), key);
                record.setColumn(columnIndex, new DateColumn(newValue));
            } else if (column.getType() == Column.Type.LONG || column.getType() == Column.Type.INT) {
                int mod = 0;
                if (key.isEmpty()){
                    mod = 10;
                }
                else{
                    mod = Integer.valueOf(key);
                }
                long newValue = FloorMasker.mask(column.asLong(), mod);
                record.setColumn(columnIndex, new LongColumn(newValue));
            } else if (column.getType() == Column.Type.DOUBLE) {
                Double newValue = FloorMasker.mask(column.asDouble());
                record.setColumn(columnIndex, new DoubleColumn(newValue));
            }
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(), e);
        }
        return record;
    }
}
