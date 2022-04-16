package com.formahei.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordEncoderTest {

    @Test
    public void encode() {
        assertEquals("MTIzNA==", PasswordEncoder.encode("1234"));
    }
}