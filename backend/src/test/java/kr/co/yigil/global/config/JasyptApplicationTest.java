package kr.co.yigil.global.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource("classpath:config.properties")
public class JasyptApplicationTest {

    @Value("${Jasypt-Secret-Key}")
    private String key;

    @Test
    void contextLoads(){}

    @Test
    void jasypt() {
        String value = "postgres";
        System.out.println(jasyptEncoding(value));
    }

    public String jasyptEncoding(String value) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        String encrypted = pbeEnc.encrypt(value);
        return encrypted + " / " + pbeEnc.decrypt(encrypted);
    }
}
