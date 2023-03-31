package idv.laborLab.userService.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EncryptionEngine implements EncryptionService {

    @Override
    public String encrypt(String inputString) {

        return new String(Base64.encodeBase64(inputString.getBytes()));
    }

    @Override
    public byte[] encryptToByte(String inputString) {

        return Base64.encodeBase64(inputString.getBytes());
    }

    @Override
    public String decrypt(String encryptedString) {

        return new String(Base64.decodeBase64(encryptedString.getBytes()));
    }

    @Override
    public String decrypt(byte[] encryptedContent) {

        return new String(Base64.decodeBase64(encryptedContent));
    }
}
