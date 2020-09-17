import com.alibaba.datax.transport.transformer.maskingMethods.cryptology.*;
import com.alibaba.datax.transport.transformer.maskingMethods.differentialPrivacy.EpsilonDifferentialPrivacyImpl;
import com.alibaba.datax.transport.transformer.maskingMethods.irreversibleInterference.MD5EncryptionImpl;
import com.alibaba.datax.transport.transformer.maskingMethods.utils.FPEncryptionUtils;
import org.junit.Test;

public class TestMasking {

    private String originStr = "你好世界！";
    private Long originDouble = 67L;


    @Test
    public void testEDP() {

        try {
            long epsilon = 100;
            EpsilonDifferentialPrivacyImpl masker = new EpsilonDifferentialPrivacyImpl();
            long result = masker.maskOne(originDouble, epsilon);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testRSA() {

        String rsacontent = "" ;
        try {
            RSAEncryptionImpl masker = new RSAEncryptionImpl();
            String encryptkey = masker.executeWithPrivateEncrypt(rsacontent);
            System.out.println("私钥加密后：" + encryptkey);
            System.out.println("公钥解密后：" + masker.executeWithPrivateDecrypt(encryptkey));

            String encryptkey1 = masker.executeWithPublicEncrypt(rsacontent);
            System.out.println("私钥加密后：" + encryptkey1);
            System.out.println("公钥解密后：" + masker.executeWithPublicDecrypt("X6TqCWqN+ZcdhLJQfuJSy9hzjKMoZ7j3Ew3FCekWN2iJUov+3TVXmlK0nu6JGUG5P7XAzgzLksf1wS8hPuIc1Ubyp45IxmJDI+9vUrkaxWOrJjbscp8oHNvEItMEZvwi6Hz5VuOHJdYGYlv7uosqP2IBzOMUmDvr6KMW8WD+xVc="));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAES() {
        AESEncryptionImpl masker = new AESEncryptionImpl();
        try {
            String str = "";
            System.out.println(str);
            String result = masker.execute(str);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testMD5() {
        MD5EncryptionImpl masker = new MD5EncryptionImpl();
        try {
            String result = masker.execute(originStr);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Test
    public void testFPE() {
        try {
            String result = FPEncryptionUtils.encrypt("s");
            System.out.println(result);
            System.out.println(FPEncryptionUtils.decrypt(result));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
