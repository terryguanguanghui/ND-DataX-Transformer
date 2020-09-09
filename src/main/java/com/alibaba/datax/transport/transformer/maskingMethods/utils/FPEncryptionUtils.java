package com.alibaba.datax.transport.transformer.maskingMethods.utils;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import com.idealista.fpe.config.Alphabet;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;
import com.idealista.fpe.transformer.IntToTextTransformer;
import com.idealista.fpe.transformer.TextToIntTransformer;
import org.apache.commons.lang3.StringUtils;

public class FPEncryptionUtils {

    private static final String SECRET_KEY="nMYQMnSuwX3V2lnNu80AqA==";

    private static final byte[] A_TWEAK_SUFFIX =new byte[0];

    private static final Alphabet EXTEND_ALPHABET=new ExtendAlphabet();

    private static final TextToIntTransformer TEXT_TO_INT_TRANSFORMER = new GenericTransformations(EXTEND_ALPHABET.availableCharacters());

    private static final IntToTextTransformer INT_TO_TEXT_TRANSFORMER = new GenericTransformations(EXTEND_ALPHABET.availableCharacters());

    private static final ThreadLocal<FormatPreservingEncryption> SEED_POOL= ThreadLocal.withInitial(
            () -> FormatPreservingEncryptionBuilder
                    .ff1Implementation()
                    .withDomain(new GenericDomain(EXTEND_ALPHABET,TEXT_TO_INT_TRANSFORMER,INT_TO_TEXT_TRANSFORMER))
                    .withDefaultPseudoRandomFunction(SECRET_KEY.getBytes())
                    .withDefaultLengthRange()
                    .build());

    public static String encrypt(String plainText){
        if(StringUtils.isEmpty(plainText)){
            return null;
        }
        return SEED_POOL.get().encrypt(plainText,A_TWEAK_SUFFIX);
    }

    public static String decrypt(String cipherText){
        if(StringUtils.isEmpty(cipherText)){
            return null;
        }
        return SEED_POOL.get().decrypt(cipherText,A_TWEAK_SUFFIX);
    }

    private static class ExtendAlphabet implements Alphabet{

        private static final char[] NUM_AND_CHARACTER_CHARS = new char[] {
                '1', '2', '3', '4', '5', '6', '7', '8','9','0',
                'A','B','C','D','E','F','G','H','I','J','K','L',
                'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                'a','b','c','d','e','f','g','h','i','j','k','l',
                'm','n','o','p','q','r','s','t','u','v','w','x','y','z','~',
                '!',
                ' ',
                '@',
                '#',
                '$',
                '%',
                '^',
                '&',
                '*',
                '(',
                ')',
                '-',
                '=',
                '_',
                '+',
                '`',
                '[',
                ']',
                '{',
                '}',
                '\\',
                '|',
                ';',
                '\'',
                ':',
                '"',
                '<',
                '>',
                ',',
                '.',
                '?',
                '/'};

        private static final char[] ONLY_NUM=new char[]{
                '1', '2', '3', '4', '5', '6', '7', '8','9','0'
        };

        @Override
        public char[] availableCharacters() {
            return NUM_AND_CHARACTER_CHARS;
        }

        @Override
        public Integer radix() {
            return NUM_AND_CHARACTER_CHARS.length;
        }
    }
}