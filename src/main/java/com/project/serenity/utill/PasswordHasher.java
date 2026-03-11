package com.project.serenity.utill;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {
    public static void main(String[] args) {
        String rawPasswordAdmin = "admin123";
        String rawPasswordReceptionist = "recep123";
        String hashedPasswordAdmin = BCrypt.withDefaults().hashToString(12, rawPasswordAdmin.toCharArray());
        String hashedPasswordReceptionist = BCrypt.withDefaults().hashToString(12, rawPasswordReceptionist.toCharArray());
        System.out.println("Hashed Password: " + hashedPasswordAdmin);
        System.out.println("Hashed Password: " + hashedPasswordReceptionist);
    }
}
