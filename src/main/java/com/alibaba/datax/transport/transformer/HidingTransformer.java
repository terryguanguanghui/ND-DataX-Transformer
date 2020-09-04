package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import java.util.Arrays;
import com.alibaba.datax.transport.transformer.hide.HidingDesensitizer;
/**
 * @author ND
 */
public class HidingTransformer extends Transformer {

    public HidingTransformer() {
        setTransformerName("dx_hiding_str");
        System.out.println("掩盖脱敏");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        int columnIndex;
        String startModle;
        String middleModle;
        String endModle;
        try {
            if (paras.length < 3) {
                throw new RuntimeException("dx_hiding_str transformer 缺少参数 ");
            }
            columnIndex = (Integer) paras[0];
            startModle = String.valueOf((String) paras[1]);
            middleModle = String.valueOf((String) paras[2]);
            endModle = String.valueOf((String) paras[3]);
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }

        try {
            Column column = record.getColumn(columnIndex);
            String oriValue = column.asString();
            if(oriValue == null){
                return  record;
            }
            String newValue = HidingDesensitizer.desensitize(oriValue, startModle, middleModle, endModle);
            record.setColumn(columnIndex, new StringColumn(newValue));
        }catch (Exception e){
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(),e);
        }
        return record;
    }

}
