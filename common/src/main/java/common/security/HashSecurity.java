package common.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSecurity {



    public static String getHash(String password) {
            MessageDigest md = getMessageDigest();
            byte[] passwordBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : passwordBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
    }



    public static MessageDigest getMessageDigest(){
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }

    }

}