package idv.laborLab.userService;

import idv.laborLab.miscellaneous.services.crypto.CryptoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CryptoServiceTest {

    @Autowired
    private CryptoService cryptoService;

    @Test
    void test_encryption_and_decryption_should_be_matched() {

        String data = "Maxence";
        String encryptedData = cryptoService.encrypt(data);
        System.out.println(encryptedData);

        String decryptedData = cryptoService.decrypt(encryptedData);
        System.out.println(decryptedData);
        Assertions.assertEquals(data, decryptedData);
    }

}
