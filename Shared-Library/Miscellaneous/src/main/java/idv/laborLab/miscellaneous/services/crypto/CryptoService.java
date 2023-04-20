package idv.laborLab.miscellaneous.services.crypto;

public interface CryptoService {

    String encrypt(String inputData);

    String decrypt(String encryptedString);
}
