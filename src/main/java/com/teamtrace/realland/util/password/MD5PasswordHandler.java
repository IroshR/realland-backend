package com.teamtrace.realland.util.password;

import org.springframework.stereotype.Component;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

@Component
public class MD5PasswordHandler implements PasswordHandler {
    @Override
    public String hashPassword(String username, String password) throws GeneralSecurityException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    @Override
    public boolean validatePassword(String username, String password, String storedHash) throws GeneralSecurityException {
        String hash = hashPassword(username, password);
        return storedHash.equals(hash);
    }
}
