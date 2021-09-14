package WXwork.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesAlgo {

    static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    static final String secretKey = "Cz_No.1_PplsHosp";


    public static byte[] encrypt(String content, String secretKey) throws Exception { // 加密

        return aes(content.getBytes(StandardCharsets.UTF_8), secretKey);
    }

//    public static String decrypt(byte[] contentArray, String secretKey) throws Exception { // 解密
//        byte[] result = aes(contentArray, Cipher.DECRYPT_MODE, secretKey);
//        return new String(result, StandardCharsets.UTF_8);
//    }

    private static byte[] aes(byte[] contentArray, String secretKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey.getBytes(), "AES"));
        return cipher.doFinal(contentArray);
    }


    public static String aesalgo(String content) {
        String result = "";
        Base64.Encoder en = Base64.getEncoder();
        // Base64.Decoder de = Base64.getDecoder();
        try {
            byte[] encryptResult = encrypt(content, secretKey);

            byte[] abc = en.encode(encryptResult);
            // byte[] efg = de.decode(abc);
            // String c = decrypt(efg, secretKey);
            return new String(abc);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
