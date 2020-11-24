package com.teamtrace.realland.util.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordHandlerFactory {
    @Autowired
    MD5PasswordHandler md5PasswordHandler;
    @Autowired
    PBKDF2PasswordHandler pbkdf2PasswordHandler;

    public PasswordHandler getPasswordHandler(final short type) {
        switch (type) {
            case PasswordHandler.MD5: {
                return md5PasswordHandler;
            }
            default: {
                return pbkdf2PasswordHandler;
            }
        }
    }
}
