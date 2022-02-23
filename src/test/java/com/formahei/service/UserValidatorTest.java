package com.formahei.service;

import com.formahei.entity.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();

    @Test
    public void getInvalidEmail1(){
        User user = initUser("a/nna.formahei.com", "0000");
        String result = userValidator.validate(user);
        assertEquals("Email must content letters, @, ., 2 or 3 characters in the end",(result));
    }
    @Test
    public void getInvalidEmail2(){
        User user = initUser("email", "0000");
        String result = userValidator.validate(user);
        assertEquals("Email must content letters, @, ., 2 or 3 characters in the end",(result));
    }
    @Test
    public void getInvalidEmail3(){
        User user = initUser("@mail.com", "0000");
        String result = userValidator.validate(user);
        assertEquals("Email must content letters, @, ., 2 or 3 characters in the end",(result));
    }
    @Test
    public void getInvalidPassword1(){
        User user = initUser("test@gmail.com", "55");
        String result = userValidator.validate(user);
        assertEquals("Password must have at least 4 characters",(result));
    }

    @Test
    public void getInvalidPassword2(){
        User user = initUser("test@gmail.com", "");
        String result = userValidator.validate(user);
        assertEquals("Password must have at least 4 characters",(result));
    }
    @Test
    public void getInvalidPassword3(){
        User user = initUser("test@gmail.com", "qqq");
        String result = userValidator.validate(user);
        assertEquals("Password must have at least 4 characters",(result));
    }

    private static User initUser(String email, String password) {
        User user = new User();
        user.setLogin("anna");
        user.setFirstName("Anastasiia");
        user.setLastName("Formahei");
        user.setAccount(0);
        user.setStatus("unblocked");
        user.setRole("CLIENT");
        user.setEmail(email);
        user.setPass(password);
        return user;
    }
}
