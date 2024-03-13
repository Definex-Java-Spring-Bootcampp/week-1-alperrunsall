package com.patika.kredinbizdenservice;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main2 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(passwordSHA("abc123"));
        System.out.println(passwordSHA("abc123"));
        System.out.println(passwordSHA("abc123").equals(passwordSHA("abc123")));
    }

    public static String passwordSHA(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] passBytes = password.getBytes(StandardCharsets.UTF_8);

        byte[] hashBytes = md.digest(passBytes);

        return new String(hashBytes, StandardCharsets.UTF_8);

    }
}
