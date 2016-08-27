package vk.security.rsa;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Created by chenbo on 16/8/27.
 */
public class VKRSA {

    /**
     * 加密，默认是采用公钥加密
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, RSAPublicKey publicKey,boolean needPadding)
            throws Exception {

        //准备X.509
        Cipher cipher =  Cipher.getInstance(VKRSAConstant.getRSAAlgorithmName(needPadding));
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //加密
        //step1.获取模长度
        int bitCount = publicKey.getModulus().bitCount();
        int bitLength = publicKey.getModulus().bitLength();
        int modulusLen = publicKey.getModulus().bitLength()/8;
        //加密的块的长度必须是模长度-11；没有为什么，这就是RSA的规定
        int encryptBlockSize = modulusLen-11;

        //step2.加密数据
        int encryptedSize = 0;      //已加密的数据长度
        int needEncryptedSize = 0;  //下一次需要加密的块长度,
        int allNeedEncrypSize = data.length;    //需要加密的总大小

        //输出
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        for( needEncryptedSize=0;encryptedSize < allNeedEncrypSize;encryptedSize+=encryptBlockSize)
        {
            needEncryptedSize = allNeedEncrypSize-encryptedSize;
            //每次加密不能超过 blockSizePerEncrypt 大小
            if(needEncryptedSize > encryptBlockSize)
            {
                needEncryptedSize = encryptBlockSize;
            }

            byte[] encryptBuffer = cipher.doFinal(data,encryptedSize,needEncryptedSize);
            out.write(encryptBuffer, 0, encryptBuffer.length);
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] decrypt(byte[] data,RSAPrivateKey privateKey,boolean needPadding) throws Exception
    {
        //准备X.509
        Cipher cipher =  Cipher.getInstance(VKRSAConstant.getRSAAlgorithmName(needPadding));
        //Cipher cipher =  Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //step1.获取模长度
        int modulusLen = privateKey.getModulus().bitLength()/8;
        //解密的块的长度，和模的长度相同，和加密不同
        int decryptBlockSize = modulusLen;

        //step2.解密数据
        int decryptedSize = 0;      //已解密的数据长度
        int needDecryptedSize = 0;  //下一次需要解密的块长度,
        int allNeedDecrypSize = data.length;    //需要加密的总大小

        //解密数据
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for(decryptedSize =0;decryptedSize<allNeedDecrypSize;decryptedSize+=decryptBlockSize)
        {
            needDecryptedSize = allNeedDecrypSize-decryptedSize;
            //每次解密长度不能超过上限
            if(needDecryptedSize > decryptBlockSize)
            {
                needDecryptedSize = decryptBlockSize;
            }

            byte[] decryptBuffer = cipher.doFinal(data,decryptedSize,needDecryptedSize);
            out.write(decryptBuffer, 0, decryptBuffer.length);
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, RSAPrivateKey privateK)
            throws Exception {

        Cipher cipher =  Cipher.getInstance(VKRSAConstant.getRSAAlgorithmName(true));
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int MAX_DECRYPT_BLOCK = privateK.getModulus().bitLength()/8;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

}
