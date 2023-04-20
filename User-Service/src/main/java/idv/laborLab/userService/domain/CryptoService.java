package idv.laborLab.userService.domain;

public interface CryptoService {

    String encrypt(String inputData);

    String decrypt(String encryptedString);
}
