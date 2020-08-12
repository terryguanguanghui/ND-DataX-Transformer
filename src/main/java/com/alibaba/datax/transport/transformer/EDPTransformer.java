package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.DoubleColumn;
import com.alibaba.datax.common.element.LongColumn;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.maskingMethods.differentialPrivacy.EpsilonDifferentialPrivacyImpl;
import java.util.Arrays;

public class EDPTransformer extends Transformer {

    public EDPTransformer(){
        setTransformerName("dx_edp");
        System.out.println("差分隐私脱敏");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        String key;
        int columnIndex;
        try {
            if (paras.length < 2) {
                throw new RuntimeException("dx_edp transformer缺少参数");
            }
            columnIndex = (Integer) paras[0];
            key = (String) paras[1];
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }
        Column column = record.getColumn(columnIndex);
        try {
            String oriValue = column.asString();
            if (oriValue == null) {
                return record;
            }
            if(column.getType() == Column.Type.DOUBLE) {
                double newValue;
                EpsilonDifferentialPrivacyImpl masker = new EpsilonDifferentialPrivacyImpl();
                newValue = masker.maskOne(Double.parseDouble(oriValue), Double.parseDouble(key));
                record.setColumn(columnIndex, new DoubleColumn(newValue));
            }
            else if(column.getType() == Column.Type.LONG){
                long newValue;
                EpsilonDifferentialPrivacyImpl masker = new EpsilonDifferentialPrivacyImpl();
                newValue = masker.maskOne(Long.valueOf(oriValue), Double.parseDouble(key));
                record.setColumn(columnIndex, new LongColumn(newValue));
            }
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(), e);
        }
        return record;
    }
}
