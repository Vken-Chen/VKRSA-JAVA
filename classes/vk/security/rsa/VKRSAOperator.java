package vk.security.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by chenbo on 16/8/27.
 */
public class VKRSAOperator {

    private static VKRSAOperator instance;

    public static VKRSAOperator defaultOperator() {
        if (instance == null) {
            instance = new VKRSAOperator();
        }
        return instance;
    }

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    //加密的时候是否需要padding
    //设置padding之后，每次加密的数据都不一致.
    private boolean needPadding = true;

    public boolean isNeedPadding() {
        return needPadding;
    }

    public void setNeedPadding(boolean needPadding) {
        this.needPadding = needPadding;
    }

    /**
     * 初始化公钥，用PEM格式字符串
     *
     * @param pem
     * @throws Exception
     */
    public void setupPublicKeyWihtPEM(String pem) throws Exception {
        if(pem == null)
        {
            Exception e = new Exception("VKRSAOperator setupPublicKeyWihtPEM fail,the reason: the input pem is null");
            throw e;
        }

        publicKey = VKRSAKey.getPublicKeyFromPEM(pem);
    }

    public void setupPrivateKeyWithPEM(String pem) throws Exception {
        if(pem == null)
        {
            Exception e = new Exception("VKRSAOperator setupPrivateKeyWithPEM fail,the reason: the input pem is null");
            throw e;
        }
        privateKey = VKRSAKey.getPrivateKeyFromPem(pem);
    }

    public void setupPrivateKeyWithPk8PEM(String pem) throws Exception {
        if(pem == null)
        {
            Exception e = new Exception("VKRSAOperator setupPrivateKeyWithPk8PEM fail,the reason: the input pem is null");
            throw e;
        }
        privateKey = VKRSAKey.getPrivateKeyFromPk8pem(pem);
    }

    /**
     * 加密byte[],输出byte[]，采用公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] encryptData(byte[] data) throws Exception {
        if (data == null) {
            Exception e = new Exception("VKRSAOperator encryptData fail,the reason: the input data is null");
            throw e;
        }
        if (publicKey == null) {
            Exception e = new Exception("VKRSAOperator encryptData fail,the reason: public key is not ready");
            throw e;
        }
        return VKRSA.encrypt(data, publicKey,isNeedPadding());
    }

    /**
     * 加密字符串,输出byte[]，采用公钥加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public byte[] encryptString(String str) throws Exception {
        if (str == null) {
            Exception e = new Exception("VKRSAOperator encryptString fail,the reason:input string is null");
            throw e;
        }
        byte[] stringBytes = str.getBytes();
        return encryptData(stringBytes);
    }


    /**
     * 加密byte[]，输出base64编码字符串，采用公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String encrptDataToBase64(byte[] data) throws Exception {
        if (data == null) {
            Exception e = new Exception("VKRSAOperator encrptDataToBase64 fail,the reason:the input data is null ");
            throw e;
        }

        byte[] encryptedData = this.encryptData(data);
        if (encryptedData == null) {
            Exception e = new Exception("VKRSAOperator encrptDataToBase64 fail,the reason:encryptData return null ");
            throw e;
        }
        return VKBase64.encode(encryptedData);
    }


    /**
     * 加密字符串，输出base64编码字符串，采用公钥加密
     *
     * @param str
     * @return
     * @throws Exception
     */
    public String encryptStringToBase64(String str) throws Exception {
        if (str == null) {
            Exception e = new Exception("VKRSAOperator encrptDataToBase64 fail,the reason:the input string is null ");
            throw e;
        }

        byte[] encryptedData = this.encryptString(str);
        if (encryptedData == null) {
            Exception e = new Exception("VKRSAOperator encryptStringToBase64 fail,the reason:encryptString return null ");
            throw e;
        }
        return VKBase64.encode(encryptedData);
    }


    /**
     * 解密byte,输出byte[],采用私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] decryptToData(byte[] data) throws Exception {
        if (data == null) {
            Exception e = new Exception("VKRSAOperator decryptToData fail,the reason: the input data is null");
            throw e;
        }

        if (privateKey == null) {
            Exception e = new Exception("VKRSAOperator decryptToData fail,the reason: private key is not ready");
            throw e;
        }
        return VKRSA.decrypt(data, privateKey,isNeedPadding());
    }

    /**
     * 解密byte,输出String,采用私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String decryptToString(byte[] data) throws Exception {
        byte[] decryptedData = this.decryptToData(data);
        if (decryptedData == null) {
            Exception e = new Exception("VKRSAOperator decryptToString fail,the reason: decryptToData return null");
            throw e;
        }
        String result = new String(decryptedData);
        return result;
    }

    /**
     * 解密base64字符串，输出byte[]，采用私钥解密
     *
     * @param base64String
     * @return
     * @throws Exception
     */
    public byte[] decryptBase64ToData(String base64String) throws Exception {
        if (base64String == null) {
            Exception e = new Exception("VKRSAOperator decryptBase64ToData fail,the reason: the input base64String is null");
            throw e;
        }

        byte[] unBase64 = VKBase64.decode(base64String);
        if (unBase64 == null) {
            Exception e = new Exception("VKRSAOperator decryptBase64ToData fail,the reason: VKBase64.decode return null");
            throw e;
        }

        return this.decryptToData(unBase64);
    }

    /**
     * 解密base64字符串，输出String，采用私钥解密
     *
     * @param base64String
     * @return
     * @throws Exception
     */
    public String decryptBase64ToString(String base64String) throws Exception {
        if (base64String == null) {
            Exception e = new Exception("VKRSAOperator decryptBase64ToString fail,the reason: the input base64String is null");
            throw e;
        }

        byte[] unBase64 = VKBase64.decode(base64String);
        if (unBase64 == null) {
            Exception e = new Exception("VKRSAOperator decryptBase64ToString fail,the reason: VKBase64.decode return null");
            throw e;
        }

        return this.decryptToString(unBase64);
    }
}
