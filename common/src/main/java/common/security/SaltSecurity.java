package common.security;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltSecurity {

    public static String getSalt(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return  Base64.getEncoder().encodeToString(salt);
    }

}
