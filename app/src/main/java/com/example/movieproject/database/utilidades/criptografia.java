package com.example.movieproject.database.utilidades;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class criptografia {

    private static final String AES = "AES";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String desEncriptar(String password, String username) throws Exception {
        SecretKeySpec llaveSecreta = generateKey(username);
        Cipher cifrado = Cipher.getInstance("AES");
        cifrado.init(Cipher.DECRYPT_MODE, llaveSecreta);
        byte[] passwordDescodificado = Base64.decode(password, Base64.DEFAULT);
        byte[] passwordDesencriptado = cifrado.doFinal(passwordDescodificado);
        String passwordDesencriptadoString = new String(passwordDesencriptado);

        return passwordDesencriptadoString;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encriptar(String password, String username) throws Exception {
        SecretKeySpec llaveSecreta = generateKey(username);
        Cipher cifrado = Cipher.getInstance("AES");
        cifrado.init(Cipher.ENCRYPT_MODE, llaveSecreta);
        byte[] passwordEncriptado = cifrado.doFinal(password.getBytes("UTF-8"));
        String datosEncriptadosString = Base64.encodeToString(passwordEncriptado, Base64.DEFAULT);

        return datosEncriptadosString;
    }

    private static SecretKeySpec generateKey(String username) throws Exception {

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = username.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec llaveSecreta = new SecretKeySpec(key,"AES");
        return llaveSecreta;
    }

}
