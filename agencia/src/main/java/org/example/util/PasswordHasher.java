package org.example.util;

public class PasswordHasher {

    public static String hashPassword(String password) {
        System.out.println("DEBUG: Contraseña recibida (texto plano): " + password);
        return password;
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        System.out.println("DEBUG: Comparando '" + password + "' con '" + hashedPassword + "'");
        return password.equals(hashedPassword);
    }
}