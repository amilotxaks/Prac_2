package Prac_2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordCracker {

    private static final String HASH = "2a2375e1171723a0e04a3c49adccb4ec6db86b2f7527db45e0bb84d8d76a9b9d3536d39e01b92d303fc966b36aa73475f9aea541d63f5ad894a50dda63b68a1c";

    public static void main(String[] args) {
        String password = crackPassword(HASH);
        System.out.println("Исходный пароль: " + password);
    }

    private static String crackPassword(String hash) {
        char[] password = new char[5];
        Arrays.fill(password, 'a');

        while (true) {
            String passwordStr = new String(password);
            String hashValue = generateHash(passwordStr);
            if (hash.equals(hashValue)) {
                return passwordStr;
            }

            incrementPassword(password, password.length - 1);
        }
    }

    private static void incrementPassword(char[] password, int position) {
        if (position < 0) {
            return;
        }

        if (password[position] != 'e') {
            password[position]++;
        } else {
            password[position] = 'a';
            incrementPassword(password, position - 1);
        }
    }

    private static String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder hexHash = new StringBuilder();
            for (byte b : hashBytes) {
                hexHash.append(String.format("%02x", b));
            }

            return hexHash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
