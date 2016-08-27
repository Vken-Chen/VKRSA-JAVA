import java.io.*;

/**
 * Created by vkenchen on 16/8/27.
 */
public class FileUtil {
    /**
     * 主要是输入流的使用，最常用的写法
     *
     * @param filePath
     * @return
     */
    public static String readFileAsString(String filePath) {
        // 读取txt内容为字符串
        StringBuffer buffer = new StringBuffer();
        // 每次读取的byte数
        byte[] b = new byte[8 * 1024];
        InputStream in = null;
        try {
            // 文件输入流
            in = new FileInputStream(filePath);
            while (in.read(b) != -1) {
                // 字符串拼接
                buffer.append(new String(b));
            }
            // 关闭流
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

    public static byte[] readFileAsData(String filename) {

        File f = new File(filename);

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
