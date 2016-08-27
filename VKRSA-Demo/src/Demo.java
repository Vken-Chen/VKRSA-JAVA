import vk.security.rsa.VKRSAOperator;

public class Demo {

    private VKRSAOperator operator;
    private String  testString = "this is vkrsa, it's the best useful and simplest rsa library of java";
    private byte[]  testData = null;
    private boolean isPrepare = false;

    public void prepare()
    {
        isPrepare = false;
        //operator = new VKRSAOperator();
        operator = VKRSAOperator.defaultOperator();
        operator.setNeedPadding(true);

        try {
            String privateKeyPEM= FileUtil.readFileAsString("src/rsa_private_key_pkcs8.pem");
            operator.setupPrivateKeyWithPk8PEM(privateKeyPEM);

        } catch (Exception e) {
            System.out.println("私钥初始化失败");
            e.printStackTrace();
            return ;
        }


        try {
            String publicKeyPEM = FileUtil.readFileAsString("src/rsa_public_key.pem");
            operator.setupPublicKeyWihtPEM(publicKeyPEM);
        } catch (Exception e) {
            System.out.println("公钥初始化失败");
            e.printStackTrace();
            return ;
        }

        testString = "this is vkrsa, it's the best useful and simplest rsa library of java";
        testData = FileUtil.readFileAsData("src/testData");

        isPrepare = true;
    }

    public void simpleUse()
    {
        if(!isPrepare)
        {
            System.out.println("准备工作未做完，请先prepare()");
            return ;
        }

        //data 加解密，数据有分块
        System.out.println("=========================data 加解密============================");
        try {
            String testDataString = new String(testData,"UTF-8");
            long timeStart = System.currentTimeMillis();
            byte[] encryptedData =  operator.encryptData(testData);
            long timeEnd = System.currentTimeMillis();
            long time = timeEnd - timeStart;
            System.out.println("encryptData加密成功，耗时："+time+" 毫秒");

            timeStart = System.currentTimeMillis();
            byte[] decryptData =  operator.decryptToData(encryptedData);
            timeEnd = System.currentTimeMillis();
            time = timeEnd - timeStart;
            String decryptString = new String(decryptData,"UTF-8");

            System.out.println("decryptToData解密成功，耗时："+time+" 毫秒");
            if(testDataString.equals(decryptString))
            {
                System.out.println("原始数据与解密后数据对比一致");
            }else
            {
                System.out.println("原始数据与解密后数据对比一致");
            }
            //System.out.println("解密后数据为:"+decryptString);




        } catch (Exception e) {
            e.printStackTrace();
        }

        //字符串加密
        System.out.println("=========================string 加解密============================");
        try {
            long timeStart = System.currentTimeMillis();
            byte[] encryptedData =  operator.encryptString(testString);
            long timeEnd = System.currentTimeMillis();
            long time = timeEnd - timeStart;
            System.out.println("encryptString 加密成功，耗时："+time+" 毫秒");

            timeStart = System.currentTimeMillis();
            String decryptedString =  operator.decryptToString(encryptedData);
            timeEnd = System.currentTimeMillis();
            time = timeEnd - timeStart;
            System.out.println("decryptToString 解密成功，耗时："+time+" 毫秒");
            if(decryptedString.equals(testString))
            {
                System.out.println("原始数据与解密后数据对比一致");
            }else
            {
                System.out.println("原始数据与解密后数据对比一致");
            }
            System.out.println("解密后数据为:"+decryptedString);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        System.out.println("=========================VKRSA Welcome============================\r\n");
        System.out.println("Welcome to the demo of VKRSA !\r\n");
        System.out.println("==================================================================\r\n");
        Demo demo = new Demo();
        demo.prepare();

        demo.simpleUse();
    }

}
