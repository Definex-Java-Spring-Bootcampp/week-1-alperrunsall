package com.patika.kredinbizdenservice;

import com.patika.kredinbizdenservice.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

public class UserDatabase {
    private final Map<String, User> users = new HashMap<>();

    public User registerUser(String name, String surname, LocalDate birthDate, String email, String password, String phoneNumber, Boolean isActive){
        if(users.containsKey(email)){
            throw new IllegalArgumentException("Bu e-posta ile zaten bir kullanıcı var.");
        }
        User user = new User(name, surname, birthDate, email, hashPassword(password), phoneNumber, isActive);
        users.put(email,user);
        return user;
    }
    private String hashPassword(String password) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] passBytes = password.getBytes(StandardCharsets.UTF_8);

            byte[] hashBytes = md.digest(passBytes);

            return new String(hashBytes, StandardCharsets.UTF_8);
        }
        catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    public User findUserByEmail(String email){
        return users.get(email);
    }

    public User randomUser(){
        Random random = new Random();
        ArrayList<User> users = new ArrayList<>(this.users.values());
        return users.get(random.nextInt(users.size()));
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(this.users.values());
    }


}
