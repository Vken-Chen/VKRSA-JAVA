#VKRSA-Java&&Android平台实现
近期本人针对RSA算法进行了封装，该代码为java代码，兼容Android平台。当前为java平台的测试demo，后续补充java平台的测试Demo；
<p><h4>iOS平台代码请转到<a href='https://github.com/Vken-Chen/VKRSA'>Vken-Chen/VKRSA</a></p>
## 说明
<p>该代码是用java.security库实现的RSA相关封装，目前只支持加密、解密，签名和验签后续再完善。</p>
<p>DEMO使用的IDE为Idea14，Eclipse或者其他IDE请自行迁移。</p>
## 1. Demo运行效果
<p align="center"><img src="https://github.com/Vken-Chen/VKRSA-JAVA/blob/master/demo.png" width="800"></p> 
## 2. 使用步骤
<p>使用VKRSAOperator类进行加解密相关操作</p>
<li>step 1.密钥初始化</li>
<li>step 2.加密或者解密</li>
### 2.1 密钥初始化
如果对密钥和证书不懂，请看<a href='http://www.jianshu.com/p/6927fe6f9813'>证书编码以及文件格式汇总</a>
#### 2.1.1 公钥初始化
<p>说明：如果只做加密，只需要初始化公钥</p>
<li>PEM格式的公钥</li>
<pre>
try {
	String publicKeyPEM = FileUtil.readFileAsString("src/rsa_public_key.pem");
	operator.setupPublicKeyWihtPEM(publicKeyPEM);
} catch (Exception e) {
    System.out.println("公钥初始化失败");
    e.printStackTrace();
}       
</pre>

#### 2.1.2 私钥初始化
<p>说明：如果只做解密，只需要初始化私钥。<br>
目前私钥只支持pkcs8格式的PEM私钥文件，后续升级。<br>
为什么私钥没有DER格式？目前DER格式一般来说只用于公钥，对外传播。</p>
<li>PEM格式的私钥（PKCS8）</li>
<pre>
try {
	String privateKeyPEM= FileUtil.readFileAsString("src/rsa_private_key_pkcs8.pem");
	operator.setupPrivateKeyWithPk8PEM(privateKeyPEM);
} catch (Exception e) {
	System.out.println("私钥初始化失败");
	e.printStackTrace();
}
</pre>
</p>
### 2.2 加密
<li> 加密NSData </li>
<pre>
byte[] testData = ...;
byte[] encryptedData =  operator.encryptData(testData);
</pre>
</p>
<li> 加密NSString(UTF8编码) </li>
<pre>
String testString = ...;
byte[] encryptedData =  operator.encryptString(testString);
</pre>

### 2.3 解密
<li> 解密到Data </li>
<pre>
byte[] decryptData =  operator.decryptToData(encryptedData);
</pre>
<li> 解密到String(UTF8编码) </li>
<pre>
String decryptedString =  operator.decryptToString(encryptedData);
</pre>

### 3.更多使用
待完善！
请查看我的简书系列文章：<a href='http://www.jianshu.com/p/84d925e4a57d'>揭开RSA神秘面纱


