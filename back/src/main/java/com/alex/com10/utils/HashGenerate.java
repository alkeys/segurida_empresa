package com.alex.com10.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
@Component
public class HashGenerate {
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //compara el hash con la contraseña
    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
