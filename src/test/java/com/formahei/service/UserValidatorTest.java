package com.formahei.service;

import com.formahei.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidatorTest {

    private final UserValidator userValidator = new UserValidator();
    @Test
    public void validate() {

        User user1 = initUser("a/nna.formahei.com", "0000");
        String result1 = userValidator.validate(user1);
        assertEquals("Email must content letters, @, ., 2 or 3 characters in the end", (result1));

        User user2 = initUser("email", "0000");
        String result2 = userValidator.validate(user2);
        assertEquals("Email must content letters, @, ., 2 or 3 characters in the end", (result2));

        User user3 = initUser("@mail.com", "0000");
        String result3 = userValidator.validate(user3);
        assertEquals("Email must content letters, @, ., 2 or 3 characters in the end", (result3));

        User user4 = initUser("test@gmail.com", "55");
        String result4 = userValidator.validate(user4);
        assertEquals("Password must have at least 4 characters", (result4));


        User user5 = initUser("test@gmail.com", "");
        String result5 = userValidator.validate(user5);
        assertEquals("Password must have at least 4 characters", (result5));

        User user6 = initUser("test@gmail.com", "qqq");
        String result6 = userValidator.validate(user6);
        assertEquals("Password must have at least 4 characters", (result6));

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