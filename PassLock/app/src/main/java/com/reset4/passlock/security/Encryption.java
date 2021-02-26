package com.reset4.passlock.security;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ilkery on 24.12.2016.
 */
public class Encryption {

    public static EncryptionResult encrypt(String masterPassword, String value) {
        EncryptionResult encryptionResult = new EncryptionResult("");
        if(masterPassword.length() < 8)
        {
            masterPassword = masterPassword + masterPassword.substring(0, 8 - masterPassword.length());
        }
        try {
            DESKeySpec keySpec = new DESKeySpec(masterPassword.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            encryptionResult.setValue(Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT));
        } catch (InvalidKeyException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (InvalidKeySpecException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (BadPaddingException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (NoSuchPaddingException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        }
        return encryptionResult;
    }

    public static EncryptionResult decrypt(String masterPassword, String value) {
        EncryptionResult encryptionResult = new EncryptionResult("");
        if(masterPassword.length() < 8)
        {
            masterPassword = masterPassword + masterPassword.substring(0, 8 - masterPassword.length());
        }
        try {
            DESKeySpec keySpec = new DESKeySpec(masterPassword.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

            encryptionResult.setValue(new String(decrypedValueBytes));
        } catch (InvalidKeyException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (InvalidKeySpecException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (BadPaddingException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (NoSuchPaddingException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            encryptionResult.setExceptionClass(e.getClass());
            encryptionResult.setExceptionMessage(e.getMessage());
        }
        return encryptionResult;
    }
}
