package com.alibaba.datax.transport.transformer.hide;

import java.util.stream.IntStream;

public class HidingDesensitizer {

    private final static char PLACE_HOLDER = '*';

    /**
     * 基于位置偏移脱敏
     *
     * @param target      目标字符序列对象
     * @param start       敏感信息在原字符序列中的起始信息
     * @param middle      敏感信息在原字符序列中的中间信息
     * @param end         敏感信息在原字符序列中的结束信息
     * @return 脱敏后的新字符序列对象的字符数组
     */
    public static String desensitize(String target, String start,String middle, String end) {
        String[] startArr = start.split(":");
        int startL = Integer.parseInt(startArr[0]);
        boolean startIsMasked = Integer.parseInt(startArr[1]) == 1;

        String[] middleArr = middle.split(":");
        int middleL = Integer.parseInt(middleArr[0]);
        boolean middleIsMasked = Integer.parseInt(middleArr[1]) == 1;

        String[] endArr = end.split(":");
        int endL = Integer.parseInt(endArr[0]);
        boolean endIsMasked = Integer.parseInt(endArr[1]) == 1;

        check(startL, middleL, endL, target);
        char[] chars = chars(target);
        replace(chars, startL, startIsMasked, middleL, middleIsMasked, endL, endIsMasked);
        return String.valueOf(chars);
    }

    /**
     * 替换字符序列中的敏感信息
     *
     * @param chars       字符序列对应的字符数组
     * @param start       敏感信息在字符序列中的起始长度
     * @param middle      敏感信息在字符序列中的中间长度
     * @param end         敏感信息在字符序列中的结束长度
     */
    private static void replace(char[] chars, int start, boolean startIsMasked, int middle, boolean middleIsMasked, int end, boolean endIsMasked) {
        if(startIsMasked){
            int index = 0;
            if(start > chars.length){
                start = chars.length;
            }
            while (index < start){
                chars[index++] = PLACE_HOLDER;
            }
        }

        if(middleIsMasked){
            int index = start;
            int endIndex = start + middle;
            if(endIndex > chars.length){
                endIndex = chars.length;
                chars[chars.length-1] = PLACE_HOLDER;
            }
            while (index < endIndex) {
                chars[index++] = PLACE_HOLDER;
            }

        }

        if(endIsMasked){
            int index = chars.length-end;
            if(index < 0){
                index = 0;
            }
            int endIndex = chars.length;
            while (index < endIndex) {
                chars[index++] = PLACE_HOLDER;
            }
        }

    }

    /**
     * 校验起始偏移和结束偏移的合法性
     *
     * @param startOffset  敏感信息在原字符序列中的起始长度
     * @param middleOffset 敏感信息在原字符序列中的中间长度
     * @param endOffset    敏感信息在原字符序列中的结束长度
     * @param target       原字符序列
     */
    private static void check(int startOffset,int middleOffset, int endOffset, String target) {
        if (startOffset < 0 || middleOffset < 0 ||
                endOffset < 0 ) {
            throw new IllegalArgumentException("startOffset: " + startOffset + ", " + "middleOffset: " + middleOffset + ", " + "endOffset: " + endOffset + ", " + "target: " + target);
        }
    }

    /**
     * 将字符序列转换为字符数组
     *
     * @param target 字符序列对象
     * @return 字符序列对象所代表的字符数组
     */
    private static char[] chars(String target) {
        char[] chars = new char[target.length()];
        IntStream.range(0, target.length()).forEach(i -> chars[i] = target.charAt(i));
        return chars;
    }

    public static void main(String[] args) {
        String t = desensitize("127.0.0.1","9:0", "2:1", "80:0");
        System.out.println(t);
    }
}
