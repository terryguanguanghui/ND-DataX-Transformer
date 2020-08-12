package com.alibaba.datax.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.core.transport.transformer.TransformerErrorCode;
import com.alibaba.datax.transformer.Transformer;
import com.alibaba.datax.transport.transformer.desensitization.annotation.BankCardNumberSensitive;
import com.alibaba.datax.transport.transformer.desensitization.Sensitive;
import java.util.Arrays;

public class BankCardNumberTransformer extends Transformer {

    public BankCardNumberTransformer() {
        setTransformerName("dx_bankcard_number");
        System.out.println("银行卡号码脱敏");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        int columnIndex;
        try {
            if (paras.length < 1) {
                throw new RuntimeException("dx_bankcard_number transformer 缺少参数");
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
        @BankCardNumberSensitive
        private String item;

        public String getItem() {
            return item;
        }

        public Item(@BankCardNumberSensitive String item) {
            this.item = item;
        }
    }
}
