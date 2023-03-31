package idv.laborLab.userService.domain;

public interface EncryptionService {


    String encrypt(String inputString);
    byte[] encryptToByte(String inputString);

    String decrypt(String encryptedString);
    String decrypt(byte[] encryptedContent);

}
