package idv.laborLab.userService.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

@Service
@Slf4j
@RequiredArgsConstructor
public class AesGcmEncryptionEngine implements CryptoService {

    public static final String ALGORITHM_MODE = "AES/GCM/NoPadding";
    public static final String ALGORITHM = "AES";
    @Value("${idv.labor-lab.encryption.key}")
    private String encryptionKey;
    @Value("${idv.labor-lab.encryption.initial-vector}")
    private String initialVector;
    @Value("${idv.labor-lab.encryption.authentication-tag-length}")
    private int authenticationTagLength;
    @Value("${idv.labor-lab.encryption.additional-authenticated-data}")
    private String additionalAuthenticatedData;

    @Override
    public String encrypt(String inputData) {

        byte[] encoded = inputData.getBytes();
        byte[] encryptedData = encrypt(encoded);
        return Base64.encodeBase64String(encryptedData);
    }

    private byte[] encrypt(byte[] data) {

        byte[] encryptedData = serviceExecutor(data, Cipher.ENCRYPT_MODE);
        System.out.println("decryptedData: " + encryptedData);
        return encryptedData;
    }

    @Override
    public String decrypt(String encryptedString) {

        byte[] decoded = Base64.decodeBase64((encryptedString.getBytes()));
        byte[] decryptedData = decrypt(decoded);
        return new String(decryptedData);
    }

    private byte[] decrypt(byte[] data) {

        byte[] decryptedData = serviceExecutor(data, Cipher.DECRYPT_MODE);
        System.out.println("decryptedData: " + decryptedData);
        return decryptedData;
    }

    private byte[] serviceExecutor(byte[] data, final int workCode) {

        SecretKey secretKey = generateSecretKey();
        AlgorithmParameterSpec algorithmParameterSpec = getParameterSpec();

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(workCode, secretKey, algorithmParameterSpec);
            cipher.updateAAD(additionalAuthenticatedData.getBytes());
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey generateSecretKey() {

        SecretKey secretKey = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);
        return secretKey;
    }

    private AlgorithmParameterSpec getParameterSpec() {

        AlgorithmParameterSpec algorithmParameterSpec = new GCMParameterSpec(authenticationTagLength, initialVector.getBytes());
        return algorithmParameterSpec;
    }
}
