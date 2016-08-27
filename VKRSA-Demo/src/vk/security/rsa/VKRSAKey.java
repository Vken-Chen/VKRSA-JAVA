package vk.security.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by chenbo on 16/8/27.
 */
public class VKRSAKey {

    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM_RSA = "RSA";

    /**
     * 通过PEM格式字符串构造公钥
     * 公钥的PEM格式如下：
     * -----BEGIN PUBLIC KEY-----
     * MIIBHjANBgkqhkiG9w0BAQEFAAOCAQsAMIIBBgKB/gwzcVng1Yj/OuQwca7Zggcr...
     * -----END PUBLIC KEY-----
     *
     * @param pem
     * @return
     */
    public static RSAPublicKey getPublicKeyFromPEM(String pem) throws Exception {

        String pureString = VKRSAKey.trimPublicPemHeaderAndFooter(pem);
        byte[] keyBytes = VKBase64.decode(pureString);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        return (RSAPublicKey) publicKey;
    }

    /**
     * 处理PEM中的header和footer
     * @param pem
     * @return
     */
    private static String trimPublicPemHeaderAndFooter(String pem)
    {
        String header = "-----BEGIN PUBLIC KEY-----";
        String footer = "-----END PUBLIC KEY-----";

        String result = pem;
        result = result.replace(header,"");
        result = result.replace(footer,"");

        //转换\r \n \t ' '
        result = result.replace("\r", "");
        result = result.replace("\n","");
        result = result.replace("\t","");
        result = result.replace(" ","");

        return result;
    }

    /**
     * 通过pk8PEM格式字符串构造公钥，注意与getPrivateKeyFromPem方法区别开
     * pkcs8的PEM格式如下：
     * -----BEGIN PRIVATE KEY-----
     * MIIBHjANBgkqhkiG9w0BAQEFAAOCAQsAMIIBBgKB/gwzcVng1Yj/OuQwca7Zggcr...
     * -----END PRIVATE KEY-----
     *
     * @param pem
     * @return
     */
    public static RSAPrivateKey getPrivateKeyFromPk8pem(String pem) throws Exception {

        String pureString  = VKRSAKey.trimPrivatePk8pemHeaderAndFooter(pem);

        byte[] keyBytes = VKBase64.decode(pureString);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        return (RSAPrivateKey) privateKey;
    }

    /**
     * 处理PEM中的header和footer
     * @param pem
     * @return
     */
    private static String trimPrivatePk8pemHeaderAndFooter(String pem)
    {
        String header = "-----BEGIN PRIVATE KEY-----";
        String footer = "-----END PRIVATE KEY-----";

        String result = pem;
        result = result.replace(header,"");
        result = result.replace(footer,"");

        //转换\r \n \t ' '
        result = result.replace("\r", "");
        result = result.replace("\n","");
        result = result.replace("\t","");
        result = result.replace(" ","");

        return result;
    }

    /**
     * 通过原始的PEM格式字符串构造私钥
     * 原始的PEM格式如下：
     * -----BEGIN RSA PRIVATE KEY-----
     * MIIBHjANBgkqhkiG9w0BAQEFAAOCAQsAMIIBBgKB/gwzcVng1Yj/OuQwca7Zggcr...
     * -----END RSA PRIVATE KEY-----
     *
     * @param pem
     * @return
     */

    public static RSAPrivateKey getPrivateKeyFromPem(String pem) throws Exception {
        String pureString = VKRSAKey.trimPrivatePemHeaderAndFooter(pem);
        byte[] keyBytes = VKBase64.decode(pureString);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        Key privateKey = keyFactory.generatePrivate(x509KeySpec);
        return (RSAPrivateKey) privateKey;
    }

    /**
     * 处理PEM中的header和footer
     * @param pem
     * @return
     */
    private static String trimPrivatePemHeaderAndFooter(String pem)
    {
        String header = "-----BEGIN RSA PRIVATE KEY-----";
        String footer = "-----END RSA PRIVATE KEY-----";

        String result = pem;
        result = result.replace(header,"");
        result = result.replace(footer,"");

        //转换\r \n \t ' '
        result = result.replace("\r", "");
        result = result.replace("\n","");
        result = result.replace("\t","");
        result = result.replace(" ","");

        return result;
    }
}
