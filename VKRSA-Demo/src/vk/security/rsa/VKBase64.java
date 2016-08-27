package vk.security.rsa;

/**
 * Created by chenbo on 16/8/27.
 */
public class VKBase64 {

    /**
     * base decode
     *
     * @param base64String
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64String) throws Exception {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        return decoder.decodeBuffer(base64String);
    }

    /**
     * base64 encode
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(bytes);
    }
}
