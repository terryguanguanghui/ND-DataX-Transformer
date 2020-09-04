package com.alibaba.datax.transport.transformer.maskingMethods.cryptology;

import com.alibaba.datax.transport.transformer.maskingConfigure.RSAKey;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.encoders.Hex;
import java.io.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * RSA
 */


public class RSAEncryptionImpl extends CryptologyMasking {
    // 填充模式
    final public static int RAW = 2;
    final public static int PKCS1 = 1;

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private KeyPair pair;
    private int keyLength = 1024;


    /**
     * constructor
     * parameter keyLength
     */
    public RSAEncryptionImpl(int keyLength) {
        // generate RSA Key Pair
        this.keyLength = keyLength;
        generateRSAKeyPair();
    }

    /**
     * constructor
     * no parameter
     */
    public RSAEncryptionImpl() {
        // generate RSA Key Pair
        generateRSAKeyPair();
    }

    private void generateRSAKeyPair() {
        try {
            publicKey = RSAKey.getPublicKey();
            privateKey = RSAKey.getPrivateKey();
        } catch (Exception e) {
            System.out.println("Exception in keypair generation. Reason: " + e);
        }
    }

    public void printRSAKeyPair() {
        try {
            KeyPairGenerator rsaKeyGen = KeyPairGenerator.getInstance("RSA");
            // setKeyLength 1024,setCertaintyOfPrime
            rsaKeyGen.initialize(keyLength, new SecureRandom());
            KeyPair Pair = rsaKeyGen.genKeyPair();
            System.out.println((RSAPublicKey) Pair.getPublic());
            RSAPrivateKey priKey = (RSAPrivateKey) Pair.getPrivate();
            RSAPublicKey pubKey = (RSAPublicKey) Pair.getPublic();
        } catch (Exception e) {
            System.out.println("Exception in keypair generation. Reason: " + e);
        }
    }

    /**
     * 使用公钥加密
     *
     * @return
     * @clearBytes
     * @type: padding type
     */
    public byte[] publicEncrypt(byte[] clearBytes, int type) {
        BigInteger mod = publicKey.getModulus();
        // 指数
        BigInteger publicExponent = publicKey.getPublicExponent();
        RSAKeyParameters para = new RSAKeyParameters(false, mod, publicExponent);

        AsymmetricBlockCipher engine = new RSAEngine();
        if (type == PKCS1) {
            engine = new PKCS1Encoding(engine);
        }
        engine.init(true, para);
        try {
            byte[] data = engine.processBlock(clearBytes, 0, clearBytes.length);
            return data;
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
            System.err.println("publicEncrypt engine.processBlock error");
        }
        return null;
    }

    /**
     * 使用公钥解密
     *
     * @param clearBytes
     * @param type
     * @return
     */
    public byte[] publicDecrypt(byte[] clearBytes, int type) {
        BigInteger mod = publicKey.getModulus();
        BigInteger pubExp = publicKey.getPublicExponent();

        RSAKeyParameters para = new RSAKeyParameters(false, mod, pubExp);
        AsymmetricBlockCipher engine = new RSAEngine();
        if (type == PKCS1) {
            engine = new PKCS1Encoding(engine);
        }
        engine.init(false, para);
        try {
            byte[] data = engine.processBlock(clearBytes, 0, clearBytes.length);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("publicDecrypt engine.processBlock exception");
        }
        return null;
    }

    /**
     * 使用私钥加密
     *
     * @param encodedBytes
     * @param type
     * @return
     */
    public byte[] privateDecrypt(byte[] encodedBytes, int type) {
        RSAPrivateCrtKey prvCrtKey = (RSAPrivateCrtKey) privateKey;
        BigInteger mod = prvCrtKey.getModulus();
        BigInteger pubExp = prvCrtKey.getPublicExponent();
        BigInteger privExp = prvCrtKey.getPrivateExponent();
        BigInteger pExp = prvCrtKey.getPrimeExponentP();
        BigInteger qExp = prvCrtKey.getPrimeExponentQ();
        BigInteger p = prvCrtKey.getPrimeP();
        BigInteger q = prvCrtKey.getPrimeQ();
        BigInteger crtCoef = prvCrtKey.getCrtCoefficient();

        RSAKeyParameters para = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);

        AsymmetricBlockCipher engine = new RSAEngine();
        if (type == PKCS1) {
            engine = new PKCS1Encoding(engine);
        }

        engine.init(false, para);
        try {
            byte[] data = engine.processBlock(encodedBytes, 0, encodedBytes.length);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("privateDecrypt engine.processBlock error");
        }
        return null;
    }

    /**
     * 使用私钥解密
     *
     * @param encodedBytes
     * @param type
     * @return
     */
    public byte[] privateEncrypt(byte[] encodedBytes, int type) {
        RSAPrivateCrtKey prvCrtKey = (RSAPrivateCrtKey) privateKey;
        BigInteger mod = prvCrtKey.getModulus();
        BigInteger pubExp = prvCrtKey.getPublicExponent();
        BigInteger privExp = prvCrtKey.getPrivateExponent();
        BigInteger pExp = prvCrtKey.getPrimeExponentP();
        BigInteger qExp = prvCrtKey.getPrimeExponentQ();
        BigInteger p = prvCrtKey.getPrimeP();
        BigInteger q = prvCrtKey.getPrimeQ();
        BigInteger crtCoef = prvCrtKey.getCrtCoefficient();
        RSAKeyParameters para = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);
        AsymmetricBlockCipher engine = new RSAEngine();
        if (type == PKCS1) {
            engine = new PKCS1Encoding(engine);
        }
        engine.init(true, para);
        try {
            byte[] data = engine.processBlock(encodedBytes, 0, encodedBytes.length);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("privateEncrypt engine.processBlock error");
        }
        return null;
    }

    public String changeBytesToString(byte[] data) {
        return new String(Hex.encode(data));
    }

    //override from AbstractMasking
    @Override
    public double execute(double epsilon) throws Exception {
        return -1;
    }

    public String execute(String originData, int type) throws NoSuchAlgorithmException {
        byte[] cipher = publicEncrypt(originData.getBytes(), type);
        return changeBytesToString(cipher);
    }

    public List<String> execute(List<String> originData) throws Exception {
        List<String> cipherData = new ArrayList<String>();
        for (String str : originData) {
            String cipherStr = changeBytesToString(publicEncrypt(str.getBytes(), 1));
            cipherData.add(cipherStr);
        }
        return cipherData;
    }


    public String executeWithPublicDecrypt(String originData, int type) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BigInteger origin = new BigInteger(originData, 16);
        byte[] cipher = publicDecrypt(origin.toByteArray(), type);
        String decoded = changeBytesToString(cipher);
        BigInteger raw_code = new BigInteger(decoded, 16);
        String result = new String(raw_code.toByteArray(), "ascii");
        return result;
    }

    public String executeWithPublicEncrypt(String originData, int type) throws NoSuchAlgorithmException {
        byte[] cipher = publicEncrypt(originData.getBytes(), type);
        return changeBytesToString(cipher);
    }

    public String executeWithPrivateDecrypt(String originData, int type) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        BigInteger origin = new BigInteger(originData, 16);
        byte[] cipher = privateDecrypt(origin.toByteArray(), type);
        String decoded = changeBytesToString(cipher);
        BigInteger raw_code = new BigInteger(decoded, 16);
        String result = new String(raw_code.toByteArray(), "ascii");
        return result;
    }

    public String executeWithPrivateEncrypt(String originData, int type) throws NoSuchAlgorithmException {
        byte[] cipher = privateEncrypt(originData.getBytes(), type);
        return changeBytesToString(cipher);
    }

    @Override
    public void mask() throws Exception {
    }

    public static void main(String[] args) {
        try {
            String content = new String("C");
            RSAEncryptionImpl rsatest = new RSAEncryptionImpl();
            String result = rsatest.execute(content, RAW);
            System.out.println(result);
            int PaddingType = RAW;
            System.out.println("RSA加密解密\n数据加密前：" + content);
            System.out.println("将原始数据转换为16进制表示的字串：" + rsatest.changeBytesToString(content.getBytes()));
            String masked = rsatest.executeWithPrivateEncrypt(content, PaddingType);
            System.out.println("私钥加密后：" + masked);
            String decoded = rsatest.executeWithPublicDecrypt(masked, PaddingType);
            System.out.println("解密后：" + decoded);
            masked = rsatest.executeWithPublicEncrypt(content, PaddingType);
            System.out.println("公钥加密后：" + masked);
            decoded = rsatest.executeWithPrivateDecrypt(masked, PaddingType);
            System.out.println("私钥解密后：" + decoded);
        } catch (Exception e) {
            System.out.println(e);
        }


        //ASCII字符长度不能超过117，原因不得而知。
        String content = new String("BKG2I");
        RSAEncryptionImpl rsatest = new RSAEncryptionImpl();
        System.out.println("String加密前：" + content);
        System.out.println("字符串：" + rsatest.changeBytesToString(content.getBytes()));
        byte[] cipher = rsatest.publicEncrypt(content.getBytes(), RAW);
        System.out.println("公钥加密后：" + rsatest.changeBytesToString(cipher));
        byte[] plain = rsatest.privateDecrypt(cipher, RAW);
        System.out.println("解密后：" + new String(plain));
    }
}