package com.example.fabrice.gestionlivraison.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fabrice on 2017-02-24.
 */

public class PasswordHelper {
    public static String md5(String password) {
        try
        {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
            {
                hexString.append(Integer.toHexString(0xFF & aMessageDigest));
            }
            return hexString.toString();

        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
