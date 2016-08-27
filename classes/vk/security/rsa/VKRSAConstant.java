package vk.security.rsa;

/**
 * Created by vkenchen on 16/8/27.
 */
public class VKRSAConstant {


    /** java 平台，已验证 **/
    private static final String JAVA_RSA_ALGORITHM_PADDING = "RSA/ECB/PKCS1Padding";
    private static final String JAVA_RSA_ALGORITHM_NOPADDING = "RSA/ECB/NoPadding";


    /** android平台 未验证**/
    private static final String ANDROID_RSA_ALGORITHM_PADDING = "RSA/None/PKCS1Padding";
    private static final String ANDROID_RSA_ALGORITHM_NOPADDING = "RSA//None/NoPadding";


    public static boolean isAndroid = false;

    public static String getRSAAlgorithmName(boolean needPadding)
    {
        if(isAndroid)
        {
            return needPadding?ANDROID_RSA_ALGORITHM_PADDING:ANDROID_RSA_ALGORITHM_NOPADDING;
        }else
        {
            return needPadding?JAVA_RSA_ALGORITHM_PADDING:JAVA_RSA_ALGORITHM_NOPADDING;
        }
    }

}
