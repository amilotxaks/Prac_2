package Prac_1;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class AESFileDecryptor {

    public static void main(String[] args) {
        String encryptedFilePath = "C:\\Users\\student\\Desktop\\untitled\\src\\Prac_1\\secret_text.enc";
        String keyFileName = "C:\\Users\\student\\Desktop\\untitled\\src\\Prac_1\\aes.key";

        try {
            // Чтение ключа из файла
            FileInputStream keyInputStream = new FileInputStream(keyFileName);
            byte[] keyAndIV = new byte[32];
            keyInputStream.read(keyAndIV);
            Key key = new SecretKeySpec(keyAndIV, 16, 16, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyAndIV, 0, 16);

            // Создание объекта Cipher для расшифровки
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, ivSpec.getIV()));


            // Чтение зашифрованных данных из файла и расшифровка
            FileInputStream encryptedInputStream = new FileInputStream(encryptedFilePath);
            byte[] encryptedData = new byte[encryptedInputStream.available()];
            encryptedInputStream.read(encryptedData);
            byte[] decryptedData = cipher.doFinal(encryptedData);

            // Запись расшифрованных данных в новый файл
            FileOutputStream decryptedOutputStream = new FileOutputStream("decrypted_output.txt");
            decryptedOutputStream.write(decryptedData);

            keyInputStream.close();
            encryptedInputStream.close();
            decryptedOutputStream.close();
            System.out.println("Файл успешно расшифрован.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

