package TXmanage.aes;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class DEScipher {

    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "DES";

    public static byte[] encrypt(String src, String key) throws Exception {

        byte[] srccode = src.getBytes(StandardCharsets.UTF_8);
        byte[] keycode = key.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        KeySpec keySpec = new DESKeySpec(keycode);
        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
        return cipher.doFinal(srccode);

    }

    public static String decrypt(byte[] encryptBytes, String key) throws Exception {

        //解密
        byte[] keycode = key.getBytes(StandardCharsets.UTF_8);
        Cipher deCipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeyFactory deDecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        KeySpec deKeySpec = new DESKeySpec(keycode);
        SecretKey deSecretKey = deDecretKeyFactory.generateSecret(deKeySpec);
        deCipher.init(Cipher.DECRYPT_MODE, deSecretKey, new SecureRandom());
        return new String(deCipher.doFinal(encryptBytes));

    }

}









