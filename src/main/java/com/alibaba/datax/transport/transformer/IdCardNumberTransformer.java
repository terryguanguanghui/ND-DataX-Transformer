package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.desensitization.Sensitive;
import com.alibaba.datax.transport.transformer.desensitization.annotation.IdCardNumberSensitive;

import java.util.Arrays;

public class IdCardNumberTransformer extends Transformer {

    public IdCardNumberTransformer() {
        setTransformerName("dx_idCard");
        System.out.println("身份证号码脱敏");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        int columnIndex;
        try {
            if (paras.length < 1) {
                throw new RuntimeException("dx_idCard transformer 缺少参数");
            }
            columnIndex = (Integer) paras[0];
        } catch (Exception e) {
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage());
        }

        try {
            Column column = record.getColumn(columnIndex);
            String oriValue = column.asString();
            if(oriValue == null){
                return  record;
            }
            Item oriObject = new Item(oriValue);
            Item newOject = Sensitive.desensitize(oriObject);
            String newValue = newOject.getItem();
            record.setColumn(columnIndex, new StringColumn(newValue));
        }catch (Exception e){
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_RUN_EXCEPTION, e.getMessage(),e);
        }
        return record;
    }
    class Item{
        @IdCardNumberSensitive
        private String item;

        public String getItem() {
            return item;
        }

        public Item(@IdCardNumberSensitive String item) {
            this.item = item;
        }
    }

}
